package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.users.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Subscriptions class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@Data
@Builder
@Entity
@Table(name = "subscriptions_history")
public class SubscriptionsPlanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_subscription", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "current_plan_name")
    private SubscriptionsPlan planType;

    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Column(name = "purchase_at", nullable = false)
    private BigDecimal purchaseAt;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "auto_renew", nullable = false)
    private boolean autoRenew;

}
