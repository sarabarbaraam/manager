package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * Ratings class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 04/12/2024
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ratings {

    /**
     * The reviewScore
     */

    @JsonProperty("review_score")
    private int reviewScore;

    /**
     * The reviewScoreDescription
     */

    @JsonProperty("review_score_desc")
    private String reviewScoreDescription;

    /**
     * The totalPositive
     */

    @JsonProperty("total_positive")
    private Integer totalPositive;

    /**
     * The totalNegative
     */

    @JsonProperty("total_negative")
    private Integer totalNegative;

    /**
     * The totalReviews
     */

    @JsonProperty("total_reviews")
    private Integer totalReviews;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ratings ratings)) return false;
        return getReviewScore() == ratings.getReviewScore()
                && Objects.equals(getReviewScoreDescription(), ratings.getReviewScoreDescription())
                && Objects.equals(getTotalPositive(), ratings.getTotalPositive())
                && Objects.equals(getTotalNegative(), ratings.getTotalNegative())
                && Objects.equals(getTotalReviews(), ratings.getTotalReviews());
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getReviewScore(), getReviewScoreDescription(), getTotalPositive(), getTotalNegative(),
                getTotalReviews());
    }

}
