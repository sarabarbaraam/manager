package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * GamesInfo class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 15/01/2026
 */

@Builder
@Schema(description = "Games Info DTO")
public record GamesInfo(

        @Schema(description = "App ID of the game", example = "570")
        int appid,

        @Schema(description = "Name of the game", example = "Dota 2")
        String name) {
}
