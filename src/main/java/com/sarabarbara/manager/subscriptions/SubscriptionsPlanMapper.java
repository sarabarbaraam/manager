package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.subscriptions.dtos.CreateSubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.dtos.SubscriptionsPlanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * SubscriptionsPlanMapper class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 10/01/2026
 */

@Mapper(componentModel = "spring")
public interface SubscriptionsPlanMapper {

    @Mapping(target = "id", ignore = true)

    SubscriptionsPlan toEntity(SubscriptionsPlanRequest request);
    SubscriptionsPlanDTO toDTO(SubscriptionsPlan subscriptionPlan);
    List<SubscriptionsPlanDTO> toDTOList(List<SubscriptionsPlan> subscriptionPlans);
    CreateSubscriptionsPlanDTO toCreateSubscriptionsPlanDTO(SubscriptionsPlan subscriptionPlan);
    void updateEntityFromRequest(SubscriptionsPlanRequest request, SubscriptionsPlan existingPlan);
}
