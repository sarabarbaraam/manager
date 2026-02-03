package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * GameSearchDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing a game search result")
public record GameSearchDTO(

        @JsonProperty("steam_appid")
        @Schema(description = "The id of the game", examples = "413150")
        Integer id,

        @Schema(description = "The name of the game", examples = "Half-Life 2")
        String name,

        @Schema(description = "The capsule image of the game",
                examples = "https://cdn.akamai.steamstatic.com/steam/apps/220/capsule_616x353.jpg?t=1697051907")
        String capsuleImage
) {
}
