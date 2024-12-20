package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * AgeRatings class
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
public class AgeRatings {

    /**
     * The esrb
     */

    private AgeRatingDetails esrb;

    /**
     * The ageRate
     */

    private AgeRatingDetails pegi;

    /**
     * The usk
     */

    private AgeRatingDetails usk;

    /**
     * The oflc
     */

    private AgeRatingDetails oflc;

    /**
     * The bbfc
     */

    private AgeRatingDetails bbfc;

    /**
     * The dejus
     */

    private AgeRatingDetails dejus;

    /**
     * The steamGermany
     */

    @JsonProperty("steam_germany")
    private AgeRatingDetails steamGermany;

    /**
     * The equals
     *
     * @param o the o
     *
     * @return the equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AgeRatings ageRatings)) return false;
        return Objects.equals(getEsrb(), ageRatings.getEsrb())
                && Objects.equals(getPegi(), ageRatings.getPegi())
                && Objects.equals(getUsk(), ageRatings.getUsk())
                && Objects.equals(getOflc(), ageRatings.getOflc())
                && Objects.equals(getBbfc(), ageRatings.getBbfc())
                && Objects.equals(getDejus(), ageRatings.getDejus())
                && Objects.equals(getSteamGermany(), ageRatings.getSteamGermany());
    }

    /**
     * The hashCode
     *
     * @return the hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getEsrb(), getPegi(), getUsk(), getOflc(), getBbfc(), getDejus(), getSteamGermany());
    }

}
