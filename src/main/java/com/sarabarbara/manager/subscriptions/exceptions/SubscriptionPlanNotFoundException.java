package com.sarabarbara.manager.subscriptions.exceptions;


/**
 * SubscriptionPlanNotFoundException class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

public class SubscriptionPlanNotFoundException extends RuntimeException {
    public SubscriptionPlanNotFoundException(String message) {
        super(message);
    }
}
