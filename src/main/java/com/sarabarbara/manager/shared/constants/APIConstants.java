package com.sarabarbara.manager.shared.constants;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ResponseConstants class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "Response Constants", description = "Constants for API responses")
public class APIConstants {

    private APIConstants() {
    }

    public static final String SUCCESS_RESPONSE = "Success Response";
    public static final String BAD_REQUEST_RESPONSE = "Bad Request Response";
    public static final String NOT_FOUND_RESPONSE = "Not Found response";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "Internal Server Error Response";

    public static final String SUCCESS = "The request was successful.";
    public static final String BAD_REQUEST = "The request could not be understood or was missing required parameters.";
    public static final String NOT_FOUND = "Not Found";
    public static final String INTERNAL_SERVER_ERROR = "An error occurred on the server.";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT = "Accept";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
    public static final String IOEXCEPTION = "IOException occurred while making the request: {}";
    public static final String THREAD_WAS_INTERRUPTED_DURING_THE_REQUEST = "Thread was interrupted during the request: {}";
    public static final String BODY = "Body: {}";
}
