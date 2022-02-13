package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.Model.UserModel;
import com.codex.machina.ex.homini.entity.PasswordToken;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.entity.VerificationToken;
import com.codex.machina.ex.homini.repository.PasswordTokenRepository;
import com.codex.machina.ex.homini.repository.UserRepository;
import com.codex.machina.ex.homini.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Override
    public User registerUser(UserModel userModel)
    {
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setFirstname(userModel.getFirstname());
        user.setLastname(userModel.getLastname());
        user.setBirthday(userModel.getBirthday());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));

        userRepository.save(user);

        return user;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user)
    {
        VerificationToken verificationToken = new VerificationToken(user,token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token)
    {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken == null)
        {
            return "invalid";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0)
        {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setActive(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken)
    {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token)
    {
        PasswordToken passwordToken = new PasswordToken(user,token);
        passwordTokenRepository.save(passwordToken);
    }

    @Override
    public String validatePasswordToken(String token)
    {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);
        if(passwordToken == null)
        {
            return "invalid";
        }
        User user = passwordToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(passwordToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0)
        {
            passwordTokenRepository.delete(passwordToken);
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordToken(String token)
    {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword)
    {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfoldPasswordisValid(User user, String oldPassword)
    {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
}
