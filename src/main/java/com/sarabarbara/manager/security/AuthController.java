package com.sarabarbara.manager.security;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/v1/users")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid @NotNull LoginRequest request) {

        log.info("AuthController - login called");

        AuthResponse response = authService.login(request);

        log.info("AuthController - login finished for user {}", request.username());
        return ResponseEntity.ok(response);

    }

}
