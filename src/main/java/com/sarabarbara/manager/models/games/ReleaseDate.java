package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

    @JsonProperty("coming_soon")
    private boolean comingSoon;

    /**
     * The date
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMM, yyyy")
    private String date;

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
