package com.sarabarbara.manager.subscriptions.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarabarbara.manager.subscriptions.SubscriptionsPlanEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

/**
 * SubscriptionsPlanDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 09/01/2026
 */

public record SubscriptionsPlanDTO(

        @Schema(description = "The unique identifier of the subscription plan", example = "1")
        Long id,

        @Enumerated(EnumType.STRING)
        @Schema(description = "The name of the subscription plan", example = "Free Plan")
        SubscriptionsPlanEnum name,

        @Schema(description = "The price of the subscription plan", example = "0.00")
        BigDecimal price,

        @JsonProperty("duration")
        @Schema(description = "The duration of the subscription plan in human-readable format", example = "12 months")
        String formattedDuration,

        @Schema(description = "The description of the subscription plan", example = "This is a free plan with limited features.")
        String description,

        @Schema(description = "The features included in the subscription plan", example = "Mark as favorite, create lists")
        String features,

        @Schema(description = "Indicates whether the subscription plan is active or not", example = "true")
        Boolean active
) {
}
