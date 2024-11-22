package com.sarabarbara.manager.models.games;

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
     * The pegi
     */

    private PEGI pegi;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ratings ratings)) return false;
        return Objects.equals(getPegi(), ratings.getPegi());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hashCode(getPegi());
    }

}
