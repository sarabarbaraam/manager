package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * PEGI class
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
public class PEGI {

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
        if (!(o instanceof PEGI pegi)) return false;
        return isDisplayOnlineNotice() == pegi.isDisplayOnlineNotice()
                && Objects.equals(getRating(), pegi.getRating())
                && Objects.equals(getDescriptors(), pegi.getDescriptors())
                && Objects.equals(getRequiredAge(), pegi.getRequiredAge());
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
