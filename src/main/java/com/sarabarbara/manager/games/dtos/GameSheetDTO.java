package com.sarabarbara.manager.games.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

/**
 * GamesDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2026
 */

@Builder(toBuilder = true)
@Schema(description = "DTO representing a game with detailed information")
public record GameSheetDTO(

        @JsonProperty("steam_appid")
        @Schema(description = "The id of the game", examples = "413150")
        Integer id,

        @Schema(description = "The type of game", examples = "Game, DLC")
        String type,

        @Schema(description = "The name of the game", examples = "Stardew Valley")
        String name,

        @JsonProperty("is_free")
        @Schema(description = "If the game is free", examples = "true")
        boolean isFree,

        @JsonProperty("controller_support")
        @Schema(description = "The support of the controller for the game", examples = "Full, Partial")
        String controllerSupport,

        @JsonProperty("dlc")
        @Schema(description = "A list of the dlc of the game", examples = "[440820]")
        List<Integer> dlc,

        @JsonProperty("short_description")
        @Schema(description = "The short description about the game",
                examples = "You've inherited your grandfather's old farm plot in Stardew Valley. Armed with hand-me-down " +
                        "tools and a few coins, you set out to begin your new life. Can you learn to live off the land " +
                        "and turn these overgrown fields into a thriving home?")
        String shortDescription,

        @JsonProperty("detailed_description")
        @Schema(description = "The detailed description of the game",
                examples = "Stardew Valley is an open-ended country-life RPG! You've inherited your grandfather's old " +
                        "farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin" +
                        " your new life. Can you learn to live off the land and turn these overgrown fields into a " +
                        "thriving home? It won't be easy. Ever since Joja Corporation came to town, the old ways of life " +
                        "have all but disappeared. The community center, once the town's most vibrant hub of activity, " +
                        "now lies in shambles. But the valley seems full of opportunity. With a little dedication, you " +
                        "might just be the one to restore Stardew Valley to greatness! Turn your overgrown field into a " +
                        "lively farm! (...)")
        String detailedDescription,

        @JsonProperty("about_the_game")
        @Schema(description = "The description of the game",
                examples = "Stardew Valley is an open-ended country-life RPG! You've inherited your grandfather's old " +
                        "farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin" +
                        " your new life. Can you learn to live off the land and turn these overgrown fields into a " +
                        "thriving home? It won't be easy. Ever since Joja Corporation came to town, the old ways of life " +
                        "have all but disappeared. The community center, once the town's most vibrant hub of activity, " +
                        "now lies in shambles. But the valley seems full of opportunity. With a little dedication, you " +
                        "might just be the one to restore Stardew Valley to greatness! Turn your overgrown field into a " +
                        "lively farm! (...)")
        String aboutTheGame,

        @JsonProperty("supported_languages")
        @Schema(description = "The supported languages", examples = "English, Spanish")
        String supportedLanguages,

        @JsonProperty("header_image")
        @Schema(description = "The header image of the game",
                examples = "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/header" +
                        ".jpg?t=1711128146")
        String headerImage,

        @JsonProperty("capsule_image")
        @Schema(description = "The cover of the game",
                examples = "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/capsule_231x87" +
                        ".jpg?t=1711128146")
        String capsuleImage, // cover

        @JsonProperty("capsule_imagev5")
        @Schema(description = "The smaller cover of the game",
                examples = "https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/413150/capsule_184x69" +
                        ".jpg?t=1711128146")
        String capsuleImageV5, // smaller cover

        @Schema(description = "The website of the game", examples = "http://www.stardewvalley.net")
        String website,

        @JsonProperty("pc_requirements")
        @Schema(description = "The pc requirements of the game", examples = "{...}")
        JsonNode pcRequirements,

        @JsonProperty("mac_requirements")
        @Schema(description = "The mac requirements of the game", examples = "{...}")
        JsonNode macRequirements,

        @JsonProperty("linux_requirements")
        @Schema(description = "The linux requirements of the game", examples = "{...}")
        JsonNode linuxRequirements,

        @JsonProperty("legal_notice")
        @Schema(description = "The legal notice of the game", examples = "© 2014 Nicalis, Inc/Edmund McMillen")
        String legalNotice,

        @Schema(description = "The developers of the game", examples = "[]")
        List<String> developers,

        @Schema(description = "The publishers of the game", examples = "[]")
        List<String> publishers,

        @JsonProperty("price_overview")
        @Schema(description = "The price of the game", examples = "{...}")
        PriceOverviewDTO price,

        @Schema(description = "The platforms of the game", examples = "{...}")
        PlatformsDTO platforms,

        @Schema(description = "The score of the game in Metacritic", examples = "{...}")
        MetacriticDTO metacritic,

        @Schema(description = "The categories of the game", examples = "[{...}]")
        List<CategoriesDTO> categories,

        @Schema(description = "The genres of the game", examples = "[{...}]")
        List<GameGenreDTO> genres,

        @JsonProperty("screenshots")
        @Schema(description = "The screenshots of the game", examples = "[{...}]")
        List<ScreenshotsDTO> screenShots,

        @JsonProperty("movies")
        @Schema(description = "The trailer of the game", examples = "[{...}]")
        List<TrailersDTO> trailer,

        @Schema(description = "The achievements of the game", examples = "{...}")
        AchievementDTO achievements,

        @JsonProperty("query_summary")
        @Schema(description = "The ratings of the game", examples = "{...}")
        RatingsDTO ratings,

        @JsonProperty("release_date")
        @Schema(description = "The release date of the game", examples = "{...}")
        ReleaseDateDTO releaseDate,

        @JsonProperty("ratings")
        @Schema(description = "The age rating of the game", examples = "{...}")
        AgeRatingsDTO ageRatings
) {

}
