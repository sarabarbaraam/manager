package com.sarabarbara.manager.models.games;

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
public class PEGI {

    /**
     * The rating
     */

    private String rating;

    /**
     * The description
     */

    private String description;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PEGI pegi)) return false;
        return Objects.equals(getRating(), pegi.getRating())
                && Objects.equals(getDescription(), pegi.getDescription());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getRating(), getDescription());
    }

}
