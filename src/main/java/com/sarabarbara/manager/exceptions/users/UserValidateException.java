package com.sarabarbara.manager.exceptions.users;

/**
 * UserValidateException class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/10/2024
 */
public class UserValidateException extends RuntimeException {

    /**
     * The User Validate Exception
     *
     * @param message the message (String)
     */

    public UserValidateException(String message) {

        super(message);
    }

    /**
     * The User Validate Exception
     *
     * @param message the message (StringBuilder(
     */

    public UserValidateException(StringBuilder message) {

        super(String.valueOf(message));
    }

}
