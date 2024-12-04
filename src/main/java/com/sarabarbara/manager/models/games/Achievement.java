package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Objects;

/**
 * Achievement class
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
public class Achievement {

    /**
     * The total
     */

    private int total;

    /**
     * The achievementList
     */

    private List<AchievementDetails> achievements;

    /**
     * The equals
     */

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Achievement that)) return false;
        return getTotal() == that.getTotal()
                && Objects.equals(getAchievements(), that.getAchievements());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getTotal(), getAchievements());
    }

}
