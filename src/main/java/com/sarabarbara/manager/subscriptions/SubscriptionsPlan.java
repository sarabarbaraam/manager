package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.subscriptions.utils.PeriodToBigIntegerMonthsConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Period;

/**
 * SubscriptionPlan class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subscriptions_plans")
public class SubscriptionsPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subscription_plan", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_plan_name", length = 20, nullable = false)
    private SubscriptionsPlanEnum name;

    @Column(nullable = false)
    private BigDecimal price;

    @Convert(converter = PeriodToBigIntegerMonthsConverter.class)
    @Column(nullable = false)
    private Period duration;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String features;

    private Boolean active;

    @Transient
    public String getDuration() {

        int months = duration.getMonths();

        if (months == 0) {

            return "lifetime";
        }

        return months + (months == 1 ? " month" : " months");
    }
}
