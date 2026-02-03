package com.sarabarbara.manager.games.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * SteamDbResultDTO record.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
public record SteamDbResultDTO(

        @Schema(description = "The unique identifier of the game", example = "570")
        int id,

        @Schema(description = "The name of the game", example = "Dota 2")
        String name,

        @Schema(description = "The tiny image URL of the game", example = "https://steamcdn-a.akamaihd.net/steam/apps/570/capsule_184x69.jpg?t=1618888888")
        @JsonProperty("tiny_image") String tinyImage
) {}
