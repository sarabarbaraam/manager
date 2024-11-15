package com.sarabarbara.manager.exceptions;

/**
 * UserValidateException class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/10/2024
 */
public class UserValidateException extends RuntimeException {

    public UserValidateException(String message) {

        super(message);
    }

    public UserValidateException(StringBuilder message) {

        super(String.valueOf(message));
    }

}
