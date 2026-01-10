package com.sarabarbara.manager.subscriptions.dtos;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

/**
 * CreateSubscriptionsPlanDTO class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 08/01/2026
 */

@Builder
@Schema(description = "DTO for creating a new subscription plan")
public record CreateSubscriptionsPlanDTO(

        @Schema(description = "The name of the subscription plan", examples = "Premium Plan")
        String name,

        @Schema(description = "The price of the subscription plan", examples = "9.99")
        Double price,

        @Schema(description = "The duration of the subscription plan in months", examples = "1")
        Integer durationInMonths,

        @Schema(description = "The description of the subscription plan", examples = "Access to all premium features")
        String description,

        @Schema(description = "The features included in the subscription plan", examples = "Mark all episodes as watched at once")
        String features,

        @Schema(description = "Indicates whether the subscription plan is active or not", example = "true")
        Boolean active
) {
}
