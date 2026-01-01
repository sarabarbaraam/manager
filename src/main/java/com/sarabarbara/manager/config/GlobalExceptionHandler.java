package com.sarabarbara.manager.config;


import com.sarabarbara.manager.exceptions.UserNotFoundException;
import com.sarabarbara.manager.exceptions.UserValidateException;
import com.sarabarbara.manager.exceptions.UsersException;
import com.sarabarbara.manager.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * GlobalExceptionHandled class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Slf4j
@RestControllerAdvice
@Schema(name = "Global Exception Handler", description = "Handles exceptions globally across the application")
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UsersException.class)
    @Schema(name = "Handle Users Exception", description = "Handles UsersException and returns appropriate error response")
    public ResponseEntity<ErrorResponse> handleUserException(UsersException e) {

        log.error("Internal server error: {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        LocalDateTime.now()
                ));

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    @Schema(name = "Handle User Not Found Exception", description = "Handles UserNotFoundException and returns appropriate error response")
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {

        log.error("User not found: {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now()
                ));

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserValidateException.class)
    @Schema(name = "Handle User Validate Exception", description = "Handles UserValidateException and returns appropriate error response")
    public ResponseEntity<ErrorResponse> handleUserValidateException(@NonNull UserValidateException e) {

        log.warn("Validation failed: {}", e.getMessage());

        return ResponseEntity.badRequest().body(
                new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                )
        );
    }

}
