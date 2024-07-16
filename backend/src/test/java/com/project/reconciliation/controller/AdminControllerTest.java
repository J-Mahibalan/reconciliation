package com.project.reconciliation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.reconciliation.dto.Bill;
import com.project.reconciliation.entities.BillingRecord;
import com.project.reconciliation.entities.DataUsage;
import com.project.reconciliation.entities.DataUsageCost;
import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.entities.request.GetByIdLong;
import com.project.reconciliation.exceptions.BillingRecordNotFoundException;
import com.project.reconciliation.exceptions.DiscrepancyNotFoundException;
import com.project.reconciliation.exceptions.SubscriberNotFoundException;
import com.project.reconciliation.service.BillingRecordService;
import com.project.reconciliation.service.DataUsageService;
import com.project.reconciliation.service.DiscrepancyService;
import com.project.reconciliation.service.SubscriberService;
import com.project.reconciliation.utilities.FetchDataUsageService;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private DataUsageService dataUsageService;

    @Mock
    private BillingRecordService billingRecordService;

    @Mock
    private SubscriberService subscriberService;

    @Mock
    private DiscrepancyService discrepancyService;
    
    @Mock
	private FetchDataUsageService fetchService;

    @BeforeEach
    void setUp() {
        adminController = new AdminController();
        adminController.setDataUsageService(dataUsageService);
        adminController.setBillingRecordService(billingRecordService);
        adminController.setSubscriberService(subscriberService);
        adminController.setDiscrepancyService(discrepancyService);
        adminController.setFetchService(fetchService);
    }

    @Test
    public void fetchDataUsage_Positive() throws FileNotFoundException, IOException, SubscriberNotFoundException {
        List<DataUsage> mockDataUsageList = Collections.singletonList(new DataUsage());
        when(fetchService.fetchData()).thenReturn(mockDataUsageList);

        ResponseEntity<List<DataUsage>> response = adminController.fetchDataUsage();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockDataUsageList);
    }

    @Test
    public void fetchDataUsage_FileNotFoundException() throws FileNotFoundException, IOException, SubscriberNotFoundException {
        when(fetchService.fetchData()).thenThrow(FileNotFoundException.class);

        assertThrows(FileNotFoundException.class, () -> {
            adminController.fetchDataUsage();
        });
    }

    @Test
    public void fetchDataUsage_IOException() throws FileNotFoundException, IOException, SubscriberNotFoundException {
        when(fetchService.fetchData()).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> {
            adminController.fetchDataUsage();
        });
    }

    @Test
    public void calculateDataUsageCosts_Positive() {
        List<DataUsageCost> mockDataUsageCostList = Collections.singletonList(new DataUsageCost());
        when(dataUsageService.calculateDataUsageCost()).thenReturn(mockDataUsageCostList);

        ResponseEntity<List<DataUsageCost>> response = adminController.calculateDataUsageCosts();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockDataUsageCostList);
    }

    @Test
    public void reconcileAllBillingRecords_Positive() throws DiscrepancyNotFoundException {
        List<Discrepancy> mockDiscrepancyList = Collections.singletonList(new Discrepancy());
        when(billingRecordService.reconcileAllBillingRecords()).thenReturn(mockDiscrepancyList);

        ResponseEntity<List<Discrepancy>> response = adminController.reconcileAllBillingRecords();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockDiscrepancyList);
    }

    @Test
    public void reconcileAllBillingRecords_DiscrepancyNotFoundException() throws DiscrepancyNotFoundException {
        when(billingRecordService.reconcileAllBillingRecords()).thenThrow(DiscrepancyNotFoundException.class);

        assertThrows(DiscrepancyNotFoundException.class, () -> {
            adminController.reconcileAllBillingRecords();
        });
    }

    @Test
    public void resolveDiscrepancies_Positive() throws BillingRecordNotFoundException {
        List<BillingRecord> mockBillingRecordList = Collections.singletonList(new BillingRecord());
        when(billingRecordService.resolveDiscrepancies()).thenReturn(mockBillingRecordList);

        ResponseEntity<List<BillingRecord>> response = adminController.resolveDiscrepancies();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockBillingRecordList);
    }

    @Test
    public void resolveDiscrepancies_BillingRecordNotFoundException() throws BillingRecordNotFoundException {
        when(billingRecordService.resolveDiscrepancies()).thenThrow(BillingRecordNotFoundException.class);

        assertThrows(BillingRecordNotFoundException.class, () -> {
            adminController.resolveDiscrepancies();
        });
    }

    @Test
    public void getBillingDetails_Positive() throws SubscriberNotFoundException {
        List<Bill> mockBillList = Collections.singletonList(new Bill());
        GetByIdLong mockRequest = new GetByIdLong();
        mockRequest.setId(1L);
        when(subscriberService.getBillingDetailsBySubscriberId(mockRequest.getId())).thenReturn(mockBillList);

        ResponseEntity<List<Bill>> response = adminController.getBillingDetails(mockRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockBillList);
    }

    @Test
    public void getBillingDetails_SubscriberNotFoundException() throws SubscriberNotFoundException {
        GetByIdLong mockRequest = new GetByIdLong();
        mockRequest.setId(1L);
        when(subscriberService.getBillingDetailsBySubscriberId(mockRequest.getId())).thenThrow(SubscriberNotFoundException.class);

        assertThrows(SubscriberNotFoundException.class, () -> {
            adminController.getBillingDetails(mockRequest);
        });
    }

    @Test
    public void getAllDiscrepancy_Positive() throws DiscrepancyNotFoundException {
        List<Discrepancy> mockDiscrepancyList = Collections.singletonList(new Discrepancy());
        when(discrepancyService.getAllDiscrepancy()).thenReturn(mockDiscrepancyList);

        ResponseEntity<List<Discrepancy>> response = adminController.getAllDiscrepancy();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(mockDiscrepancyList);
    }

    @Test
    public void getAllDiscrepancy_DiscrepancyNotFoundException() throws DiscrepancyNotFoundException {
        when(discrepancyService.getAllDiscrepancy()).thenThrow(DiscrepancyNotFoundException.class);

        assertThrows(DiscrepancyNotFoundException.class, () -> {
            adminController.getAllDiscrepancy();
        });
    }
}
