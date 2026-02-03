package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * CategoriesDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the categories of a game")
public record CategoriesDTO(

        @Schema(description = "The id of the category", examples = "1")
        int id,

        @Schema(description = "The description of the category", examples = "Single-player")
        String description

) {
}
