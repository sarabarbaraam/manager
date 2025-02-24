package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * AgeRatingDetails class
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
public class AgeRatingDetails {

    /**
     * The rating
     */

    private String rating;

    /**
     * The descriptors
     */

    private String descriptors;

    /**
     * The displayOnlineNotice
     */

    @JsonProperty("display_online_notice")
    private boolean displayOnlineNotice;
    /**
     * The requiredAge
     */

    @JsonProperty("required_age")
    private String requiredAge;

    /**
     * the equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AgeRatingDetails ageRatingDetails)) return false;
        return isDisplayOnlineNotice() == ageRatingDetails.isDisplayOnlineNotice()
                && Objects.equals(getRating(), ageRatingDetails.getRating())
                && Objects.equals(getDescriptors(), ageRatingDetails.getDescriptors())
                && Objects.equals(getRequiredAge(), ageRatingDetails.getRequiredAge());
    }

    /**
     * the hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getRating(), getDescriptors(), isDisplayOnlineNotice(), getRequiredAge());
    }

}
