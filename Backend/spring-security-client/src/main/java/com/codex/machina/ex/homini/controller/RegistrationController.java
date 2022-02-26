package com.codex.machina.ex.homini.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.codex.machina.ex.homini.Model.PasswordModel;
import com.codex.machina.ex.homini.Model.UserModel;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.entity.VerificationToken;
import com.codex.machina.ex.homini.event.RegistrationCompleteEvent;
import com.codex.machina.ex.homini.service.EmailSenderService;
import com.codex.machina.ex.homini.service.UserService;
import com.codex.machina.ex.homini.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RegistrationController
{
	
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private EmailSenderService emailSenderService;
    @GetMapping("/api/test")
    public String test(Principal principal)
    {
        return "This is a test ! " + principal.getName();
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request)
    {
        User user = userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token)
    {
        String result = userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid"))
        {
            return "User Verified Successfully";
        }
        return "Bad User";
    }
    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request)
    {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request)
    {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user != null)
        {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user,token);
            url = passwordResetTokenMail(user, applicationUrl(request), token) ;
        }
        return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel)
    {
        String result = userService.validatePasswordToken(token);
        if(!result.equalsIgnoreCase("valid"))
        {
            return "Invalid Token";
        }
        Optional<User> user = userService.getUserByPasswordToken(token);
        if(user.isPresent())
        {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        }
        else
        {
            return "Invalid Token";
        }
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel)
    {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfoldPasswordisValid(user, passwordModel.getOldPassword()))
        {
            return "Invalid Old Password";
        }
            userService.changePassword(user, passwordModel.getNewPassword());
        return "Password Changed Successfully";
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/jwtTokenRefresh")
    public void refreshJWTToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            try
            {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByUsername(username);

                String access_token = JWT
                        .create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", List.of(user.getRole()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception e)
            {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("status",HttpStatus.FORBIDDEN.toString());
                error.put("message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else
        {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    private String applicationUrl(HttpServletRequest request)
    {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken)
    {
        String url = applicationUrl + "/verifyRegistration?token=" + verificationToken.getToken();
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to verify your account : { " + url + " }",
                "CODEX Account Verification Mail");
        System.out.println("Click the link to verify your account : { " + url + " }");
    }
    private String passwordResetTokenMail(User user, String applicationUrl, String token)
    {
        String url = applicationUrl + "/savePassword?token=" + token;
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to reset your password : { " + url + " }",
                "CODEX Account Password Reset Mail");
        System.out.println("Click the link to reset your password : { " + url + " }");
        return url;
    }
    @GetMapping("/users")
    public java.util.List<User> getAllUsers(){
    	return userService.fetchUsers();
    }
}
