package com.sarabarbara.manager.utils;

import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * UsersUtils class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/10/2024
 */

@Component
@AllArgsConstructor
public class UsersUtils {

    private static final Logger logger = LoggerFactory.getLogger(UsersUtils.class);
    private final UserRepository userRepository;
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Checks if the password, username and email are correct
     *
     * @param newInfo      the newInfo
     * @param existingUser the existingUser
     */
    public void checks(UserDTO newInfo, Users existingUser) {

        if (newInfo.getPassword() != null && !newInfo.getPassword().isBlank()) {

            passwordValidator(newInfo, existingUser);
        }

        if (newInfo.getUsername() != null && !newInfo.getUsername().isBlank()) {

            usernameValidator(newInfo.getUsername());
        }

        if (newInfo.getEmail() != null && !newInfo.getEmail().isBlank()) {

            emailValidator(newInfo.getEmail());
        }
    }

    /**
     * Validates the password format
     *
     * @param newInfo      the newInfo
     * @param existingUser the updatedUser
     */
    public void passwordValidator(UserDTO newInfo, Users existingUser) {

        if (newInfo.getPassword() != null && !newInfo.getPassword().isBlank()) {

            if (!isValidPassword(newInfo.getPassword())) {

                throw new UserValidateException("The password does not meet the security requirements. " +
                        "Special characters allowed: !?/@#$%^&*()_+=-");
            }

            logger.info("Password is valid, proceeding to encrypt");
            existingUser.setPassword(passwordEncoder.encode(newInfo.getPassword()));

            logger.info("Encrypted password: {}", existingUser.getPassword());
            userRepository.save(existingUser);
        }
    }

    /**
     * Validates if the password is correct
     *
     * @param password password
     *
     * @return the pattern
     */
    public static boolean isValidPassword(String password) {

        final Pattern pattern = Pattern.compile(
                "^(?=.*?[A-Z].*?)(?=.*?[a-z].*?)(?=.*?\\d.*?)(?=.*?[!?/@#$%^&*()_+=-].*?)[A-Za-z\\d!?/@#$%^&*()" +
                        "_+=-]{8,70}$"
        );

        if (!pattern.matcher(password).matches()) {

            throw new UserValidateException("The password does not meet the security requirements. " +
                    "Special characters allowed: !?/@#$%^&*()_+=-");
        }

        return pattern.matcher(password).matches();

    }

    /**
     * Validates if the username is taken
     *
     * @param username username
     */
    public void usernameValidator(String username) {

        Optional<Users> optionalUsername = userRepository.findByUsernameIgnoreCase(username);

        if (optionalUsername.isPresent()) {
            throw new UserValidateException("The username " + username + " is already taken.");
        }

        logger.info("The username {} is available", username);
    }

    /**
     * Validates if the email is taken
     *
     * @param email email
     */
    public void emailValidator(String email) {

        Optional<Users> optionalEmail = userRepository.findByEmail(email);

        if (optionalEmail.isPresent()) {
            throw new UserValidateException("The email " + email + " is already taken.");
        }

        logger.info("The email {} is available", email);
    }

    public boolean userExist(UserLoginDTO user) {

        Optional<Users> optionalUsername = userRepository.findByUsernameIgnoreCase(user.getUsername());
        Optional<Users> optionalEmail = userRepository.findByEmail(user.getEmail());

        logger.info("The user exists");
        return optionalUsername.isPresent() || optionalEmail.isPresent();
    }

}
