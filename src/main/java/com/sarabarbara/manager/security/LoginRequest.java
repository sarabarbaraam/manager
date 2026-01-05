package com.sarabarbara.manager.security;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * LoginRequest class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/01/2026
 */

public record LoginRequest(

        @NotBlank
        @Schema(description = "The username of the user", example = "john_smith")
        String username,

        @NotBlank
        @Schema(description = "The password of the user", example = "Testpassword123!")
        String password
) {


}
