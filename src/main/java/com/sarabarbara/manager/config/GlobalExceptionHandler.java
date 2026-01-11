package com.sarabarbara.manager.config;


import com.sarabarbara.manager.shared.ErrorResponse;
import com.sarabarbara.manager.subscriptions.exceptions.SubscriptionPlanNotFoundException;
import com.sarabarbara.manager.subscriptions.exceptions.SubscriptionsPlanException;
import com.sarabarbara.manager.users.exceptions.UserNotFoundException;
import com.sarabarbara.manager.users.exceptions.UserValidateException;
import com.sarabarbara.manager.users.exceptions.UsersException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
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
    public ResponseEntity<ErrorResponse> handleUserInternalServerErrorException(UsersException e) {

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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(UsersException.class)
    public ResponseEntity<ErrorResponse> handleUsersNoContentException(@NotNull UsersException e) {

        log.error("No content: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.NO_CONTENT.value(),
                        LocalDateTime.now()
                ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserValidateException.class)
    public ResponseEntity<ErrorResponse> handleUsersBadRequestException(@NonNull UserValidateException e) {

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

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SubscriptionsPlanException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionsPlanInternalServerErrorException(UsersException e) {

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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(SubscriptionsPlanException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionsPlanNoContentException(@NotNull UsersException e) {

        log.error("No content: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ErrorResponse(
                        false,
                        e.getMessage(),
                        HttpStatus.NO_CONTENT.value(),
                        LocalDateTime.now()
                ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserValidateException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionsPlanBadRequestException(@NonNull UserValidateException e) {

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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledAccountException(DisabledException e) {

        log.error("Disabled account access attempt: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        false,
                        "User account is inactive",
                        HttpStatus.FORBIDDEN.value(),
                        LocalDateTime.now()
                ));
    }
}
