package com.sarabarbara.manager.models.games;

import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * ReleaseDate class
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
public class ReleaseDate {

    /**
     * The comingSoon
     */

    private boolean comingSoon;

    /**
     * The date
     */

    private LocalDate date;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReleaseDate that)) return false;
        return isComingSoon() == that.isComingSoon()
                & Objects.equals(getDate(), that.getDate());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(isComingSoon(), getDate());
    }

}
