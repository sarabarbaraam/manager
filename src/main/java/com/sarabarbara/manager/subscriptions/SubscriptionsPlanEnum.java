package com.sarabarbara.manager.subscriptions;


import lombok.RequiredArgsConstructor;

/**
 * SubscriptionsPlanEnum class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@RequiredArgsConstructor
public enum SubscriptionsPlanEnum {

    FREE    ("Free Plan"),
    PREMIUM_MONTHLY("Premium Monthly Plan"),
    PREMIUM_TRIMESTRAL ("Premium Trimestral Plan"),
    PREMIUM_YEARLY ("Premium Yearly Plan"),
    FAMILY("Family Plan");

    private final String description;
}
