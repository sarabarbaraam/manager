package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * GameListDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing a list of games")
public record GameListDTO(

        @JsonProperty("steam_appid")
        @Schema(description = "The id of the game", examples = "413150")
        Integer id,

        @Schema(description = "The name of the game", examples = "Half-Life 2")
        String name,

        @Schema(description = "The type of game", examples = "Game, DLC")
        String type,

        @Schema(description = "The capsule image of the game",
                examples = "https://cdn.akamai.steamstatic.com/steam/apps/220/capsule_616x353.jpg?t=1697051907")
        String capsuleImage,

        @Schema(description = "The short description of the game",
                examples = "Half-Life 2 is a first-person shooter developed by Valve. Set in a dystopian future, " +
                        "players assume the role of Gordon Freeman as he battles against the oppressive Combine regime.")
        String shortDescription,

        @JsonProperty("price_overview")
        @Schema(description = "The price of the game", examples = "{...}")
        PriceOverviewDTO price,

        @JsonProperty("is_free")
        @Schema(description = "If the game is free", examples = "true")
        boolean isFree,

        @Schema(description = "The genres of the game", examples = "[{...}]")
        List<GameGenreDTO> genres
) {
}
