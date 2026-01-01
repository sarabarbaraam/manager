package com.sarabarbara.manager.requestes;


import com.sarabarbara.manager.enums.GenreEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.sarabarbara.manager.utils.constants.UsersConstants.*;

/**
 * UserRequest class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Schema(name = "User Request", description = "Request object for creating or updating a user")
public class UserRequest {

    /**
     * The name of the user
     */

    @NotBlank
    @Size(min = 3, max = 45, message = NAME_CHARACTERS_LIMIT)
    @Schema(description = "The name of the user", examples = "John")
    private String name;

    /**
     * The username of the user
     */

    @NotBlank
    @Size(min = 3, max = 20, message = USERNAME_CHARACTERS_LIMIT)
    @Pattern(regexp = USERNAME_REGEX, message = USERNAME_PATTERN)
    @Schema(description = "The username of the user", examples = "john_smith")
    private String username;

    /**
     * The password
     */

    @NotBlank
    @Size(min = 8, max = 70, message = PASSWORD_CHARACTERS_LIMIT)
    @Pattern(regexp = PASSWORD_REGEX, message = PASSWORD_PATTERN)
    @Schema(description = "The password of the user", examples = "Testpassword123!")
    private String password;

    /**
     * The email
     */

    @NotBlank
    @Email(message = EMAIL_MUST_BE_VALID)
    @Pattern(regexp = EMAIL_REGEX)
    @Schema(description = "The email of the user", examples = "johnsmith@example.com")
    private String email;

    /**
     * The genre of the user
     */

    @Schema(description = "The genre of the user", examples = "M, F, NB, NP")
    private GenreEnum genre;

    /**
     * The url of the profile picture of the user
     */

    @Schema(description = "The profile picture url of the user", examples = "pp.png")
    private String profilePictureURL;
}