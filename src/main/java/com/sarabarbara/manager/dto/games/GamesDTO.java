package com.sarabarbara.manager.dto.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.sarabarbara.manager.models.games.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * GamesDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 20/11/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GamesDTO {

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
     * The detailedDescription
     */

    @JsonProperty("detailed_description")
    private String detailedDescription;

    /**
     * The aboutTheGame
     */

    @JsonProperty("about_the_game")
    private String aboutTheGame;

    /**
     * The shortDescription
     */

    @JsonProperty("short_description")
    private String shortDescription;

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
     * The capsuleImageV5
     */

    @JsonProperty("capsule_imagev5")
    private String capsuleImageV5;

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
        if (!(o instanceof GamesDTO gamesDTO)) return false;
        return isFree() == gamesDTO.isFree()
                && Objects.equals(getType(), gamesDTO.getType())
                && Objects.equals(getName(), gamesDTO.getName())
                && Objects.equals(getDlc(), gamesDTO.getDlc())
                && Objects.equals(getDetailedDescription(), gamesDTO.getDetailedDescription())
                && Objects.equals(getAboutTheGame(), gamesDTO.getAboutTheGame())
                && Objects.equals(getShortDescription(), gamesDTO.getShortDescription())
                && Objects.equals(getSupportedLanguages(), gamesDTO.getSupportedLanguages())
                && Objects.equals(getHeaderImage(), gamesDTO.getHeaderImage())
                && Objects.equals(getCapsuleImage(), gamesDTO.getCapsuleImage())
                && Objects.equals(getCapsuleImageV5(), gamesDTO.getCapsuleImageV5())
                && Objects.equals(getWebsite(), gamesDTO.getWebsite())
                && Objects.equals(getPcRequirements(), gamesDTO.getPcRequirements())
                && Objects.equals(getMacRequirements(), gamesDTO.getMacRequirements())
                && Objects.equals(getLinuxRequirements(), gamesDTO.getLinuxRequirements())
                && Objects.equals(getLegalNotice(), gamesDTO.getLegalNotice())
                && Objects.equals(getDevelopers(), gamesDTO.getDevelopers())
                && Objects.equals(getPublishers(), gamesDTO.getPublishers())
                && Objects.equals(getPrice(), gamesDTO.getPrice())
                && Objects.equals(getPlatforms(), gamesDTO.getPlatforms())
                && Objects.equals(getMetacritic(), gamesDTO.getMetacritic())
                && Objects.equals(getCategories(), gamesDTO.getCategories())
                && Objects.equals(getGenres(), gamesDTO.getGenres())
                && Objects.equals(getMovies(), gamesDTO.getMovies())
                && Objects.equals(getAchievements(), gamesDTO.getAchievements())
                && Objects.equals(getReleaseDate(), gamesDTO.getReleaseDate())
                && Objects.equals(getAgeRatings(), gamesDTO.getAgeRatings())
                && Objects.equals(getRatings(), gamesDTO.getRatings());
    }

    /**
     * The hasCode
     *
     * @return hasCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getName(), isFree(), getDlc(), getDetailedDescription(), getAboutTheGame(),
                getShortDescription(), getSupportedLanguages(), getHeaderImage(), getCapsuleImage(),
                getCapsuleImageV5(), getWebsite(), getPcRequirements(), getMacRequirements(), getLinuxRequirements(),
                getLegalNotice(), getDevelopers(), getPublishers(), getPrice(), getPlatforms(), getMetacritic(),
                getCategories(), getGenres(), getMovies(), getAchievements(), getReleaseDate(), getAgeRatings(),
                getRatings());
    }

}
