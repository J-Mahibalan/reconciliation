package com.project.reconciliation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.reconciliation.entities.BillingRecord;
import com.project.reconciliation.entities.DataUsageCost;
import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.entities.Subscriber;
import com.project.reconciliation.repositories.BillingRecordRepository;
import com.project.reconciliation.repositories.DataUsageCostRepository;
import com.project.reconciliation.repositories.DiscrepancyRepository;

@ExtendWith(MockitoExtension.class)
public class BillingRecordServiceImplTest {

    @Mock
    private DataUsageCostRepository dataUsageCostRepository;

    @Mock
    private BillingRecordRepository billingRecordRepository;

    @Mock
    private DiscrepancyRepository discrepancyRepository;

    @InjectMocks
    private BillingRecordServiceImpl billingRecordService;

    private Subscriber subscriber;
    private BillingRecord billingRecord;
    private DataUsageCost dataUsageCost;
    private Discrepancy discrepancy;

    @BeforeEach
    void setUp() {
        subscriber = new Subscriber();
        subscriber.setSubscriberId(1L);

        billingRecord = new BillingRecord();
        billingRecord.setBillId(1);
        billingRecord.setSubscriber(subscriber);
        billingRecord.setTotalAmount(100.0);

        dataUsageCost = new DataUsageCost();
        dataUsageCost.setCostId(1);
        dataUsageCost.setSubscriber(subscriber);
        dataUsageCost.setDataCost(110.0);

        discrepancy = new Discrepancy();
        discrepancy.setDiscrepancyId(1);
        discrepancy.setSubscriber(subscriber);
        discrepancy.setDescription("Undercharged cost");
        discrepancy.setResolved(false);
    }

    @Test
    void reconcileAllBillingRecords_Positive() {
        when(dataUsageCostRepository.findAll()).thenReturn(Arrays.asList(dataUsageCost));
        when(billingRecordRepository.findAll()).thenReturn(Arrays.asList(billingRecord));
        when(discrepancyRepository.save(any(Discrepancy.class))).thenReturn(discrepancy);

        List<Discrepancy> discrepancies = billingRecordService.reconcileAllBillingRecords();

        assertEquals(1, discrepancies.size());
        assertEquals("Undercharged cost", discrepancies.get(0).getDescription());
    }

    @Test
    void reconcileAllBillingRecords_NoDiscrepancies() {
        dataUsageCost.setDataCost(100.0);

        when(dataUsageCostRepository.findAll()).thenReturn(Arrays.asList(dataUsageCost));
        when(billingRecordRepository.findAll()).thenReturn(Arrays.asList(billingRecord));

        List<Discrepancy> discrepancies = billingRecordService.reconcileAllBillingRecords();

        assertEquals(0, discrepancies.size());
    }

    @Test
    void reconcileAllBillingRecords_NoDataUsageCost() {
        when(dataUsageCostRepository.findAll()).thenReturn(Collections.emptyList());
        when(billingRecordRepository.findAll()).thenReturn(Arrays.asList(billingRecord));

        List<Discrepancy> discrepancies = billingRecordService.reconcileAllBillingRecords();

        assertEquals(0, discrepancies.size());
    }

    @Test
    void resolveDiscrepancies_Positive() {
        when(discrepancyRepository.findByResolvedFalse()).thenReturn(Arrays.asList(discrepancy));
        when(billingRecordRepository.findBySubscriber(any(Subscriber.class))).thenReturn(billingRecord);
        when(dataUsageCostRepository.findBySubscriber(any(Subscriber.class))).thenReturn(Arrays.asList(dataUsageCost));
        when(billingRecordRepository.save(any(BillingRecord.class))).thenReturn(billingRecord);
        when(discrepancyRepository.save(any(Discrepancy.class))).thenReturn(discrepancy);

        List<BillingRecord> resolvedRecords = billingRecordService.resolveDiscrepancies();

        assertEquals(1, resolvedRecords.size());
        assertTrue(discrepancy.getResolved());
    }

    @Test
    void resolveDiscrepancies_NoUnresolvedDiscrepancies() {
        when(discrepancyRepository.findByResolvedFalse()).thenReturn(Collections.emptyList());
        when(billingRecordRepository.findAll()).thenReturn(Arrays.asList(billingRecord));

        List<BillingRecord> resolvedRecords = billingRecordService.resolveDiscrepancies();

        assertEquals(1, resolvedRecords.size());
    }

    @Test
    void resolveDiscrepancies_NoBillingRecord() {
        when(discrepancyRepository.findByResolvedFalse()).thenReturn(Arrays.asList(discrepancy));
        when(billingRecordRepository.findBySubscriber(any(Subscriber.class))).thenReturn(null);

        List<BillingRecord> resolvedRecords = billingRecordService.resolveDiscrepancies();

        assertEquals(0, resolvedRecords.size());
    }
}
