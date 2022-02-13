package com.codex.machina.ex.homini.controller;

import com.codex.machina.ex.homini.Model.PasswordModel;
import com.codex.machina.ex.homini.Model.UserModel;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.entity.VerificationToken;
import com.codex.machina.ex.homini.event.RegistrationCompleteEvent;
import com.codex.machina.ex.homini.service.EmailSenderService;
import com.codex.machina.ex.homini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RegistrationController
{
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private EmailSenderService emailSenderService;
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
}
