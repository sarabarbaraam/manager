package com.sarabarbara.manager.models.games;

import lombok.*;

import java.util.Objects;

/**
 * AchievementDetails class
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
public class AchievementDetails {

    /**
     * The name
     */

    private String name;

    /**
     * The percent
     */

    private float percent;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AchievementDetails that)) return false;
        return Float.compare(getPercent(), that.getPercent()) == 0
                && Objects.equals(getName(), that.getName());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPercent());
    }

}
