package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * RatingsDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the ratings of a game")
public record RatingsDTO(

        @JsonProperty("review_score")
        @Schema(description = "The review score of the game", examples = "9")
        int reviewScore,

        @JsonProperty("review_score_desc")
        @Schema(description = "The review score description of the game", examples = "Overwhelmingly Positive")
        String reviewScoreDescription,

        @JsonProperty("total_positive")
        @Schema(description = "The total positive number of positive reviews of the game", examples = "344319")
        Integer totalPositive,

        @JsonProperty("total_negative")
        @Schema(description = "The total number of negative reviews of the game", examples = "3784")
        Integer totalNegative,

        @JsonProperty("total_reviews")
        @Schema(description = "The total number of reviews of the game", examples = "348103")
        Integer totalReviews
) {
}
