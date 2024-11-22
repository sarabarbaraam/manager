package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * SystemRequirements class
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
public class SystemRequirements {

    /**
     * The minimum
     */

    private String minimum;

    /**
     * The recommended
     */

    private String recommended;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SystemRequirements that)) return false;
        return Objects.equals(getMinimum(), that.getMinimum())
                && Objects.equals(getRecommended(), that.getRecommended());
    }

    /**
     * The hashcode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getMinimum(), getRecommended());
    }

}
