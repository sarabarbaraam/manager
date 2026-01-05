package com.sarabarbara.manager.users.dtos;


import com.sarabarbara.manager.shared.GenreEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

/**
 * UsersDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 30/12/2025
 */

@Builder
@Schema(description = "DTO Object for Users")
public record UsersDTO(

        @Schema(description = "The id of the user", examples = "802")
        Long id,

        @Schema(description = "The name of the user", examples = "John")
        String name,

        @Schema(description = "The password of the user", examples = "Testpassword123!")
        String password,

        @Schema(description = "The email of the user", examples = "johnsmith@example.com")
        String email,

        @Enumerated(EnumType.STRING)
        @Schema(description = "The genre of the user", examples = "M, F, NB, PNTS")
        GenreEnum userGenre,

        @Schema(description = "The profile picture url of the user", examples = "pp.png")
        String profilePictureURL,

        @Schema(description = "Indicates if the user is premium or not", examples = "true")
        Boolean premium) {

}
