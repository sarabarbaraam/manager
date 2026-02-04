package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * ScreenshotsDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing a screenshot of a game")
public record ScreenshotsDTO(

        @JsonProperty("path_thumbnail")
        @Schema(description = "The path for thumbnail of the screenshot", examples = "pathThumbnail.png")
        String pathThumbnail,

        @JsonProperty("path_full")
        @Schema(description = "The path of the screenshot", examples = "pathScreenshot.png")
        String pathFull
) {
}
