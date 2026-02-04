package com.sarabarbara.manager.security;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sarabarbara.manager.shared.constants.APIConstants.AUTHENTICATION_FAILED;
import static com.sarabarbara.manager.shared.constants.APIConstants.AUTHENTICATION_SUCCESS;
import static com.sarabarbara.manager.shared.constants.SwaggerAuthExamplesConstants.AUTHENTICATION_FAILED_RESPONSE;

/**
 * AuthController class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT token upon successful login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = AUTHENTICATION_SUCCESS),
            @ApiResponse(responseCode = "401", description = AUTHENTICATION_FAILED,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = AUTHENTICATION_FAILED,
                                    value = AUTHENTICATION_FAILED_RESPONSE
                            )
                    )
            ),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid @NotNull LoginRequest request) {

        log.info("AuthController - login called");

        AuthResponse response = authService.login(request);

        log.info("AuthController - login finished for user {}", request.username());
        return ResponseEntity.ok(response);

    }

}
