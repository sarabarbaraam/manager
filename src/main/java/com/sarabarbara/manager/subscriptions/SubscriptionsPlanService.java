package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.subscriptions.dtos.CreateSubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.dtos.SubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.requestes.SubscriptionsPlanRequest;
import com.sarabarbara.manager.subscriptions.requestes.UpdateSubscriptionsPlanRequest;

import java.util.List;

/**
 * SubscriptionsPlanService class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 08/01/2026
 */

public interface SubscriptionsPlanService {

    CreateSubscriptionsPlanDTO createSubscriptionPlan(SubscriptionsPlanRequest request);
    List<SubscriptionsPlanDTO> getSubscriptionPlan(int page, int size);
    List<SubscriptionsPlanDTO> getSubscriptionPlanById(Long id, int page, int size);
    SubscriptionsPlanDTO updateSubscriptionPlan(Long id, UpdateSubscriptionsPlanRequest request);
    void deleteSubscriptionPlan(Long id);
}
