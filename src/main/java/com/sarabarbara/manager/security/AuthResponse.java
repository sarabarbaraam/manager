package com.sarabarbara.manager.security;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AuthResponse class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

@Schema(name = "Authentication Response", description = "Response returned after user authentication")
public record AuthResponse(

        @Schema(description = "Access token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Type of the token", example = "Bearer")
        String tokenType,

        @Schema(description = "Expiration time in seconds", example = "3600")
        long expiresIn

) {
}
