package com.sarabarbara.manager.exceptions;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UserNotFoundException class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "User Not Found Exception", description = "Exception thrown when a user is not found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
