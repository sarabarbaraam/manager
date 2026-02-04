package com.sarabarbara.manager.users;

import com.sarabarbara.manager.infrastructure.external.zerobounce.EmailValidationResult;
import com.sarabarbara.manager.infrastructure.external.zerobounce.ZeroBounceClient;
import com.sarabarbara.manager.security.AuthService;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlan;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlanEnum;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlanRepository;
import com.sarabarbara.manager.subscriptions.exceptions.SubscriptionPlanNotFoundException;
import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.exceptions.UserNotFoundException;
import com.sarabarbara.manager.users.exceptions.UserValidateException;
import com.sarabarbara.manager.users.exceptions.UsersException;
import com.sarabarbara.manager.users.requestes.UpdateUserRequest;
import com.sarabarbara.manager.users.requestes.UserRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.sarabarbara.manager.shared.constants.UsersConstants.PASSWORD_PATTERN;
import static com.sarabarbara.manager.shared.constants.UsersConstants.PASSWORD_REGEX;

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
    private final SubscriptionsPlanRepository subscriptionsPlanRepository;
    private final ZeroBounceClient zeroBounceClient;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;
    private final AuthService authService;

    // todo: send verification email after user creation
    @Override
    public CreateUserDTO createUser(UserRequest request) {

        log.info("UsersServiceImpl - createUser called");
        log.debug("Creating the user with the following data: {}", request);

        log.debug("Validating new user data...");
        newUserValidator(request);

        log.debug("Validating email...");
        EmailValidationResult emailResult = emailValidator(request.email());

        Users user = usersMapper.toEntity(request);

        log.debug("Setting email as verified...");
        user.setEmailVerified(emailResult.verified());

        log.debug("Encoding password...");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.debug("Assigning USER role to the new user...");
        user.setRole(RolesEnum.USER);

        log.debug("Assigning FREE subscription plan to the new user...");
        SubscriptionsPlan freePlan = subscriptionsPlanRepository
                .findByName(SubscriptionsPlanEnum.FREE)
                .orElseThrow(() -> new SubscriptionPlanNotFoundException("FREE plan not found"));

        user.setCurrentPlan(freePlan);
        user.setActive(true);

        log.info("User created successfully: {}", user);
        userRepository.save(user);

        return usersMapper.toCreateUserDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersDTO> getUsers(int page, int size) {

        log.info("UsersServiceImpl - getUsers called");
        log.debug("Fetching all users from the database...");

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Users> users = userRepository.findAll(pageRequest);

        if (users.isEmpty()) {

            log.error("No users found in the database.");
            throw new UsersException("No users found in the database.");
        }

        return usersMapper.toDTOList(users.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsersDTO> getUserByUsername(String username, int page, int size) throws UserNotFoundException {

        log.info("UsersServiceImpl - getUserByUsername called");

        log.debug("Searching users with username: {}", username);
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Users> searchedUser = userRepository.findByUsernameContainingIgnoreCaseAndActiveTrue(username, pageRequest);

        if (searchedUser.isEmpty()) {

            log.error("No users found matching the username: {}", username);
            throw new UserNotFoundException("No users found matching the username: " + username);
        }


        log.info("Users found: {}", searchedUser);
        return usersMapper.toDTOList(searchedUser.getContent());
    }

    @Override
    public UsersDTO updateUser(@NotNull UpdateUserRequest request) throws UserNotFoundException {

        log.info("UsersServiceImpl - updateUser called");

        Long id = authService.getCurrentUserId();
        log.debug("Updating user with id: {}", id);

        Users existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + id + " not found."));

        log.debug("Existing user data: {}", existingUser);

        if (request.username() != null && !request.username().equalsIgnoreCase(existingUser.getUsername())) {

            log.debug("Validating new username: {}", request.username());
            usernameValidator(request.username());

            existingUser.setUsername(request.username());
        }

        if (request.email() != null && !request.email().equalsIgnoreCase(existingUser.getEmail())) {

            log.debug("Validating new email: {}", request.email());
            emailValidator(request.email());

            existingUser.setEmail(request.email());
        }

        if (request.password() != null) {

            log.debug("Validating and encoding new password");
            passwordValidator(request.password());

            existingUser.setPassword(passwordEncoder.encode(request.password()));
        }

        log.debug("Saving updated user data...");
        Users updatedUser = userRepository.save(usersMapper.updateEntityFromRequest(request, existingUser));

        log.info("User updated successfully: {}", updatedUser);
        return usersMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser() {

        log.info("UsersServiceImpl - deleteUser called");
        log.debug("Deactivating user");

        Long userId = authService.getCurrentUserId();

        Users user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id " + userId + " not found."));

        user.setActive(false);
        userRepository.save(user);

        log.info("User deactivated successfully");

    }

    // ----------------------------------- Complementary methods -----------------------------------

    // ============ VALIDATIONS ============

    private void newUserValidator(@NonNull UserRequest user) throws UserValidateException {

        log.debug("Validating username...");
        usernameValidator(user.username());

        log.debug("Validating password...");
        passwordValidator(user.password());
    }

    public void usernameValidator(String username) throws UserValidateException {

        Optional<Users> optionalUsername = userRepository.findByUsernameIgnoreCase(username);

        if (optionalUsername.isPresent()) {

            log.error("The username {} is already taken.", username);
            throw new UserValidateException("The username " + username + " is already taken.");
        }

        log.info("The username {} is available", username);
    }

    public EmailValidationResult emailValidator(String email) throws UserValidateException {

        Optional<Users> optionalEmail = userRepository.findByEmail(email);

        if (optionalEmail.isPresent()) {

            log.error("The email {} is already taken.", email);
            throw new UserValidateException("The email " + email + " is already taken.");
        }

        EmailValidationResult result = zeroBounceClient.emailIsReal(email);

        if (!result.valid()) {

            log.error("Email not acceptable: {}", result.reason());
            throw new UserValidateException("Email not acceptable: " + result.reason());
        }

        return result;
    }

    public void passwordValidator(String password) {

        final Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        if (!pattern.matcher(password).matches()) {

            log.error("Password doesn't match pattern");
            throw new UserValidateException(PASSWORD_PATTERN);
        }

        log.debug("The password's format is correct");
    }
}
