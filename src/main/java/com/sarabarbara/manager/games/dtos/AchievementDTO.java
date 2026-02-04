package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * AchievementDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing an achievement in a game")
public record AchievementDTO(

        @Schema(description = "The total of achievements of the game", examples = "49")
        int total,

        @Schema(description = "The details of the achievements of the game", examples = "[{...}]")
        List<AchievementDetailsDTO> achievements

) {
}
