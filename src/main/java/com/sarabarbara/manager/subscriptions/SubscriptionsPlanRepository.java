package com.sarabarbara.manager.subscriptions;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * SubcriptionsPlanRepository class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 06/01/2026
 */

@Repository
public interface SubscriptionsPlanRepository extends JpaRepository<SubscriptionsPlan, Long> {

    Page<SubscriptionsPlan> findById(Long id, PageRequest pageRequest);
    Optional<SubscriptionsPlan> findByName(SubscriptionsPlanEnum name);
}
