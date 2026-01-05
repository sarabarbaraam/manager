package com.sarabarbara.manager.users;

import com.sarabarbara.manager.infrastructure.external.zerobounce.ZeroBounceClient;
import com.sarabarbara.manager.security.AuthService;
import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.exceptions.UserNotFoundException;
import com.sarabarbara.manager.users.exceptions.UserValidateException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sarabarbara.manager.shared.constants.UsersConstants.PASSWORD_PATTERN;

/**
 * UsersServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 29/12/2025
 */

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final ZeroBounceClient zeroBounceClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;
    private final AuthService authService;

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

        newUserValidator(request);

        Users user = usersMapper.toEntity(request);

        log.debug("Encoding password...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.debug("Assigning USER role to the new user...");
        user.setRole(Collections.singleton(RolesEnum.USER));

        log.info("User created successfully: {}", user);
        userRepository.save(user);

        return usersMapper.toCreateUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersDTO> getUsers() {

        log.info("UsersServiceImpl - getUsers called");
        log.debug("Fetching all users from the database...");

        List<Users> user = userRepository.findAll();

        return usersMapper.toDTOList(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersDTO> getUserByUsername(String username, int page, int size) throws UserNotFoundException {

        log.info("UsersServiceImpl - getUserByUsername called");

        log.debug("Searching users with username: {}", username);
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Users> searchedUser = userRepository.findByUsernameIgnoreCaseAndActiveTrue(username, pageRequest);

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

        log.info("UsersServiceImpl - deleteUser called");
        log.debug("Deleting user");

        Long userId = authService.getCurrentUserId();

        Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + userId + " not found."));

        user.setActive(false);

        log.info("User deactivated successfully");

    }

    // ----------------------------------- Complementary methods -----------------------------------

    // ============ VALIDATIONS ============

    /**
     * Validate if the new user data is correct
     *
     * @param user the user's data
     * @throws UserValidateException the {@link UserValidateException}
     */

    private void newUserValidator(@NonNull UserRequest user) throws UserValidateException {

        log.debug("Validating username...");
        usernameValidator(user.username());

        log.debug("Validating email...");
        emailValidator(user.email());

        log.debug("Validating password...");
        if (!isFormatPasswordCorrect(user.password())) {

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

    public void emailValidator(String email) throws UserValidateException {

        Optional<Users> optionalEmail = userRepository.findByEmail(email);

        if (optionalEmail.isPresent()) {

            log.error("The email {} is already taken.", email);
            throw new UserValidateException("The email " + email + " is already taken.");
        }

        log.debug("The email {} is available", email);
        zeroBounceClient.emailIsReal(email);
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
