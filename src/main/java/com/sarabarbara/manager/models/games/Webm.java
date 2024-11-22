package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * Webm class
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
public class Webm {

    /**
     * The fourHundredEightyQuality
     */

    private String fourHundredEightyQuality; // 480 in json

    /**
     * The max
     */
    private String max;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Webm webm)) return false;
        return Objects.equals(getFourHundredEightyQuality(), webm.getFourHundredEightyQuality())
                && Objects.equals(getMax(), webm.getMax());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getFourHundredEightyQuality(), getMax());
    }

}
