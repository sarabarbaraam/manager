package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.CreateResponse;
import com.sarabarbara.manager.dto.LoginResponse;
import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.*;
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

        UserCreateDTO userCreateDTO = null;

        try {

            userCreateDTO = usersService.createUser(user).getUserCreateDTO();

            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateResponse(true, userCreateDTO,
                    "User successfully"));

        } catch (Exception e) {

            logger.error("Error creating user", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CreateResponse(false, userCreateDTO,
                    "Can't create the user: " + e.getMessage()));
        }
    }

    /**
     * The Search User Controller
     */

    @PostMapping("/search/users")
    public ResponseEntity<SearchResponse<UserSearchDTO>> searchUser(@RequestBody String identifier,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {

        SearchResponse<UserSearchDTO> response = usersService.searchUser(identifier, page, size);

        if (response.getResults().isEmpty()) {

            logger.info("No users found for identifier: {}", identifier);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    /**
     * The Update Controller
     */

    @PatchMapping("/{identifier}/update")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable String identifier, @RequestBody UserDTO user) {

        try {

            Users updatedUser = usersService.updateUser(identifier, user);
            UserDTO userDTO =
                    UserDTO.builder().id(updatedUser.getId()).name(updatedUser.getName()).username(updatedUser.getUsername())
                            .email(updatedUser.getEmail()).genre(updatedUser.getGenre())
                            .profilePictureURL(updatedUser.getProfilePictureURL()).premium(updatedUser.getPremium()).build();

            UpdateUserResponse response = UpdateUserResponse.builder()
                    .message("User updated successfully: ")
                    .user(UserUpdateDTO.builder().name(userDTO.getName()).username(userDTO.getUsername()).email(userDTO.getEmail())
                            .genre(userDTO.getGenre()).profilePictureURL(userDTO.getProfilePictureURL()).premium(userDTO.getPremium()).build())
                    .build();

            logger.info("User updated successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UpdateUserResponse("Can't update the user: "
                    + e.getMessage(), null));
        }

    }

    /**
     * The Delete Controller
     */

    @DeleteMapping("/settings/{identifier}")
    public ResponseEntity<String> deleteUser(@PathVariable String identifier) {

        try {

            usersService.deleteUser(identifier);

            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");

        } catch (Exception e) {

            logger.error("Error deleting user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can't delete user: " + e.getMessage());
        }
    }

    /**
     * The Login Controller
     */

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginDTO user) {
        LoginResponse response = usersService.loginUser(user);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED)
                .body(response);
    }

}