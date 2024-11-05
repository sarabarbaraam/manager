package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.CreateResponse;
import com.sarabarbara.manager.dto.LoginResponse;
import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.UserCreateDTO;
import com.sarabarbara.manager.dto.users.UserDTO;
import com.sarabarbara.manager.dto.users.UserLoginDTO;
import com.sarabarbara.manager.dto.users.UserSearchDTO;
import com.sarabarbara.manager.models.Users;
import com.sarabarbara.manager.services.UsersService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    private final UsersService usersService;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    /**
     * The Register Controller
     */

    @PostMapping("/register")
    public ResponseEntity<CreateResponse> register(@Validated @RequestBody Users user) {

        UserCreateDTO userCreateDTO;

        try {

            logger.info("Creating user started");

            CreateResponse response = usersService.createUser(user);

            if (response.isSuccess()) {

                userCreateDTO = response.getUser();

                logger.info("User {} created successfully", user.getUsername());
                logger.info("Creating user finished");

                return ResponseEntity.status(HttpStatus.CREATED).body(new CreateResponse(true, userCreateDTO,
                        "User created successfully"));
            }

            logger.error("User creation failed: {} ", response.getMessage());
            logger.info("Creating user finished");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {

            logger.error("Can't create user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CreateResponse(false, null, "Can't create user: Some internal error occurred."));
        }
    }

    /**
     * The Search User Controller
     */

    @PostMapping("/search/users")
    public ResponseEntity<SearchResponse<UserSearchDTO>> searchUser(@RequestBody String identifier,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {

        try {

            logger.info("Searching user started");

            SearchResponse<UserSearchDTO> response = usersService.searchUser(identifier, page, size);

            if (response.getResults().isEmpty()) {

                logger.info("No users found for identifier: {}", identifier);
                logger.info("Searching user finished");

                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            logger.info("Total users found with identifier {} : {}", identifier, response.getTotalResults());

            for (UserSearchDTO user : response.getResults()) {

                logger.info("Users found: - {} ", user.getUsername());
            }

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
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable String identifier, @RequestBody UserDTO user) {

        try {

            logger.info("Updating user started");

            UpdateUserResponse response = usersService.updateUser(identifier, user);

            if (response.isSuccess()) {

                logger.info("User updated successfully");
                logger.info("Updating user finished");

                return ResponseEntity.status(HttpStatus.OK).body(response);

            }

            logger.error("User update failed: {} ", response.getMessage());
            logger.info("updating user finished");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception e) {

            logger.error("Can't update user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdateUserResponse(false, null, "Can't update user: Some internal error occurred."));
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
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginDTO user) {

        try {

            logger.info("Logging user started");

            LoginResponse response = usersService.loginUser(user);

            logger.info("Logging user finished");
            return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                    .body(response);

        } catch (Exception e) {

            logger.error("Can't logging user: Some internal error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(false, "Can't logged the user: Some internal error occurred."));
        }

    }

}