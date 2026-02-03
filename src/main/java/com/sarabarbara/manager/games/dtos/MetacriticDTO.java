package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * MetacriticDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the Metacritic score of a game")
public record MetacriticDTO(

        @Schema(description = "Score of the game in Metacritic", examples = "89")
        int score,


        @Schema(description = "The url to the score in Metacritic",
                examples = "https://www.metacritic.com/game/pc/stardew-valley?ftag=MCD-06-10aaa1f")
        String url
) {
}
