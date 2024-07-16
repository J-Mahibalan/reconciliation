package com.project.reconciliation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.reconciliation.dto.Bill;
import com.project.reconciliation.entities.BillingRecord;
import com.project.reconciliation.entities.Subscriber;
import com.project.reconciliation.exceptions.SubscriberNotFoundException;
import com.project.reconciliation.repositories.BillingRecordRepository;
import com.project.reconciliation.repositories.SubscriberRepository;

@ExtendWith(MockitoExtension.class)
public class SubscriberServiceImplTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private BillingRecordRepository billingRecordRepository;

    @InjectMocks
    private SubscriberServiceImpl subscriberService;

    private Subscriber subscriber;
    private BillingRecord billingRecord;

    @BeforeEach
    void setUp() {
        subscriber = new Subscriber();
        subscriber.setSubscriberId(1L);
        subscriber.setName("John Doe");
        subscriber.setEmail("john.doe@example.com");

        billingRecord = new BillingRecord();
        billingRecord.setSubscriber(subscriber);
        billingRecord.setTotalAmount(100.0);
        billingRecord.setBillingPeriod("2021-06");
        billingRecord.setDueDate(LocalDate.of(2021, 07, 01));
    }

    @Test
    void getSubscriberById_Positive() throws SubscriberNotFoundException {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));

        Subscriber result = subscriberService.getSubscriberById(1L);

        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());
    }

    @Test
    void getSubscriberById_Negative() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        SubscriberNotFoundException exception = assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.getSubscriberById(1L);
        });

        assertEquals("Subscriber not found with ID: 1", exception.getMessage());
    }

    @Test
    void getBillingDetailsBySubscriberId_Positive() throws SubscriberNotFoundException {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(billingRecordRepository.findAll()).thenReturn(Arrays.asList(billingRecord));

        List<Bill> bills = subscriberService.getBillingDetailsBySubscriberId(1L);

        assertEquals(1, bills.size());
        Bill bill = bills.get(0);
        assertEquals("John Doe", bill.getName());
        assertEquals("john.doe@example.com", bill.getEmail());
        assertEquals(100.0, bill.getBillingAmount());
        assertEquals("2021-06", bill.getBillingPeriod());
        assertEquals(LocalDate.of(2021, 07, 01), bill.getDueDate());
    }

    @Test
    void getBillingDetailsBySubscriberId_Negative_NoSubscriber() {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.empty());

        SubscriberNotFoundException exception = assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.getBillingDetailsBySubscriberId(1L);
        });

        assertEquals("Subscriber not found with ID: 1", exception.getMessage());
    }

    @Test
    void getBillingDetailsBySubscriberId_Negative_NoBillingRecords() throws SubscriberNotFoundException {
        when(subscriberRepository.findById(1L)).thenReturn(Optional.of(subscriber));
        when(billingRecordRepository.findAll()).thenReturn(Collections.emptyList());

        SubscriberNotFoundException exception = assertThrows(SubscriberNotFoundException.class, () -> {
            subscriberService.getBillingDetailsBySubscriberId(1L);
        });

        assertEquals("No billing records found for subscriber with ID: 1", exception.getMessage());
    }
}
