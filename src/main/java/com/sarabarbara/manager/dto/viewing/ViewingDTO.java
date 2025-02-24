package com.sarabarbara.manager.dto.viewing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * ViewingDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 07/01/2025
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewingDTO {

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
     * The mediaType
     */

    @JsonProperty("media_type")
    private String mediaType;

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
        if (!(o instanceof ViewingDTO that)) return false;
        return isAdult() == that.isAdult()
                && isVideo() == that.isVideo()
                && Objects.equals(getTitle(), that.getTitle())
                && Objects.equals(getOriginalTitle(), that.getOriginalTitle())
                && Objects.equals(getGenreIds(), that.getGenreIds())
                && Objects.equals(getOriginalLanguage(), that.getOriginalLanguage())
                && Objects.equals(getOverview(), that.getOverview())
                && Objects.equals(getVoteAverage(), that.getVoteAverage())
                && Objects.equals(getVoteCount(), that.getVoteCount())
                && Objects.equals(getPosterPath(), that.getPosterPath())
                && Objects.equals(getMediaType(), that.getMediaType())
                && Objects.equals(getReleaseDate(), that.getReleaseDate());
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getOriginalTitle(), isAdult(), getGenreIds(), getOriginalLanguage(),
                getOverview(), getVoteAverage(), getVoteCount(), getPosterPath(), getMediaType(), getReleaseDate(),
                isVideo());
    }

}
