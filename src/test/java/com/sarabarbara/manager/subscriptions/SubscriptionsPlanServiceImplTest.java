package com.sarabarbara.manager.subscriptions;


import com.sarabarbara.manager.subscriptions.dtos.CreateSubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.dtos.SubscriptionsPlanDTO;
import com.sarabarbara.manager.subscriptions.exceptions.SubscriptionPlanNotFoundException;
import com.sarabarbara.manager.subscriptions.requestes.SubscriptionsPlanRequest;
import com.sarabarbara.manager.subscriptions.requestes.UpdateSubscriptionsPlanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * SubscriptionsPlanServiceImplTest class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/01/2026
 */

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SubscriptionsPlanServiceImplTest {

    @InjectMocks
    private SubscriptionsPlanServiceImpl service;

    @Mock
    private SubscriptionsPlanRepository subscriptionsPlanRepository;

    @Mock
    private SubscriptionsPlanMapper subscriptionsPlanMapper;

    private SubscriptionsPlan sampleEntity;
    private SubscriptionsPlan savedEntity;
    private SubscriptionsPlanDTO sampleDto;
    private CreateSubscriptionsPlanDTO createDto;

    @BeforeEach
    void setUp() {

        sampleEntity = new SubscriptionsPlan();
        sampleEntity.setId(1L);
        sampleEntity.setName(SubscriptionsPlanEnum.FREE);
        sampleEntity.setActive(true);

        savedEntity = new SubscriptionsPlan();
        savedEntity.setId(2L);
        savedEntity.setName(SubscriptionsPlanEnum.PREMIUM_MONTHLY);
        savedEntity.setActive(true);

        sampleDto = mock(SubscriptionsPlanDTO.class);
        createDto = mock(CreateSubscriptionsPlanDTO.class);
    }

    @Test
    void createSubscriptionPlan_success_setsDuration_and_returnsDto() {
        SubscriptionsPlanRequest request = mock(SubscriptionsPlanRequest.class);
        when(request.duration()).thenReturn("P1M");

        when(subscriptionsPlanMapper.toEntity(request)).thenReturn(sampleEntity);
        when(subscriptionsPlanRepository.save(any(SubscriptionsPlan.class))).thenReturn(savedEntity);
        when(subscriptionsPlanMapper.toCreateSubscriptionsPlanDTO(savedEntity)).thenReturn(createDto);

        CreateSubscriptionsPlanDTO result = service.createSubscriptionPlan(request);

        assertSame(createDto, result);

        ArgumentCaptor<SubscriptionsPlan> captor = ArgumentCaptor.forClass(SubscriptionsPlan.class);
        verify(subscriptionsPlanRepository).save(captor.capture());
        SubscriptionsPlan captured = captor.getValue();

        assertEquals(Period.parse("P1M"), captured.getDuration());
        verify(subscriptionsPlanMapper).toEntity(request);
        verify(subscriptionsPlanMapper).toCreateSubscriptionsPlanDTO(savedEntity);
    }

    @Test
    void createSubscriptionPlan_invalidDuration_throwsDateTimeParseException() {
        SubscriptionsPlanRequest request = mock(SubscriptionsPlanRequest.class);
        when(request.duration()).thenReturn("invalid-duration");

        when(subscriptionsPlanMapper.toEntity(request)).thenReturn(sampleEntity);

        assertThrows(DateTimeParseException.class, () -> service.createSubscriptionPlan(request));

        verify(subscriptionsPlanRepository, never()).save(any());
    }

    @Test
    void getSubscriptionPlan_returnsDtos_forPage() {
        int page = 0, size = 5;
        List<SubscriptionsPlan> content = List.of(sampleEntity);
        PageImpl<SubscriptionsPlan> pageImpl = new PageImpl<>(content);
        List<SubscriptionsPlanDTO> expectedDtos = List.of(sampleDto);

        when(subscriptionsPlanRepository.findAll(any(PageRequest.class))).thenReturn(pageImpl);
        when(subscriptionsPlanMapper.toDTOList(content)).thenReturn(expectedDtos);

        List<SubscriptionsPlanDTO> result = service.getSubscriptionPlan(page, size);

        assertEquals(expectedDtos, result);
        verify(subscriptionsPlanRepository).findAll(any(PageRequest.class));
        verify(subscriptionsPlanMapper).toDTOList(content);
    }

    @Test
    void getSubscriptionPlanById_found_returnsDtos() {
        Long id = 1L;
        int page = 0, size = 10;
        List<SubscriptionsPlan> content = List.of(sampleEntity);
        PageImpl<SubscriptionsPlan> pageImpl = new PageImpl<>(content);
        List<SubscriptionsPlanDTO> expectedDtos = List.of(sampleDto);

        when(subscriptionsPlanRepository.findById(eq(id), any(PageRequest.class))).thenReturn(pageImpl);
        when(subscriptionsPlanMapper.toDTOList(content)).thenReturn(expectedDtos);

        List<SubscriptionsPlanDTO> result = service.getSubscriptionPlanById(id, page, size);

        assertEquals(expectedDtos, result);
        verify(subscriptionsPlanRepository).findById(eq(id), any(PageRequest.class));
        verify(subscriptionsPlanMapper).toDTOList(content);
    }

    @Test
    void getSubscriptionPlanById_notFound_throwsSubscriptionPlanNotFoundException() {
        Long id = 99L;
        PageImpl<SubscriptionsPlan> emptyPage = new PageImpl<>(List.of());

        when(subscriptionsPlanRepository.findById(eq(id), any(PageRequest.class))).thenReturn(emptyPage);

        assertThrows(SubscriptionPlanNotFoundException.class, () -> service.getSubscriptionPlanById(id, 0, 10));
        verify(subscriptionsPlanRepository).findById(eq(id), any(PageRequest.class));
    }

    @Test
    void updateSubscriptionPlan_success_updatesDuration_and_returnsDto() {
        Long id = 1L;
        UpdateSubscriptionsPlanRequest request = mock(UpdateSubscriptionsPlanRequest.class);
        when(request.duration()).thenReturn("P2M");

        when(subscriptionsPlanRepository.findById(id)).thenReturn(Optional.of(sampleEntity));
        when(subscriptionsPlanMapper.updateEntityFromRequest(request, sampleEntity)).thenReturn(sampleEntity);
        when(subscriptionsPlanRepository.save(any(SubscriptionsPlan.class))).thenAnswer(inv -> inv.getArgument(0));

        SubscriptionsPlanDTO expectedDto = mock(SubscriptionsPlanDTO.class);
        when(subscriptionsPlanMapper.toDTO(sampleEntity)).thenReturn(expectedDto);

        SubscriptionsPlanDTO result = service.updateSubscriptionPlan(id, request);

        assertSame(expectedDto, result);
        ArgumentCaptor<SubscriptionsPlan> captor = ArgumentCaptor.forClass(SubscriptionsPlan.class);
        verify(subscriptionsPlanRepository).save(captor.capture());
        assertEquals(Period.parse("P2M"), captor.getValue().getDuration());
    }

    @Test
    void updateSubscriptionPlan_notFound_throwsSubscriptionPlanNotFoundException() {
        Long id = 100L;
        UpdateSubscriptionsPlanRequest request = mock(UpdateSubscriptionsPlanRequest.class);
        when(subscriptionsPlanRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(SubscriptionPlanNotFoundException.class, () -> service.updateSubscriptionPlan(id, request));
        verify(subscriptionsPlanRepository).findById(id);
        verify(subscriptionsPlanRepository, never()).save(any());
    }

    @Test
    void updateSubscriptionPlan_invalidDuration_throwsDateTimeParseException() {
        Long id = 1L;
        UpdateSubscriptionsPlanRequest request = mock(UpdateSubscriptionsPlanRequest.class);
        when(request.duration()).thenReturn("bad");
        when(subscriptionsPlanRepository.findById(id)).thenReturn(Optional.of(sampleEntity));

        assertThrows(DateTimeParseException.class, () -> service.updateSubscriptionPlan(id, request));
        verify(subscriptionsPlanRepository, never()).save(any());
    }

    @Test
    void deleteSubscriptionPlan_success_setsActiveFalse_and_saves() {
        Long id = 1L;
        when(subscriptionsPlanRepository.findById(id)).thenReturn(Optional.of(sampleEntity));
        when(subscriptionsPlanRepository.save(any(SubscriptionsPlan.class))).thenReturn(sampleEntity);

        service.deleteSubscriptionPlan(id);

        ArgumentCaptor<SubscriptionsPlan> captor = ArgumentCaptor.forClass(SubscriptionsPlan.class);
        verify(subscriptionsPlanRepository).save(captor.capture());

        assertNotEquals(Boolean.TRUE, captor.getValue().getActive());
    }

    @Test
    void deleteSubscriptionPlan_notFound_throwsSubscriptionPlanNotFoundException() {
        Long id = 123L;
        when(subscriptionsPlanRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(SubscriptionPlanNotFoundException.class, () -> service.deleteSubscriptionPlan(id));
        verify(subscriptionsPlanRepository, never()).save(any());
    }

}
