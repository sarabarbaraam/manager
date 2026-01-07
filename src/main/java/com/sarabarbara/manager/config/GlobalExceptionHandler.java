package com.sarabarbara.manager.config;


import com.sarabarbara.manager.shared.ErrorResponse;
import com.sarabarbara.manager.subscriptions.exceptions.SubscriptionPlanNotFoundException;
import com.sarabarbara.manager.users.exceptions.UserNotFoundException;
import com.sarabarbara.manager.users.exceptions.UserValidateException;
import com.sarabarbara.manager.users.exceptions.UsersException;
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
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UsersException.class)
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
    public ResponseEntity<ErrorResponse> handleUserValidateException(@NonNull UserValidateException e) {

        log.error("Validation failed: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                ));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SubscriptionPlanNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionPlanNotFoundException(SubscriptionPlanNotFoundException e) {

        log.error("Subscription plan not found: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now()
                ));
    }

}
