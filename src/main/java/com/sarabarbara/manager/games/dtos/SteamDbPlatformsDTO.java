package com.sarabarbara.manager.games.dtos;


import lombok.Builder;

/**
 * SteamDbPlatforms class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 16/01/2026
 */

@Builder
public record SteamDbPlatformsDTO(
        boolean windows,
        boolean mac,
        boolean linux


) {
}
