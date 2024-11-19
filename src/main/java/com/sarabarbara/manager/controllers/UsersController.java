package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.users.*;
import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.responses.SearchResponse;
import com.sarabarbara.manager.responses.users.CreateUserResponse;
import com.sarabarbara.manager.responses.users.LoginUserResponse;
import com.sarabarbara.manager.responses.users.UpdateUserResponse;
import com.sarabarbara.manager.services.UsersService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sarabarbara.manager.mappers.UsersMapper.*;

/**
 * UsersController class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 29/09/2024
 */

@RestController
@AllArgsConstructor
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    private final UsersService usersService;

    /**
     * The Register Controller
     */

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> register(@Validated @RequestBody Users user) throws UserValidateException {

        try {

            logger.info("Creating user started");

            Users createdUser = usersService.createUser(user);

            UserCreateDTO userCreateDTO = toUserCreateDTOMapper(createdUser.getName(), createdUser.getUsername(),
                    createdUser.getEmail(), createdUser.getGenre(), createdUser.getProfilePictureURL(),
                    createdUser.getPremium());

            logger.info("Creating user finished");

            logger.info("User created successfully: {}", userCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateUserResponse(true, userCreateDTO,
                    "User created successfully"));

        } catch (UserValidateException ue) {

            logger.error("User creation failed. {}", ue.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CreateUserResponse(false, null, ue.getMessage()));

        } catch (Exception e) {

            logger.error("Can't create user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateUserResponse(false, null,
                            "Can't create user: Some internal error occurred."));
        }
    }

    /**
     * The Search User Controller
     */

    @PostMapping("/search/users")
    public ResponseEntity<SearchResponse<UserSearchDTO>> searchUser(@RequestBody String identifier,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {

        try {

            logger.info("Searching user started");

            List<Users> searchedUser = usersService.searchUser(identifier, page - 1, size);

            if (searchedUser.isEmpty()) {

                logger.info("No users found for identifier: {}", identifier);

                logger.info("Searching user finished without content");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            List<UserSearchDTO> userSearchDTOs = toUserSearchDTOMapper(searchedUser);

            int totalPage = (int) Math.ceil((double) searchedUser.size() / size);
            SearchResponse<UserSearchDTO> response = new SearchResponse<>(
                    userSearchDTOs, searchedUser.size(), page, totalPage);

            logger.info("Total users found with identifier {} : {}. Current page: {}. Total pages: {}", identifier,
                    searchedUser.size(), page, totalPage);
            logger.info("Users found:");
            userSearchDTOs.forEach(user -> logger.info("  - {}", user.getUsername()));


            logger.info("Searching user finished");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("Can't search user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new SearchResponse<>(null, 0, 0, 0));
        }

    }

    /**
     * The Update Controller
     */

    @PatchMapping("/{identifier}/update")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable String identifier, @RequestBody UserDTO user)
            throws UserValidateException {

        try {

            logger.info("Updating user started");

            Users updatedUser = usersService.updateUser(identifier, user);

            UserUpdateDTO userUpdateDTO = toUserUpdateDTOMapper(updatedUser.getName(), updatedUser.getUsername(),
                    updatedUser.getEmail(), updatedUser.getGenre(),
                    updatedUser.getProfilePictureURL(), updatedUser.getPremium());

            logger.info("User updated successfully: {}", userUpdateDTO);

            logger.info("Updating user finished");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new UpdateUserResponse(true, userUpdateDTO, "User updated successfully"));

        } catch (UserValidateException ue) {

            logger.info(ue.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new UpdateUserResponse(false, null, ue.getMessage()));

        } catch (Exception e) {

            logger.error("Can't update user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdateUserResponse(false, null,
                            "Can't update user: Some internal error occurred."));
        }

    }

    /**
     * The Delete Controller
     */

    @DeleteMapping("/settings/{identifier}")
    public ResponseEntity<String> deleteUser(@PathVariable String identifier) {

        try {

            logger.info("Deleting user started");

            usersService.deleteUser(identifier);

            logger.info("Deleting user finished");
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");

        } catch (Exception e) {

            logger.error("Can't delete user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Can't delete user: Some internal error occurred.");
        }
    }

    /**
     * The Login Controller
     */

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody UserLoginDTO user) throws UserNotFoundException {

        try {

            logger.info("Logging user started");

            boolean loginUser = usersService.loginUser(user);

            if (!loginUser) {

                logger.error("Can't logged the user. Ensure the email/username and password are correct");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginUserResponse(false,
                                "Can't logged the user. Ensure the email/username and password are correct"));

            }

            logger.info("Logged successfully");
            logger.info("Logging user finished");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginUserResponse(true, "Logged successfully"));

        } catch (Exception e) {

            logger.error("Can't logged user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginUserResponse(false, "Can't logged the user: Some internal error occurred."));
        }
    }

}