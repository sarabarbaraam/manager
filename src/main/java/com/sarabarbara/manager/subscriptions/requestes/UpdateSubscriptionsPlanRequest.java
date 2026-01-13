package com.sarabarbara.manager.subscriptions.requestes;


import com.sarabarbara.manager.subscriptions.SubscriptionsPlanEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

import static com.sarabarbara.manager.subscriptions.SubscriptionsPlanConstants.SUBSCRIPTION_PLAN_DESCRIPTION_CHARACTERS_LIMIT;
import static com.sarabarbara.manager.subscriptions.SubscriptionsPlanConstants.SUBSCRIPTION_PLAN_FEATURES_CHARACTERS_LIMIT;

/**
 * UpdateSubscriptionsPlanDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/01/2026
 */

@Builder
@Schema(name = "Update Subscriptions Plan Request", description = "Request object for updating a subscription plan")
public record UpdateSubscriptionsPlanRequest(

        @Enumerated(EnumType.STRING)
        @Schema(description = "The name of the subscription plan", example = "Free Plan")
        SubscriptionsPlanEnum name,

        @Schema(description = "The price of the subscription plan", example = "0.00")
        BigDecimal price,

        @Schema(description = "The duration of the subscription plan in months. ISO-8601 duration", example = "P1M")
        String duration,

        @Size(min = 40, max = 255, message = SUBSCRIPTION_PLAN_DESCRIPTION_CHARACTERS_LIMIT)
        @Schema(description = "The description of the subscription plan", example = "This is a free plan with limited features.")
        String description,

        @Size(min = 10, max = 255, message = SUBSCRIPTION_PLAN_FEATURES_CHARACTERS_LIMIT)
        @Schema(description = "The features included in the subscription plan", example = "Mark as favorite, create lists")
        String features,

        @Schema(description = "Indicates whether the subscription plan is active", example = "true")
        Boolean active
) {

}
