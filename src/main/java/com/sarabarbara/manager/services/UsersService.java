package com.sarabarbara.manager.services;

import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserSearchDTO;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserRegistrationException;
import com.sarabarbara.manager.exceptions.UserUpdateException;
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

    public Users createUser(Users user) {

        logger.info("Creating user: {}", user);

        logger.info("Validating username...");
        usersUtils.usernameValidator(user.getUsername());

        logger.info("Validating email...");
        usersUtils.emailValidator(user.getEmail());

        logger.info("Validating password...");
        isValidPassword(user.getPassword());

        if (isValidPassword(user.getPassword())) {

            logger.info("Encoding password...");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users savedUser = userRepository.save(user);

            logger.info("User created successfully: {}", savedUser);
            return savedUser;
        } else {

            throw new UserRegistrationException(" - Can't register the user");
        }

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

        PageRequest pageRequest = PageRequest.of(page, size);

        logger.info("Searching user: {}, Page: {}, Size: {}", identifier, page, size);

        Page<Users> userPage = userRepository.findAllByUsernameContaining(identifier, pageRequest);

        if (userPage.isEmpty()) {

            throw new UserNotFoundException("User not found");
        }

        List<UserSearchDTO> userDTOs = userPage.stream()
                .map(user -> UserSearchDTO.builder()
                        .username(user.getUsername())
                        .profilePictureURL(user.getProfilePictureURL())
                        .build())
                .toList();  // immutable list

        int totalPages = userPage.getTotalPages();

        logger.info("Users found: {}, Page number: {}, Total pages: {}", userDTOs.size(), page, totalPages);

        return new SearchResponse<>(userDTOs, (int) userPage.getTotalElements(), page, totalPages);
    }


    /**
     * Updates the user information
     *
     * @param identifier the identifier
     * @param newInfo    the newInfo
     */

    public Users updateUser(String identifier, UserDTO newInfo) {

        Optional<Users> optionalUser = userRepository.findByUsernameIgnoreCase(identifier);

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
            to the updatedUser object (the existing user in the database). */
            modelMapper.map(newInfo, existingUser);

            logger.info("Updating user {}...", existingUser.getUsername());
            return userRepository.save(existingUser);

        }

        logger.error("User with identifier {} can't be updated", identifier);
        throw new UserUpdateException("Can't update the user");
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
            throw new UserNotFoundException("User with username" + identifier + " not found");
        }
    }

}
