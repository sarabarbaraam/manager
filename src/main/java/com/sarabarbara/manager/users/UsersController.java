package com.sarabarbara.manager.users;


import com.sarabarbara.manager.users.dtos.UsersDTO;
import com.sarabarbara.manager.users.dtos.CreateUserDTO;
import com.sarabarbara.manager.shared.BaseResponse;
import com.sarabarbara.manager.users.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sarabarbara.manager.shared.constants.APIConstants.*;
import static com.sarabarbara.manager.shared.constants.SwaggerUsersExamplesConstants.*;

/**
 * UsersController class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Schema(name = "Users Controller", description = "Controller for managing users")
public class UsersController {

    private final UsersService usersService;

    @Operation(summary = "Register an user",
            description = "Register an user for their name, username, password, email and profile picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_RESPONSE,
                                    summary = SUCCESS,
                                    value = REGISTER_USER_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_RESPONSE,
                                    summary = BAD_REQUEST,
                                    value = REGISTER_USER_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_RESPONSE,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = REGISTER_USER_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @PostMapping("/register")
    public BaseResponse<CreateUserDTO> registerUser(@Validated @RequestBody UserRequest request) {

        log.info("UsersController - createUser called");
        log.info("UsersController - createUser finished with data: {}", request);
        return BaseResponse
                .<CreateUserDTO>builder()
                .success(true)
                .data(usersService.createUser(request))
                .message("User created successfully")
                .build();
    }

    @Operation(summary = "Search users",
            description = "Search all users with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_RESPONSE,
                                    summary = SUCCESS,
                                    value = SEARCH_USER_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_RESPONSE,
                                    summary = BAD_REQUEST,
                                    value = SEARCH_USER_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_RESPONSE,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = SEARCH_USER_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @GetMapping
    public BaseResponse<List<UsersDTO>> getUsers(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size) {

        log.info("UsersController - getUsers called");
        log.info("UsersController - getUsers finished");
        return BaseResponse
                .<List<UsersDTO>>builder()
                .success(true)
                .data(usersService.getUsers())
                .message("Users retrieved successfully")
                .build();

    }

    @Operation(summary = "Searches an user",
            description = "Searches an user for their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_RESPONSE,
                                    summary = SUCCESS,
                                    value = SEARCH_USER_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "400", description = BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = BAD_REQUEST_RESPONSE,
                                    summary = BAD_REQUEST,
                                    value = SEARCH_USER_BAD_REQUEST_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_RESPONSE,
                                    summary = NOT_FOUND,
                                    value = SEARCH_USER_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_RESPONSE,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = SEARCH_USER_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @GetMapping("/search/users/{username}")
    public BaseResponse<List<UsersDTO>> searchUser(@PathVariable String username,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int size) {

        log.info("UsersController - searchUser called");
        log.info("UsersController - searchUser finished");
        return BaseResponse
                .<List<UsersDTO>>builder()
                .success(true)
                .data(usersService.getUserByUsername(username, page - 1, size))
                .message("Search completed successfully")
                .build();
    }

    // todo: updatecontroller

    @Operation(summary = "Delete an user",
            description = "Delete an user by the id from the authentication token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = SUCCESS_RESPONSE,
                                    summary = SUCCESS,
                                    value = DELETE_USER_SUCCESSFUL_RESPONSE
                            ))),
            @ApiResponse(responseCode = "404", description = NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = NOT_FOUND_RESPONSE,
                                    summary = NOT_FOUND,
                                    value = DELETE_USER_NOT_FOUND_RESPONSE
                            ))),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = INTERNAL_SERVER_ERROR_RESPONSE,
                                    summary = INTERNAL_SERVER_ERROR,
                                    value = DELETE_USER_INTERNAL_SERVER_ERROR_RESPONSE
                            )))
    })
    @DeleteMapping("/settings")
    public BaseResponse<String> deleteUser() {

        log.info("UsersController - deleteUser called");
        usersService.deleteUser();

        log.info("UsersController - deleteUser finished");
        return BaseResponse
                .<String>builder()
                .success(true)
                .message("User deleted successfully")
                .build();
    }

}