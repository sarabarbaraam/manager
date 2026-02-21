package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * SteamDbPrice class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 16/01/2026
 */

@Builder
public record SteamDbPriceDTO(


        String currency,
        int initial,
        @JsonProperty("final") int finalPrice

) {
}
