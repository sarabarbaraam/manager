package com.sarabarbara.manager.dto.viewing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * ViewingSearchDTO class
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
public class ViewingSearchDTO {

    /**
     * The title
     */

    private String title;

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
     * The posterPath
     */

    @JsonProperty("poster_path")
    private String posterPath;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ViewingSearchDTO viewing)) return false;
        return Objects.equals(getTitle(), viewing.getTitle())
                && Objects.equals(getOverview(), viewing.getOverview())
                && Objects.equals(getVoteAverage(), viewing.getVoteAverage())
                && Objects.equals(getPosterPath(), viewing.getPosterPath());
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getOverview(), getVoteAverage(), getPosterPath());
    }

}
