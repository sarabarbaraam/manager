package com.sarabarbara.manager.models.viewing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarabarbara.manager.enums.viewing.ViewingType;
import lombok.*;

import java.util.Objects;

/**
 * Viewing class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 13/01/2025
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Viewing {

    /**
     * The id
     */

    private Integer id;

    /**
     * The title
     */

    private String title;

    /**
     * The originalTitle
     */

    @JsonProperty("original_title")
    private String originalTitle;

    /**
     * The viewingType
     */

    private ViewingType viewingType;

    /**
     * The adult
     */

    private boolean adult;

    /**
     * The genreIds
     */

    @JsonProperty("genre_ids")
    private Integer genreIds;

    /**
     * The originalLanguage
     */

    @JsonProperty("original_language")
    private String originalLanguage;

    /**
     * The overview
     */

    private String overview;

    /**
     * The voteAverage
     */

    @JsonProperty("vote_average")
    private Float voteAverage;

    /**
     * The voteCount
     */

    @JsonProperty("vote_count")
    private Integer voteCount;

    /**
     * The posterPath
     */

    @JsonProperty("poster_path")
    private String posterPath;

    /**
     * The releaseDate
     */

    @JsonProperty("release_date")
    private String releaseDate;

    /**
     * The video
     */

    private boolean video;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Viewing viewing)) return false;
        return isAdult() == viewing.isAdult()
                && isVideo() == viewing.isVideo()
                && Objects.equals(getTitle(), viewing.getTitle())
                && Objects.equals(getOriginalTitle(), viewing.getOriginalTitle())
                && Objects.equals(getViewingType(), viewing.getViewingType())
                && Objects.equals(getId(), viewing.getId())
                && Objects.equals(getGenreIds(), viewing.getGenreIds())
                && Objects.equals(getOriginalLanguage(), viewing.getOriginalLanguage())
                && Objects.equals(getOverview(), viewing.getOverview())
                && Objects.equals(getVoteAverage(), viewing.getVoteAverage())
                && Objects.equals(getVoteCount(), viewing.getVoteCount())
                && Objects.equals(getPosterPath(), viewing.getPosterPath())
                && Objects.equals(getReleaseDate(), viewing.getReleaseDate());
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getOriginalTitle(), getViewingType(), isAdult(), getId(), getGenreIds(),
                getOriginalLanguage(), getOverview(), getVoteAverage(), getVoteCount(), getPosterPath(),
                getReleaseDate(), isVideo());
    }

}
