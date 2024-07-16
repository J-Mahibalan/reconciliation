package com.project.reconciliation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.reconciliation.entities.DataUsage;
import com.project.reconciliation.entities.DataUsageCost;
import com.project.reconciliation.entities.Subscriber;
import com.project.reconciliation.repositories.DataUsageCostRepository;
import com.project.reconciliation.repositories.DataUsageRepository;
import com.project.reconciliation.repositories.SubscriberRepository;

@ExtendWith(MockitoExtension.class)
public class DataUsageServiceImplTest {

    @Mock
    private DataUsageRepository dataUsageRepository;

    @Mock
    private DataUsageCostRepository costRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private DataUsageServiceImpl dataUsageService;

    private DataUsage dataUsage;
    private Subscriber subscriber;

    @BeforeEach
    void setUp() {
        subscriber = new Subscriber();
        subscriber.setSubscriberId(1L);
        subscriber.setName("John Doe");
        subscriber.setEmail("john.doe@example.com");

        dataUsage = new DataUsage();
        dataUsage.setDataId(1);
        dataUsage.setSubscriber(subscriber);
        dataUsage.setStartTime(LocalDateTime.of(2021, 7, 1, 10, 0));
        dataUsage.setEndTime(LocalDateTime.of(2021, 7, 1, 11, 0));
        dataUsage.setDataConsumed(500);
    }

    @Test
    void addData_Positive() {
        when(dataUsageRepository.save(any(DataUsage.class))).thenReturn(dataUsage);

        DataUsage result = dataUsageService.addData(dataUsage);

        assertEquals(dataUsage, result);
        verify(dataUsageRepository, times(1)).save(dataUsage);
    }

    @Test
    void addData_Negative_AlreadyExists() {
        when(dataUsageRepository.existsById(dataUsage.getDataId())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            dataUsageService.addData(dataUsage);
        });

        assertEquals("Data with ID " + dataUsage.getDataId() + " already exists", exception.getMessage());
    }

    @Test
    void calculateDataUsageCost_Positive() {
        when(dataUsageRepository.findAll()).thenReturn(List.of(dataUsage));
        when(costRepository.existsBySubscriberAndStartTimeAndEndTime(any(), any(), any())).thenReturn(false);
        when(costRepository.save(any(DataUsageCost.class))).thenReturn(new DataUsageCost());

        List<DataUsageCost> result = dataUsageService.calculateDataUsageCost();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(costRepository, times(1)).save(any(DataUsageCost.class));
    }

    @Test
    void calculateDataUsageCost_Negative_AlreadyExists() {
        when(dataUsageRepository.findAll()).thenReturn(List.of(dataUsage));
        when(costRepository.existsBySubscriberAndStartTimeAndEndTime(any(), any(), any())).thenReturn(true);

        List<DataUsageCost> result = dataUsageService.calculateDataUsageCost();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(costRepository, never()).save(any(DataUsageCost.class));
    }
}
