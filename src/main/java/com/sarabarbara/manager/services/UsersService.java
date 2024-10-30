package com.sarabarbara.manager.services;

import com.sarabarbara.manager.dto.CreateResponse;
import com.sarabarbara.manager.dto.LoginResponse;
import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.*;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import com.sarabarbara.manager.utils.UsersUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.sarabarbara.manager.utils.UsersUtils.isValidPassword;

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

    private final UserRepository userRepository;
    private final UsersUtils usersUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    /**
     * Create a user in the BBDD
     *
     * @param user the user
     */

    public CreateResponse createUser(Users user) {

        logger.info("Creating user: {}", user);

        logger.info("Validating username...");
        usersUtils.usernameValidator(user.getUsername());

        logger.info("Validating email...");
        usersUtils.emailValidator(user.getEmail());

        logger.info("Validating password...");
        isValidPassword(user.getPassword());

        UserCreateDTO userCreateDTO = null;

        if (isValidPassword(user.getPassword())) {

            logger.info("Encoding password...");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users savedUser = userRepository.save(user);

            logger.info("User created successfully: {}", savedUser);

            userCreateDTO =
                    UserCreateDTO.builder().name(user.getName()).username(user.getUsername()).email(user.getEmail())
                            .genre(user.getGenre()).profilePictureURL(user.getProfilePictureURL()).premium(user.getPremium()).build();

            return new CreateResponse(true, userCreateDTO, "User created successfully");
        }

        return new CreateResponse(false, userCreateDTO, "Can't create user: ");
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

    public SearchResponse<UserSearchDTO> searchUser(String identifier, int page, int size) {

        // page number (default = 0) and the number of elements in the page
        PageRequest pageRequest = PageRequest.of(page, size);

        logger.info("Searching user: {}, Page: {}, Size: {}", identifier, page, size);

        Page<Users> userPage = userRepository.findAllByUsernameContaining(identifier, pageRequest);

        List<UserSearchDTO> userDTO = userPage.stream()
                .map(user -> UserSearchDTO.builder()
                        .username(user.getUsername())
                        .profilePictureURL(user.getProfilePictureURL())
                        .build())
                .toList();  // immutable list

        int totalPages = userPage.getTotalPages();

        if (userPage.isEmpty()) {

            return new SearchResponse<>(userDTO, (int) userPage.getTotalElements(), page, totalPages);
        }

        logger.info("Users found: {}, Page number: {}, Total pages: {}", userDTO.size(), page, totalPages);

        return new SearchResponse<>(userDTO, (int) userPage.getTotalElements(), page, totalPages);
    }


    /**
     * Updates the user information
     *
     * @param identifier the identifier
     * @param newInfo    the newInfo
     */

    public UpdateUserResponse updateUser(String identifier, UserDTO newInfo) {

        Optional<Users> optionalUser = userRepository.findByUsernameIgnoreCase(identifier);
        UserUpdateDTO userUpdateDTO = null;

        logger.info("Updating user: {}", optionalUser);

        if (optionalUser.isPresent()) {

            Users existingUser = optionalUser.get();

            logger.info("New user info: {}", newInfo);

            // checks that the new password is valid and username/email are unique
            usersUtils.checks(newInfo, existingUser);

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

            userUpdateDTO =
                    UserUpdateDTO.builder().name(existingUser.getName()).username(existingUser.getUsername()).email(existingUser.getEmail())
                    .genre(existingUser.getGenre()).profilePictureURL(existingUser.getProfilePictureURL()).premium(existingUser.getPremium()).build();
            logger.info("User {} updated successfully", existingUser);
            return new UpdateUserResponse("User updated successfully", userUpdateDTO);

        }

        logger.error("User with identifier {} can't be updated", identifier);
        return new UpdateUserResponse("Can't update user: ", userUpdateDTO);
    }

    /**
     * Delete a user from the BBDD
     *
     * @param identifier the identifier
     */

    public void deleteUser(String identifier) {

        Optional<Users> optionalUser = userRepository.findByUsernameIgnoreCase(identifier);
        Long id;

        if (optionalUser.isPresent()) {

            id = optionalUser.get().getId();

            logger.info("Deleting user: {}", optionalUser);
            userRepository.deleteById(id);

            logger.info("User with id {} (username: {}) has been deleted successfully.", id, identifier);
        } else {

            logger.error("User with username {} not found", identifier);
            throw new UserNotFoundException("User with username " + identifier + " not found");
        }
    }

    /**
     * Login a user
     *
     * @param user the user
     *
     * @return the LoginResponse
     */

    public LoginResponse loginUser(UserLoginDTO user) {

        logger.info("Logging user (identifier: {})", user);
        logger.info("Checking if the user exist...");

        List<Optional<Users>> userExistence = usersUtils.userExist(user);
        Optional<Users> optionalUsername = userExistence.get(0);
        Optional<Users> optionalEmail = userExistence.get(1);

        if (optionalUsername.isPresent() || optionalEmail.isPresent()) {

            logger.info("Checking user information...");
            if ((optionalEmail.isPresent() && passwordEncoder.matches(user.getPassword(),
                    optionalEmail.get().getPassword())) || (optionalUsername.isPresent() && passwordEncoder.matches(user.getPassword(),
                    optionalUsername.get().getPassword()))) {

                logger.info("Logged successfully");
                return new LoginResponse(true, "Logged successfully");
            }
        }

        logger.info("Username/email or password are incorrect");
        return new LoginResponse(false, "Can't logged the user. Ensure the email/username and password are correct");
    }

}
