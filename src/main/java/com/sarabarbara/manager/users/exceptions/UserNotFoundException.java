package com.sarabarbara.manager.users.exceptions;


/**
 * UserNotFoundException class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
