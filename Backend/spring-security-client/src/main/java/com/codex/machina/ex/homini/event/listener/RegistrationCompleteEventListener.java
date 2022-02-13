package com.codex.machina.ex.homini.event.listener;

import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.event.RegistrationCompleteEvent;
import com.codex.machina.ex.homini.service.EmailSenderService;
import com.codex.machina.ex.homini.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>
{
    @Autowired
    private UserService userService;
    @Autowired
    private EmailSenderService emailSenderService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event)
    {
        // Create the verification token for the USER
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        // Send Mail to the USER
        String url = event.getApplicationUrl() + "/verifyRegistration?token=" + token;
        emailSenderService.sendSimpleEmail(
                user.getEmail(),
                "Click the link to verify your account : { " + url + " }",
                "CODEX Account Verification Mail");
        System.out.println("Click the link to verify your account : { " + url + " }");
    }
}
