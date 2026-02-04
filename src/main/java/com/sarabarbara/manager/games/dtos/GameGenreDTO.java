package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * GameGenreDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing a game genre")
public record GameGenreDTO(

        @Schema(description = "The id of the genre", examples = "23")
        String id,

        @Schema(description = "The description of the genre", examples = "Indie")
        String description
) {
}
