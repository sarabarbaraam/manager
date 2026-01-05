package com.sarabarbara.manager.users;


import com.sarabarbara.manager.shared.GenreEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.sarabarbara.manager.shared.constants.UsersConstants.*;

/**
 * UserRequest class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Schema(name = "User Request", description = "Request object for creating or updating a user")
public record UserRequest(

        @NotBlank
        @Size(min = 3, max = 45, message = NAME_CHARACTERS_LIMIT)
        @Schema(description = "The name of the user", example = "John")
        String name,

        @NotBlank
        @Size(min = 3, max = 20, message = USERNAME_CHARACTERS_LIMIT)
        @Pattern(regexp = USERNAME_REGEX, message = USERNAME_PATTERN)
        @Schema(description = "The username of the user", example = "john_smith")
        String username,

        @NotBlank
        @Size(min = 8, max = 70, message = PASSWORD_CHARACTERS_LIMIT)
        @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_PATTERN)
        @Schema(description = "The password of the user", example = "Testpassword123!")
        String password,

        @NotBlank
        @Email(message = EMAIL_MUST_BE_VALID)
        @Pattern(regexp = EMAIL_REGEX)
        @Schema(description = "The email of the user", example = "johnsmith@example.com")
        String email,

        @Schema(description = "The genre of the user", example = "M, F, NB, NP")
        GenreEnum genre,

        @Schema(description = "The profile picture url of the user", example = "pp.png")
        String profilePictureURL
) {
}
