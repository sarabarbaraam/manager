package com.sarabarbara.manager.services;

import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UserDTO;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserRegistrationException;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        final Pattern pattern = Pattern.compile(
                "^(?=.*?[A-Z].*?)(?=.*?[a-z].*?)(?=.*?\\d.*?)(?=.*?[!@#$%^&*()_+=-].*?)[A-Za-z\\d!@#$%^&*()_+=-]{8,70}$"
        );
        final Matcher matcher = pattern.matcher(user.getPassword());

        if (matcher.matches()) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users savedUser = userRepository.save(user);

            logger.info("User created successfully: {}", savedUser);
            return savedUser;
        }

        throw new UserRegistrationException("Invalid password format");
    }


    /**
     * Search for a user in the BBDD
     *
     * @param identifier the identifier
     *
     * @return the user
     */

    public SearchResponse<UserDTO> searchUser(String identifier) {

        int page = 0;
        Optional<Users> optionalUser = userRepository.findByUsername(identifier);
        logger.info("Searching user: {}", identifier);

        if (optionalUser.isPresent()) {

            Users user = optionalUser.get();

            UserDTO dto =
                    UserDTO.builder().username(user.getUsername()).profilePictureURL(user.getProfilePictureURL()).build();

            List<UserDTO> userList = new ArrayList<>();
            userList.add(dto);

            logger.info("User found: {}, Page number: {}", user.getUsername(), page);

            return new SearchResponse<>(userList, userList.size(), page, 1);
        }

        return new SearchResponse<>(Collections.emptyList(), 0, page, 0);
    }


    /**
     * Updates the user information
     *
     * @param identifier the identifier
     * @param newInfo    the newInfo
     */

    public Users updateUser(String identifier, Users newInfo) {

        Optional<Users> optionalUser = userRepository.findByUsername(identifier);

        logger.info("Updating user: {}", optionalUser);

        if (optionalUser.isPresent()) {

            Users updatedUser = optionalUser.get();

            logger.info("New user info: {}", newInfo);

            // ignores the id field
            modelMapper.typeMap(Users.class, Users.class).addMappings(mapper -> mapper.skip(Users::setId));

            /* copies the values of the Users object (newInfo, any non-null field)
            to the updatedUser object (the existing user in the database). */
            modelMapper.map(newInfo, updatedUser);

            if (!newInfo.getPassword().isEmpty()) {
                final Pattern pattern = Pattern.compile(
                        "^(?=.*?[A-Z].*?)(?=.*?[a-z].*?)(?=.*?\\d.*?)(?=.*?[!?/@#$%^&*()_+=-].*?)[A-Za-z\\d!?/@#$%^&*()" +
                                "_+=-]{8,70}$"
                );
                final Matcher matcher = pattern.matcher(newInfo.getPassword());

                if (matcher.matches()) {
                    updatedUser.setPassword(passwordEncoder.encode(newInfo.getPassword()));
                } else {
                    throw new IllegalArgumentException("The new password does not meet the security requirements. Special characters allowed: !?/@#$%^&*()_+=-");
                }
            }

            return userRepository.save(updatedUser);
        }

        logger.error("User with identifier {} not found", identifier);
        throw new UserNotFoundException("User with identifier " + identifier + " not found");
    }

    /**
     * Delete a user from the BBDD
     *
     * @param identifier the identifier
     */

    public void deleteUser(String identifier) {

        Optional<Users> optionalUser = userRepository.findByUsername(identifier);
        if (optionalUser.isPresent()) {

            logger.info("Deleting user: {}", optionalUser);

            optionalUser.ifPresent(userRepository::delete);
        }
    }

}
