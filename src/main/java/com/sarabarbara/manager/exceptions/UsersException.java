package com.sarabarbara.manager.exceptions;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UsersException class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "Users Exception", description = "Custom exception for user-related errors")
public class UsersException extends RuntimeException {

    public UsersException(String message) {
        super(message);
    }
}
