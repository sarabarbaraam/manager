package com.sarabarbara.manager.services;

import com.sarabarbara.manager.apis.ZeroBounceAPI;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sarabarbara.manager.constants.Constants.SPECIAL_CHARACTERS_ALLOWED;
import static com.sarabarbara.manager.constants.Constants.USER_NOT_FOUND;
import static com.sarabarbara.manager.utils.UsersUtils.isFormatPasswordCorrect;

/**
 * UsersService class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/10/2024
 */

@Service
@Transactional
@AllArgsConstructor
public class UsersService {

    private UserRepository userRepository;
    private final ZeroBounceAPI zeroBounceAPI;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper modelMapper = new ModelMapper();

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
    private StringBuilder errorMessages;

    /**
     * Create a user in the BBDD
     *
     * @param user the user
     *
     * @return the creation of the user
     *
     * @throws UserValidateException the {@link UserValidateException}
     */

    public Users createUser(Users user) throws UserValidateException {

        logger.info("Creating user: {}", user);
        validateNewUser(user);

        logger.info("Encoding password...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        logger.info("User created successfully: {}", user);
        return userRepository.save(user);
    }

    /**
     * Search a user in the BBDD
     *
     * @param identifier the identifier
     * @param page       the page
     * @param size       the size
     *
     * @return the results of the search
     */

    public List<Users> searchUser(String identifier, int page, int size) {

        // page number (default = 0) and the number of elements in the page
        PageRequest pageRequest = PageRequest.of(page, size);

        logger.info("Searching user: {}, Page: {}, Size: {}", identifier, page, size);

        Page<Users> userPage = userRepository.findAllByUsernameContainingIgnoreCase(identifier, pageRequest);

        logger.info("Users found: {}, Page number: {}, Total pages: {}", userPage.getTotalElements(), page,
                userPage.getTotalPages());
        return userPage.getContent();
    }


    /**
     * Updates the user information
     *
     * @param identifier the identifier
     * @param newInfo    the newInfo
     *
     * @return the updated user
     *
     * @throws UserValidateException the {@link UserValidateException}
     */

    public Users updateUser(String identifier, UserDTO newInfo) throws UserValidateException {

        Optional<Users> optionalUser = userRepository.findByUsernameIgnoreCase(identifier);

        logger.info("Updating user: {}", optionalUser);

        if (optionalUser.isPresent()) {

            Users existingUser = optionalUser.get();

            logger.info("New user info: {}", newInfo);

            // checks that the new password is valid and username/email are unique
            checks(newInfo, existingUser);

            // ignores the id field
            modelMapper.typeMap(Users.class, Users.class).addMappings(mapper -> mapper.skip(Users::setId));
            // ignores the password field because otherwise it does not encrypt it
            modelMapper.typeMap(UserDTO.class, Users.class).addMappings(mapper -> mapper.skip(Users::setPassword));
            // ignores the null fields
            modelMapper.getConfiguration().setSkipNullEnabled(true);

            /* copies the values of the Users object (newInfo, any non-null field)
            to the existingUser object. */
            modelMapper.map(newInfo, existingUser);

            logger.info("Updating user {}...", existingUser.getUsername());
            userRepository.save(existingUser);


            logger.info("User {} updated successfully", existingUser);
            return existingUser;
        }

        logger.error("User with identifier {} can't be updated: user not found", identifier);
        throw new UserNotFoundException("Can't update user: User not found");
    }

    /**
     * Delete a user from the BBDD
     *
     * @param identifier the identifier
     *
     * @throws UserNotFoundException the {@link UserNotFoundException}
     */

    public void deleteUser(String identifier) throws UserNotFoundException {

        Optional<Users> optionalUser = userRepository.findByUsernameIgnoreCase(identifier);


        if (optionalUser.isEmpty()) {

            logger.error(USER_NOT_FOUND);
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        Long id = optionalUser.get().getId();

        logger.info("Deleting user: {}", optionalUser);
        userRepository.deleteById(id);

        logger.info("User with id {} (username: {}) has been deleted successfully.", id, identifier);

    }

    /**
     * Login a user
     *
     * @param user the user
     *
     * @return true or false
     */

    public boolean loginUser(@RequestBody UserLoginDTO user) {

        logger.info("Logging user (identifier: {})", user);
        logger.info("Checking if the user exist...");

        Optional<Users> userExistence = userExist(user);

        logger.info("Checking user information...");

        if ((userExistence.isPresent() && passwordEncoder.matches(user.getPassword(),
                userExistence.get().getPassword()))) {

            logger.info("Logged successfully");

            return true;
        }

        logger.error("Credential doesn't match");
        return false;

    }

    // Complementary methods

    /**
     * Validate the info of new user is correct
     *
     * @param user the user
     */

    private void validateNewUser(@NonNull Users user) {

        logger.info("Validating username...");
        usernameValidator(user.getUsername());

        logger.info("Validating email...");
        emailValidator(user.getEmail());

        logger.info("Validating password...");
        if (!isFormatPasswordCorrect(user.getPassword())) {

            logger.error(SPECIAL_CHARACTERS_ALLOWED);

            throw new UserValidateException(SPECIAL_CHARACTERS_ALLOWED);

        }
    }

    /**
     * Checks if the password, username and email are correct
     *
     * @param newInfo      the newInfo
     * @param existingUser the updatedUser
     *
     * @throws UserValidateException the {@link UserValidateException}
     */

    public void checks(@NonNull UserDTO newInfo, Users existingUser) throws UserValidateException {

        if (newInfo.getUsername() != null && !newInfo.getUsername().isBlank()) {
            try {

                usernameValidator(newInfo.getUsername());

            } catch (UserValidateException e) {

                errorMessages = new StringBuilder();

                errorMessages.append("Username validation failed: ").append(e.getMessage());
                throw new UserValidateException(errorMessages);
            }
        }

        if (newInfo.getEmail() != null && !newInfo.getEmail().isBlank()) {
            try {

                emailValidator(newInfo.getEmail());

            } catch (UserValidateException e) {

                errorMessages = new StringBuilder();

                errorMessages.append("Email validation failed: ").append(e.getMessage());
                throw new UserValidateException(errorMessages);
            }
        }

        if (newInfo.getPassword() != null && !newInfo.getPassword().isBlank()) {
            try {

                passwordValidator(newInfo, existingUser);

            } catch (UserValidateException e) {

                errorMessages = new StringBuilder();

                logger.error("Password validation failed: {}", e.getMessage());
                errorMessages.append("Password validation failed: ").append(e.getMessage());
                throw new UserValidateException(errorMessages);
            }
        }

    }

    /**
     * Validates if the username is taken
     *
     * @param username the username
     */

    public void usernameValidator(String username) {

        Optional<Users> optionalUsername = userRepository.findByUsernameIgnoreCase(username);

        if (optionalUsername.isPresent()) {

            logger.error("The username {} is already taken.", username);
            throw new UserValidateException("The username " + username + " is already taken.");
        }

        logger.info("The username {} is available", username);
    }

    /**
     * Validates if the email is taken
     *
     * @param email the email
     */

    public void emailValidator(String email) {

        Optional<Users> optionalEmail = userRepository.findByEmail(email);

        if (optionalEmail.isPresent()) {

            logger.error("The email {} is already taken.", email);
            throw new UserValidateException("The email " + email + " is already taken.");
        }

        logger.info("The email {} is available", email);

        zeroBounceAPI.emailIsReal(email);
    }

    /**
     * Validates the password format
     *
     * @param newInfo      the newInfo
     * @param existingUser the updatedUser
     *
     * @throws UserValidateException the {@link UserValidateException}
     */

    private void passwordValidator(@NonNull UserDTO newInfo, Users existingUser) throws UserValidateException {

        if (!isFormatPasswordCorrect(newInfo.getPassword())) {

            logger.error("Invalid password.");
            throw new UserValidateException(SPECIAL_CHARACTERS_ALLOWED);
        }

        if (Objects.equals(newInfo.getPassword(), existingUser.getPassword())) {

            logger.error("The password can't be the same as the previous one");
            throw new UserValidateException("The password can't be the same as the previous one");
        }

        logger.info("Password is valid, proceeding to encrypt");
        existingUser.setPassword(passwordEncoder.encode(newInfo.getPassword()));

        logger.info("Encrypted password: {}", existingUser.getPassword());
        userRepository.save(existingUser);

    }

    /**
     * Checks if the user exist
     *
     * @param user the user
     *
     * @return the optional user
     */

    public Optional<Users> userExist(@NonNull UserLoginDTO user) {

        Optional<Users> optionalUsername = userRepository.findByUsernameIgnoreCase(user.getUsername());
        if (optionalUsername.isPresent()) {

            logger.info("User existence check completed.");
            return optionalUsername;
        }

        Optional<Users> optionalEmail = userRepository.findByEmail(user.getEmail());
        if (optionalEmail.isPresent()) {

            logger.info("User existence check completed.");
            return optionalEmail;
        }

        logger.error("User not exist");
        return Optional.empty();
    }

}
