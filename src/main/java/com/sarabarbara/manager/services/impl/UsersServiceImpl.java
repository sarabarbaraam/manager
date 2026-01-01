package com.sarabarbara.manager.services.impl;

import com.sarabarbara.manager.apis.ZeroBounceAPI;
import com.sarabarbara.manager.dtos.UsersDTO;
import com.sarabarbara.manager.dtos.users.CreateUserDTO;
import com.sarabarbara.manager.entities.Users;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.repositories.UsersRepository;
import com.sarabarbara.manager.requestes.UserRequest;
import com.sarabarbara.manager.services.UsersService;
import com.sarabarbara.manager.utils.mappers.UsersMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sarabarbara.manager.utils.constants.UsersConstants.PASSWORD_PATTERN;


/**
 * UsersServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 29/12/2025
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final ZeroBounceAPI zeroBounceAPI;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;

    /**
     * Create a new user.
     *
     * @param request the user request
     * @return the created user data
     */

    @Override
    public CreateUserDTO createUser(UserRequest request) {

        log.info("UsersServiceImpl - createUser called");
        log.debug("Creating the user with the following data: {}", request);

        validateNewUser(request);

        Users user = usersMapper.toEntity(request);

        log.debug("Encoding password...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.info("User created successfully: {}", user);
        userRepository.save(user);

        return usersMapper.toCreateUserDTO(user);
    }

    @Override
    public List<UsersDTO> getUsers() {

        log.info("UsersServiceImpl - getUsers called");
        log.debug("Fetching all users from the database...");

        List<Users> user = userRepository.findAll();

        return usersMapper.toDTOList(user);
    }

    @Override
    public List<UsersDTO> getUserByUsername(String username, int page, int size) throws UserNotFoundException {

        log.info("UsersServiceImpl - getUserByUsername called");

        log.debug("Searching users with username: {}", username);
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Users> searchedUser = userRepository.findAllByUsernameContainingIgnoreCase(username, pageRequest);

        if (searchedUser.isEmpty()) {

            log.error("No users found matching the username: {}", username);
            throw new UserNotFoundException("No users found matching the username: " + username);
        }


        log.info("Users found: {}", searchedUser);
        return usersMapper.toDTOList(searchedUser.getContent());
    }

    @Override
    public UsersDTO updateUser(Long idUser, UserRequest request) throws UserNotFoundException {
        return null;
    }

    @Override
    public void deleteUser() {

    }


    // ----------------------------------- Complementary methods -----------------------------------

    // ============ VALIDATIONS ============

    /**
     * Validate if the new user data is correct
     *
     * @param user the user's data
     * @throws UserValidateException the {@link UserValidateException}
     */

    private void validateNewUser(@NonNull UserRequest user) {

        log.debug("Validating username...");
        usernameValidator(user.getUsername());

        log.debug("Validating email...");
        emailValidator(user.getEmail());

        log.debug("Validating password...");
        if (!isFormatPasswordCorrect(user.getPassword())) {

            log.error(PASSWORD_PATTERN);
            throw new UserValidateException(PASSWORD_PATTERN);
        }
    }

    public void usernameValidator(String username) throws UserValidateException {

        Optional<Users> optionalUsername = userRepository.findByUsernameIgnoreCase(username);

        if (optionalUsername.isPresent()) {

            log.error("The username {} is already taken.", username);
            throw new UserValidateException("The username " + username + " is already taken.");
        }

        log.info("The username {} is available", username);
    }

    /**
     * Validates if the email is taken or not
     *
     * @param email the email
     */

    public void emailValidator(String email) {

        Optional<Users> optionalEmail = userRepository.findByEmail(email);

        if (optionalEmail.isPresent()) {

            log.error("The email {} is already taken.", email);
            throw new UserValidateException("The email " + email + " is already taken.");
        }

        log.debug("The email {} is available", email);
        zeroBounceAPI.emailIsReal(email);
    }

    /**
     * Validates if the password format is correct
     *
     * @param password the password
     * @return true if the password format is correct, false otherwise
     */

    public static boolean isFormatPasswordCorrect(String password) {

        final Pattern pattern = Pattern.compile(
                "^(?=.*?[A-Z].*?)(?=.*?[a-z].*?)(?=.*?\\d.*?)(?=.*?[!?/@#$%^&*()_+=-].*?)[A-Za-z\\d!?/@#$%^&*()" +
                        "_+=-]{8,70}$"
        );

        if (!pattern.matcher(password).matches()) {

            log.info("Password doesn't match pattern");
            return false;
        }

        log.debug("The password's format is correct");
        return true;
    }
}
