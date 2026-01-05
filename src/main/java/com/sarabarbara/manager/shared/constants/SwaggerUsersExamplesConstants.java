package com.sarabarbara.manager.shared.constants;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SwaggerConstants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "Swagger Constants", description = "Constants used for Swagger documentation")
public class SwaggerUsersExamplesConstants {

    private SwaggerUsersExamplesConstants() {
    }

    // ==================================== REGISTER ====================================

    public static final String REGISTER_USER_SUCCESSFUL_RESPONSE = """
            {
                 "success": true,
                 "userCreate": {
                   "name": "John Doe",
                   "username": "johndoe",
                   "email": "john.doe@example.com",
                   "userGenre": "MALE",
                   "profilePictureURL": "https://example.com/profiles/johndoe.jpg",
                   "premium": true
                 },
                 "message": "User created successfully"
            }
            """;

    public static final String REGISTER_USER_BAD_REQUEST_RESPONSE = """
            {
                  "success": false,
                  "userCreate": null,
                  "message": "Invalid input data: email is required"
            }
            """;

    public static final String REGISTER_USER_INTERNAL_SERVER_ERROR_RESPONSE = """
            {
               "success": false,
               "userCreate": null,
               "message": "An unexpected error occurred while creating the user"
            }
            """;

    // ==================================== SEARCH ====================================

    public static final String SEARCH_USER_SUCCESSFUL_RESPONSE = """
            {
               "results": [
                 {
                   "username": "alicesmith",
                   "profilePictureURL": "https://example.com/profiles/alice.jpg"
                 }
               ],
               "totalResults": 1,
               "currentPage": 1,
               "totalPage": 1,
               "message": "Search completed successfully"
            }
            """;

    public static final String SEARCH_USER_BAD_REQUEST_RESPONSE = """
            {
               "results": [],
               "totalResults": 0,
               "currentPage": 0,
               "totalPage": 0,
               "message": "Invalid search parameters: 'page' must be greater than 0"
            }
            """;

    public static final String SEARCH_USER_NOT_FOUND_RESPONSE = """
            {
               "results": [],
               "totalResults": 0,
               "currentPage": 0,
               "totalPage": 0,
               "message": "No users found matching the username"
            }
            """;

    public static final String SEARCH_USER_INTERNAL_SERVER_ERROR_RESPONSE = """
            {
               "results": [],
               "totalResults": 0,
               "currentPage": 0,
               "totalPage": 0,
               "message": "An unexpected error occurred while performing the search"
            }
            """;

    // ==================================== UPDATE ====================================

    public static final String UPDATE_USER_SUCCESSFUL_RESPONSE = """
            {
               "success": true,
               "user": {
                 "name": "Alice Smith",
                 "username": "alicesmith",
                 "email": "alice.smith@example.com",
                 "userGenre": "FEMALE",
                 "profilePictureURL": "https://example.com/profiles/alice.jpg",
                 "premium": true
               },
               "message": "User updated successfully"
             }
            """;

    public static final String UPDATE_USER_BAD_REQUEST_RESPONSE = """
            {
               "success": false,
               "user": null,
               "message": "Invalid input data: email must be a valid address"
            }
            """;

    public static final String UPDATE_USER_NOT_FOUND_RESPONSE = """
            {
               "success": false,
               "user": null,
               "message": "User not found"
               }
            """;


    public static final String UPDATE_USER_INTERNAL_SERVER_ERROR_RESPONSE = """
            {
                "success": false,
                "user": null,
                "message": "An unexpected error occurred while updating the user"
            }
            """;

    // ==================================== DELETE ====================================

    public static final String DELETE_USER_SUCCESSFUL_RESPONSE = "User deleted successfully";

    public static final String DELETE_USER_NOT_FOUND_RESPONSE = "User not found";

    public static final String DELETE_USER_INTERNAL_SERVER_ERROR_RESPONSE = "Can't delete user: Some internal error " +
            "occurred.";

    // ==================================== LOGIN ====================================

    public static final String LOGIN_USER_SUCCESSFUL_RESPONSE = """
            {
                "success": true,
                "message": "User logged successfully"
            }
            """;

    public static final String LOGIN_USER_BAD_REQUEST_RESPONSE = """
            {
                "success": false,
                "message": "Invalid credentials provided"
            }
            """;

    public static final String LOGIN_USER_NOT_FOUND_RESPONSE = """
            {
                  "success": false,
                  "message": "User not found"
            }
            """;

    public static final String LOGIN_USER_INTERNAL_SERVER_ERROR_RESPONSE = """
            {
               "success": false,
               "message": "An unexpected error occurred during login"
            }
            """;

}
