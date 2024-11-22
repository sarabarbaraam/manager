package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * Games class
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Games {

    /**
     * The id
     */

    @JsonProperty("steam_appid")
    private int id;

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

    private String legalNotice;

    /**
     * The developers
     */

    private List<String> developers;

    /**
     * The publisher
     */

    private String publisher;

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
     * The movies
     */

    private List<Movies> movies;

    /**
     * The achievement
     */

    private Achievement achievement;

    /**
     * The releaseDate
     */

    private ReleaseDate releaseDate;

    /**
     * The rating
     */

    private Ratings rating;

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
        return getId() == games.getId()
                && isFree() == games.isFree()
                && getDlc() == games.getDlc()
                && Objects.equals(getType(), games.getType())
                && Objects.equals(getName(), games.getName())
                && Objects.equals(getDetailedDescription(), games.getDetailedDescription())
                && Objects.equals(getAboutTheGame(), games.getAboutTheGame())
                && Objects.equals(getShortDescription(), games.getShortDescription())
                && Objects.equals(getSupportedLanguages(), games.getSupportedLanguages())
                && Objects.equals(getHeaderImage(), games.getHeaderImage())
                && Objects.equals(getCapsuleImage(), games.getCapsuleImage())
                && Objects.equals(getCapsuleImageV5(), games.getCapsuleImageV5())
                && Objects.equals(getWebsite(), games.getWebsite())
                && Objects.equals(getPcRequirements(), games.getPcRequirements())
                && Objects.equals(getMacRequirements(), games.getMacRequirements())
                && Objects.equals(getLinuxRequirements(), games.getLinuxRequirements())
                && Objects.equals(getLegalNotice(), games.getLegalNotice())
                && Objects.equals(getDevelopers(), games.getDevelopers())
                && Objects.equals(getPublisher(), games.getPublisher())
                && Objects.equals(getPrice(), games.getPrice())
                && Objects.equals(getPlatforms(), games.getPlatforms())
                && Objects.equals(getMetacritic(), games.getMetacritic())
                && Objects.equals(getCategories(), games.getCategories())
                && Objects.equals(getGenres(), games.getGenres())
                && Objects.equals(getMovies(), games.getMovies())
                && Objects.equals(getAchievement(), games.getAchievement())
                && Objects.equals(getReleaseDate(), games.getReleaseDate())
                && Objects.equals(getRating(), games.getRating());
    }

    /**
     * The hashCode
     *
     * @return the HashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getName(), isFree(), getDlc(), getDetailedDescription(),
                getAboutTheGame(), getShortDescription(), getSupportedLanguages(), getHeaderImage(),
                getCapsuleImage(), getCapsuleImageV5(), getWebsite(), getPcRequirements(), getMacRequirements(),
                getLinuxRequirements(), getLegalNotice(), getDevelopers(), getPublisher(), getPrice(), getPlatforms()
                , getMetacritic(), getCategories(), getGenres(), getMovies(), getAchievement(), getReleaseDate(),
                getRating());
    }

}
