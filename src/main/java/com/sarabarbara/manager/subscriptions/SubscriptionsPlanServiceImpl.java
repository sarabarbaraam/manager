package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.subscriptions.dtos.CreateSubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.dtos.SubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.exceptions.SubscriptionPlanNotFoundException;
import com.sarabarbara.manager.subscriptions.requestes.SubscriptionsPlanRequest;
import com.sarabarbara.manager.subscriptions.requestes.UpdateSubscriptionsPlanRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Period;
import java.util.List;

/**
 * SubscriptionsPlanServiceImpl class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 10/01/2026
 */

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class SubscriptionsPlanServiceImpl implements SubscriptionsPlanService {

    private final SubscriptionsPlanRepository subscriptionsPlanRepository;
    private final SubscriptionsPlanMapper subscriptionsPlanMapper;

    @Override
    public CreateSubscriptionsPlanDTO createSubscriptionPlan(SubscriptionsPlanRequest request) {

        log.info("SubscriptionsPlanServiceImpl - createSubscriptionPlan called");

        log.debug("Creating the subscription plan with the following data: {}", request);
        SubscriptionsPlan subscriptionPlan = subscriptionsPlanMapper.toEntity(request);

        Period duration = Period.parse(request.duration());
        subscriptionPlan.setDuration(duration);

        SubscriptionsPlan savedPlan = subscriptionsPlanRepository.save(subscriptionPlan);

        return subscriptionsPlanMapper.toCreateSubscriptionsPlanDTO(savedPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionsPlanDTO> getSubscriptionPlan(int page, int size) {

        log.info("SubscriptionsPlanServiceImpl - getSubscriptionPlan called");
        log.debug("Fetching all subscription plans from the database");

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SubscriptionsPlan> plans = subscriptionsPlanRepository.findAll(pageRequest);

        return subscriptionsPlanMapper.toDTOList(plans.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionsPlanDTO> getSubscriptionPlanById(Long id, int page, int size) {

        log.info("SubscriptionsPlanServiceImpl - getSubscriptionPlanById called");
        log.debug("Fetching subscription plan with id: {} from the database", id);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SubscriptionsPlan> searchedPlan = subscriptionsPlanRepository.findById(id, pageRequest);

        if (searchedPlan.isEmpty()) {

            log.error("No plans found matching the id: {}", id);
            throw new SubscriptionPlanNotFoundException("No plans found matching the id: " + id);
        }

        return subscriptionsPlanMapper.toDTOList(searchedPlan.getContent());
    }

    @Override
    public SubscriptionsPlanDTO updateSubscriptionPlan(Long id, UpdateSubscriptionsPlanRequest request) {

        log.info("SubscriptionsPlanServiceImpl - updateSubscriptionPlan called");
        log.debug("Updating subscription plan with id: {} with the following data: {}", id, request);

        SubscriptionsPlan existingPlan = subscriptionsPlanRepository.findById(id)
                .orElseThrow(() -> new SubscriptionPlanNotFoundException("Subscription plan with id " + id + " not found"));

        Period duration = Period.parse(request.duration());
        existingPlan.setDuration(duration);

        SubscriptionsPlan updatedPlan =
                subscriptionsPlanRepository.save(subscriptionsPlanMapper.updateEntityFromRequest(request, existingPlan));

        return subscriptionsPlanMapper.toDTO(updatedPlan);
    }

    @Override
    public void deleteSubscriptionPlan(Long id) {

        log.info("SubscriptionsPlanServiceImpl - deleteSubscriptionPlan called");
        log.debug("Deactivating subscription plan with id: {}", id);

        SubscriptionsPlan existingPlan = subscriptionsPlanRepository.findById(id)
                .orElseThrow(() -> new SubscriptionPlanNotFoundException("Subscription plan with id " + id + " not found"));

        existingPlan.setActive(false);
        subscriptionsPlanRepository.save(existingPlan);

        log.info("Subscription plan deactivated successfully");
    }
}
