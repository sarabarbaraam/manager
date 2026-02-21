package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SteamStoreDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/02/2026
 */

public record SteamStoreDTO(

        @Schema(description = "Application ID of the game", example = "570")
        int id,

        @Schema(description = "Name of the game", example = "Dota 2")
        String name,

        @Schema(description = "Type of the game", example = "game")
        String type,

        @Schema(description = "URL of the game's image", example = "https://cdn.akamai.steamstatic.com/steam/apps/570/capsule_616x353.jpg")
        String capsuleImage
) {
}
