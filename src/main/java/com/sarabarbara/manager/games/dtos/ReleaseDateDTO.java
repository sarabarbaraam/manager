package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * ReleaseDateDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the release date information of a game")
public record ReleaseDateDTO(

        @JsonProperty("coming_soon")
        @Schema(description = "If the game didn't go out yet", examples = "true")
        boolean comingSoon,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM, yyyy")
        @Schema(description = "The release date of the game", examples = "05/11/2017")
        String date
) {
}
