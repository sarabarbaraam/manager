package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;

/**
 * Ratings class
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
public class Ratings {

    /**
     * The esrb
     */

    private PEGI esrb;

    /**
     * The pegi
     */

    private PEGI pegi;

    /**
     * The usk
     */

    private PEGI usk;

    /**
     * The oflc
     */

    private PEGI oflc;

    /**
     * The bbfc
     */

    private PEGI bbfc;

    /**
     * The dejus
     */

    private PEGI dejus;

    /**
     * The steamGermany
     */

    @JsonProperty("steam_germany")
    private PEGI steamGermany;

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
        return Objects.equals(getEsrb(), ratings.getEsrb())
                && Objects.equals(getPegi(), ratings.getPegi())
                && Objects.equals(getUsk(), ratings.getUsk())
                && Objects.equals(getOflc(), ratings.getOflc())
                && Objects.equals(getBbfc(), ratings.getBbfc())
                && Objects.equals(getDejus(), ratings.getDejus())
                && Objects.equals(getSteamGermany(), ratings.getSteamGermany());
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
