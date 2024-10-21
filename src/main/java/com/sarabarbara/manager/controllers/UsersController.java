package com.sarabarbara.manager.controllers;

import com.sarabarbara.manager.dto.SearchResponse;
import com.sarabarbara.manager.dto.UpdateUserResponse;
import com.sarabarbara.manager.dto.users.UserDTO;
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
    public ResponseEntity<UserDTO> register(@Validated @RequestBody Users user) {
        try {

            Users createdUser = usersService.createUser(user);

            UserDTO userDTO = UserDTO.builder()
                    .id(createdUser.getId())
                    .name(createdUser.getName())
                    .username(createdUser.getUsername())
                    .email(createdUser.getEmail())
                    .genre(createdUser.getGenre())
                    .profilePictureURL(createdUser.getProfilePictureURL())
                    .premium(createdUser.getPremium())
                    .build();


            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

        } catch (Exception e) {

            logger.error("Error creating user", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            SearchResponse<UserSearchDTO> response = usersService.searchUser(identifier, page, size);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            logger.error("User not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
                            .password(updatedUser.getPassword()).email(updatedUser.getEmail()).genre(updatedUser.getGenre())
                            .profilePictureURL(updatedUser.getProfilePictureURL()).premium(updatedUser.getPremium()).build();

            UpdateUserResponse response = UpdateUserResponse.builder()
                    .message("User updated successfully: ")
                    .user(userDTO)
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

}