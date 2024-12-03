package com.sarabarbara.manager.dto.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.sarabarbara.manager.models.games.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * GamesSheetDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 25/11/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GamesSheetDTO {

    /**
     * The type
     */

    private String type;

    /**
     * The name
     */

    private String name;

    /**
     * The isFree
     */

    @JsonProperty("is_free")
    private boolean isFree;

    /**
     * The controllerSupport
     */

    @JsonProperty("controller_support")
    private String controllerSupport;

    /**
     * The dlc
     */

    @JsonProperty("dlc")
    private List<Integer> dlc;

    /**
     * The aboutTheGame
     */

    @JsonProperty("about_the_game")
    private String aboutTheGame;

    /**
     * The supportedLanguages
     */

    @JsonProperty("supported_languages")
    private String supportedLanguages;

    /**
     * The headerImage
     */

    @JsonProperty("header_image")
    private String headerImage;

    /**
     * The capsuleImage
     */

    @JsonProperty("capsule_image")
    private String capsuleImage; // cover

    /**
     * The website
     */

    private String website;

    /**
     * The pcRequirements
     */

    @JsonProperty("pc_requirements")
    private JsonNode pcRequirements;

    /**
     * The macRequirements
     */

    @JsonProperty("mac_requirements")
    private JsonNode macRequirements;

    /**
     * The linuxRequirements
     */

    @JsonProperty("linux_requirements")
    private JsonNode linuxRequirements;

    /**
     * The legalNotice
     */

    @JsonProperty("legal_notice")
    private String legalNotice;

    /**
     * The developers
     */

    private List<String> developers;

    /**
     * The publishers
     */

    private List<String> publishers;

    /**
     * The price
     */

    @JsonProperty("price_overview")
    private PriceOverview price;

    /**
     * The platforms
     */

    private Platforms platforms;

    /**
     * The metacritic
     */

    private Metacritic metacritic;

    /**
     * The categories
     */

    private List<Categories> categories;

    /**
     * The genre
     */

    private List<GameGenre> genres;

    /**
     * The screenShots
     */

    @JsonProperty("screenshots")
    private List<Screenshots> screenShots;

    /**
     * The movies
     */

    private List<Movies> movies;

    /**
     * The achievements
     */

    private Achievement achievements;

    /**
     * The releaseDate
     */

    @JsonProperty("release_date")
    private ReleaseDate releaseDate;

    /**
     * The rating
     */

    private Ratings ratings;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Games games)) return false;
        return isFree() == games.isFree()
                && Objects.equals(getType(), games.getType())
                && Objects.equals(getName(), games.getName())
                && Objects.equals(getControllerSupport(), games.getControllerSupport())
                && Objects.equals(getDlc(), games.getDlc())
                && Objects.equals(getAboutTheGame(), games.getAboutTheGame())
                && Objects.equals(getSupportedLanguages(), games.getSupportedLanguages())
                && Objects.equals(getHeaderImage(), games.getHeaderImage())
                && Objects.equals(getCapsuleImage(), games.getCapsuleImage())
                && Objects.equals(getWebsite(), games.getWebsite())
                && Objects.equals(getPcRequirements(), games.getPcRequirements())
                && Objects.equals(getMacRequirements(), games.getMacRequirements())
                && Objects.equals(getLinuxRequirements(), games.getLinuxRequirements())
                && Objects.equals(getLegalNotice(), games.getLegalNotice())
                && Objects.equals(getDevelopers(), games.getDevelopers())
                && Objects.equals(getPublishers(), games.getPublishers())
                && Objects.equals(getPrice(), games.getPrice())
                && Objects.equals(getPlatforms(), games.getPlatforms())
                && Objects.equals(getMetacritic(), games.getMetacritic())
                && Objects.equals(getCategories(), games.getCategories())
                && Objects.equals(getGenres(), games.getGenres())
                && Objects.equals(getScreenShots(), games.getScreenShots())
                && Objects.equals(getMovies(), games.getMovies())
                && Objects.equals(getAchievements(), games.getAchievements())
                && Objects.equals(getReleaseDate(), games.getReleaseDate())
                && Objects.equals(getRatings(), games.getRatings());
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName(), isFree(), getControllerSupport(), getDlc(),
                getAboutTheGame(), getSupportedLanguages(), getHeaderImage(), getCapsuleImage(), getWebsite(),
                getPcRequirements(), getMacRequirements(), getLinuxRequirements(), getLegalNotice(), getDevelopers(),
                getPublishers(), getPrice(), getPlatforms(), getMetacritic(), getCategories(), getGenres(),
                getScreenShots(), getMovies(), getAchievements(), getReleaseDate(), getRatings());
    }

}
