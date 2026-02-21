package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SteamDbDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 05/02/2026
 */

public record SteamDbDTO(

        @Schema(description = "Game ID", example = "570")
        Long id,

        @Schema(description = "Game name", example = "Dota 2")
        String name,

        @Schema(description = "Image URL", example = "https://cdn.akamai.steamstatic.com/steam/apps/570/capsule_616x353.jpg")
        String image
) {
}
