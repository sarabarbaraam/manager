package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * TrailersDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the trailers of a game")
public record TrailersDTO(

        @Schema(description = "The id of the trailer", examples = "256660296")
        int id,

        @Schema(description = "The name of the trailer", examples = "Stardew Valley Trailer")
        String name,

        @Schema(description = "The thumbnail of the trailer", examples = "https://shared.akamai.steamstatic" +
                ".com/store_item_assets/steam/apps/256660296/movie.293x165.jpg?t=1454099186")
        String thumbnail,

        @JsonProperty("webm")
        @Schema(description = "The trailer quality of the game", examples = "480, max")
        TrailerFormatDTO trailerQuality
) {
}
