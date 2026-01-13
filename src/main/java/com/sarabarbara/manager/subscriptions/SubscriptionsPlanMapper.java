package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.subscriptions.dtos.CreateSubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.dtos.SubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.requestes.SubscriptionsPlanRequest;
import com.sarabarbara.manager.subscriptions.requestes.UpdateSubscriptionsPlanRequest;
import org.mapstruct.*;

import java.time.Period;
import java.util.List;

/**
 * SubscriptionsPlanMapper class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 10/01/2026
 */

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionsPlanMapper {

    SubscriptionsPlan toEntity(SubscriptionsPlanRequest request);

    SubscriptionsPlanDTO toDTO(SubscriptionsPlan subscriptionPlan);

    List<SubscriptionsPlanDTO> toDTOList(List<SubscriptionsPlan> subscriptionPlans);

    CreateSubscriptionsPlanDTO toCreateSubscriptionsPlanDTO(SubscriptionsPlan subscriptionPlan);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "price", source = "request.price")
    @Mapping(target = "duration", source = "request.duration")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "features", source = "request.features")
    @Mapping(target = "active", source = "request.active")
    SubscriptionsPlan updateEntityFromRequest(UpdateSubscriptionsPlanRequest request, @MappingTarget SubscriptionsPlan existingPlan);

    // ===================== Custom Mappings =====================

    default Period mapDuration(String duration) {
        return duration == null ? null : Period.parse(duration);
    }
}
