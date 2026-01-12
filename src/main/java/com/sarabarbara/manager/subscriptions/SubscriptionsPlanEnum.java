package com.sarabarbara.manager.subscriptions;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * SubscriptionsPlanEnum class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@Getter
@RequiredArgsConstructor
public enum SubscriptionsPlanEnum {

    FREE("Free Plan"),
    PREMIUM_MONTHLY("Premium Monthly Plan"),
    PREMIUM_TRIMESTRAL("Premium Trimestral Plan"),
    PREMIUM_YEARLY("Premium Yearly Plan"),
    FAMILY("Family Plan"),
    LIFETIME("Lifetime Plan");

    @JsonValue
    private final String description;

    @JsonCreator
    public static @NotNull SubscriptionsPlanEnum fromString(@NotNull String value) {

        String normalized = value.toLowerCase().replace(" ", "").replace("_", "");

        for (SubscriptionsPlanEnum plan : values()) {

            String enumNormalized = plan.description.toLowerCase().replace(" ", "").replace("_", "");

            if (enumNormalized.equals(normalized)) {
                return plan;
            }
        }
        throw new IllegalArgumentException("Invalid subscription plan: " + value);
    }

}
