package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * TrailerFormatDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the format of a trailer")
public record TrailerFormatDTO(

        @JsonProperty("480")
        @Schema(description = "The quality 480p of the trailer of the game")
        String movie480,

        @JsonProperty("max")
        @Schema(description = "The maximum quality of the trailer of the game")
        String movieMax
) {
}
