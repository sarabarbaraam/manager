package com.sarabarbara.manager.games.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * PlatformsDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the platforms supported by a game")
public record PlatformsDTO(

        @Schema(description = "The windows platform for the game")
        boolean windows,


        @Schema(description = "The mac platform for the game")
        boolean mac,


        @Schema(description = "The linux platform for the game")
        boolean linux
) {
}
