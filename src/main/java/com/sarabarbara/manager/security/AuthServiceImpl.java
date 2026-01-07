package com.sarabarbara.manager.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * AuthServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilServiceImpl jwtUtilServiceImpl;

    @Override
    public Long getCurrentUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            throw new RuntimeException("User not authenticated");
        }

        Object user = authentication.getPrincipal();

        if (!(user instanceof UserDetail userDetails)) {

            throw new RuntimeException("Invalid authentication user");
        }

        return userDetails.getId();
    }

    @Override
    public AuthResponse login(@NotNull LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetail userDetails = Objects.requireNonNull(
                (UserDetail) authentication.getPrincipal(),
                "UserDetails cannot be null"
        );

        if (!userDetails.isEnabled()) {

            // todo log inactive account login attempt. implement account activation flow
            log.warn("Attempt to login with inactive account: {}", userDetails.getUsername());
            throw new DisabledException("User account is inactive");
        }

        String token = jwtUtilServiceImpl.generateToken(userDetails);

        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Failed to generate JWT token");
        }

        Date expiration = jwtUtilServiceImpl.extractExpiration(token);

        return new AuthResponse(
                token,
                "Bearer ",
                expiration.getTime()
        );

    }
}

