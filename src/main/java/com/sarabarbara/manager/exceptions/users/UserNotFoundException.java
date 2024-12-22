package com.sarabarbara.manager.exceptions.users;

/**
 * UserNotFoundException class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/10/2024
 */

public class UserNotFoundException extends RuntimeException {

    /**
     * The User Not Found Exception
     *
     * @param message the message
     */

    public UserNotFoundException(String message) {

        super(message);
    }

}
