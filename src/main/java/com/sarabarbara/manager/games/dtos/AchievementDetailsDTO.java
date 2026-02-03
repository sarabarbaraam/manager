package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * AchievementDetailsDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder
@Schema(description = "DTO representing the details of an achievement in a game")
public record AchievementDetailsDTO(

        @Schema(description = "The name of the achievement", examples = "1")
        String name,

        @JsonProperty("defaultvalue")
        @Schema(description = "The default value of the achievement", examples = "0")
        int defaultValue,

        @Schema(description = "The display name of the achievement", examples = "Magdalene")
        String displayName,

        @Schema(description = "If the achievement is hidden or not", examples = "0")
        int hidden,

        @Schema(description = "The description of the achievement", examples = "Unlocked a new character.")
        String description,

        @Schema(description = "The icon of the achievement",
                examples = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/250900" +
                        "/a36d7e92df7e991758907a75dfa55d36b52548c4.jpg")
        String icon,

        @JsonProperty("icongray")
        @Schema(description = "The icon gray of the achievement",
                examples = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/250900" +
                        "/fc5c40f3429120652211c9e95570752ba5810f22.jpg")
        String iconGray
) {
}
