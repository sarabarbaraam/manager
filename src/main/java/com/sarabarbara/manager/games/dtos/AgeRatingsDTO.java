package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * AgeRatingsDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the age ratings of a game")
public record AgeRatingsDTO(

        @JsonProperty("rating")
        @Schema(description = "The age rating of the game", examples = "e10, 12, pg")
        String ageRating,

        @Schema(description = "The descriptor of the age rating of the game", examples = "Violence")
        String descriptors,

        @JsonProperty("display_online_notice")
        @Schema(description = "The display online notice of the age rating of the game", examples = "true")
        boolean displayOnlineNotice,

        @JsonProperty("required_age")
        @Schema(description = "The required age of the game", examples = "12")
        String requiredAge
) {
}
