package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.Model.UserModel;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.entity.VerificationToken;

import java.util.Optional;

public interface UserService
{
    User registerUser(UserModel userModel);
    void saveVerificationTokenForUser(String token, User user);
    String validateVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
    User findUserByEmail(String email);
    User getUserByUsername(String username);
    void createPasswordResetTokenForUser(User user, String token);
    String validatePasswordToken(String token);
    Optional<User> getUserByPasswordToken(String token);
    void changePassword(User user, String newPassword);
    boolean checkIfoldPasswordisValid(User user, String oldPassword);
}
