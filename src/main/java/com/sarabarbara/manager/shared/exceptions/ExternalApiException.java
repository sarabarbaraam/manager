package com.sarabarbara.manager.shared.exceptions;


/**
 * ExternalApiException class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

public class ExternalApiException extends RuntimeException {

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}