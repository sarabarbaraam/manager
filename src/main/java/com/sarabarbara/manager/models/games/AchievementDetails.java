package com.sarabarbara.manager.models.games;

import com.fasterxml.jackson.annotation.JsonProperty;
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
     * The defaultValue
     */

    @JsonProperty("defaultvalue")
    private int defaultValue;

    /**
     * The displayName
     */

    private String displayName;

    /**
     * The hidden
     */

    private int hidden;

    /**
     * The description
     */

    private String description;

    /**
     * The icon
     */

    private String icon;

    /**
     * The iconGray
     */

    @JsonProperty("icongray")
    private String iconGray;

    /**
     * The equals
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AchievementDetails that)) return false;
        return getDefaultValue() == that.getDefaultValue()
                && getHidden() == that.getHidden()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getDisplayName(), that.getDisplayName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getIcon(), that.getIcon())
                && Objects.equals(getIconGray(), that.getIconGray());
    }

    /**
     * The hashCode
     */

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDefaultValue(), getDisplayName(), getHidden(), getDescription(),
                getIcon(), getIconGray());
    }

}
