package com.sarabarbara.manager.dto.viewing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarabarbara.manager.enums.viewing.ViewingType;
import lombok.*;

import java.util.Objects;

/**
 * ViewingSheetDTO class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 18/02/2025
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViewingSheetDTO {

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
        if (!(o instanceof ViewingSheetDTO that)) return false;
        return adult == that.adult
                && video == that.video
                && Objects.equals(title, that.title)
                && Objects.equals(originalTitle, that.originalTitle)
                && viewingType == that.viewingType && Objects.equals(genreIds, that.genreIds)
                && Objects.equals(originalLanguage, that.originalLanguage)
                && Objects.equals(overview, that.overview)
                && Objects.equals(voteAverage, that.voteAverage)
                && Objects.equals(voteCount, that.voteCount)
                && Objects.equals(posterPath, that.posterPath)
                && Objects.equals(releaseDate, that.releaseDate);
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(title, originalTitle, viewingType, adult, genreIds, originalLanguage, overview,
                voteAverage, voteCount, posterPath, releaseDate, video);
    }

}
