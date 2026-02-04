package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * PriceOverviewDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the price overview of a game")
public record PriceOverviewDTO(

        @Schema(description = "The currency of the game", examples = "EUR")
        String currency,

        @Schema(description = "The initial price of the game", examples = "1399")
        int initial,

        @JsonProperty("final")
        @Schema(description = "The final price of the game if there is some discount", examples = "1399")
        int finalPrice,

        @JsonProperty("discount_percent")
        @Schema(description = "The discount percent of the game", examples = "0")
        int discountPercent,

        @JsonProperty("initial_formatted")
        @Schema(description = "The initial formatted for the price of the game", examples = "")
        String initialFormatted,

        @JsonProperty("final_formatted")
        @Schema(description = "The final formatted for the price of the game", examples = "13.99")
        String finalFormatted
) {

}