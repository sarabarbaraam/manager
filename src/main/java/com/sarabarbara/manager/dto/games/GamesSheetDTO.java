package com.sarabarbara.manager.dto.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
     * The ratings
     */

    @JsonProperty("query_summary")
    private Ratings ratings;

    /**
     * The releaseDate
     */

    @JsonProperty("release_date")
    private ReleaseDate releaseDate;

    /**
     * The rating
     */

    @JsonProperty("ratings")
    private AgeRatings ageRatings;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Games gamesSheetDTO)) return false;
        return isFree() == gamesSheetDTO.isFree()
                && Objects.equals(getType(), gamesSheetDTO.getType())
                && Objects.equals(getName(), gamesSheetDTO.getName())
                && Objects.equals(getControllerSupport(), gamesSheetDTO.getControllerSupport())
                && Objects.equals(getDlc(), gamesSheetDTO.getDlc())
                && Objects.equals(getAboutTheGame(), gamesSheetDTO.getAboutTheGame())
                && Objects.equals(getSupportedLanguages(), gamesSheetDTO.getSupportedLanguages())
                && Objects.equals(getHeaderImage(), gamesSheetDTO.getHeaderImage())
                && Objects.equals(getCapsuleImage(), gamesSheetDTO.getCapsuleImage())
                && Objects.equals(getWebsite(), gamesSheetDTO.getWebsite())
                && Objects.equals(getPcRequirements(), gamesSheetDTO.getPcRequirements())
                && Objects.equals(getMacRequirements(), gamesSheetDTO.getMacRequirements())
                && Objects.equals(getLinuxRequirements(), gamesSheetDTO.getLinuxRequirements())
                && Objects.equals(getLegalNotice(), gamesSheetDTO.getLegalNotice())
                && Objects.equals(getDevelopers(), gamesSheetDTO.getDevelopers())
                && Objects.equals(getPublishers(), gamesSheetDTO.getPublishers())
                && Objects.equals(getPrice(), gamesSheetDTO.getPrice())
                && Objects.equals(getPlatforms(), gamesSheetDTO.getPlatforms())
                && Objects.equals(getMetacritic(), gamesSheetDTO.getMetacritic())
                && Objects.equals(getCategories(), gamesSheetDTO.getCategories())
                && Objects.equals(getGenres(), gamesSheetDTO.getGenres())
                && Objects.equals(getScreenShots(), gamesSheetDTO.getScreenShots())
                && Objects.equals(getMovies(), gamesSheetDTO.getMovies())
                && Objects.equals(getAchievements(), gamesSheetDTO.getAchievements())
                && Objects.equals(getReleaseDate(), gamesSheetDTO.getReleaseDate())
                && Objects.equals(getAgeRatings(), gamesSheetDTO.getAgeRatings())
                && Objects.equals(getRatings(), gamesSheetDTO.getRatings());
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
                getScreenShots(), getMovies(), getAchievements(), getReleaseDate(), getAgeRatings(), getRatings());
    }

}
