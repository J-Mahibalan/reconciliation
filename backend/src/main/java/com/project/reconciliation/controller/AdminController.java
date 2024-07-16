package com.project.reconciliation.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private DataUsageService dataUsageService;

	@Autowired
	private BillingRecordService billingRecordService;

	@Autowired
	private SubscriberService subscriberService;
	
	@Autowired
	private DiscrepancyService discrepancyService;
	
	@Autowired
	private FetchDataUsageService fetchService;
	
	protected AdminController() {
    }

    @Autowired
    public AdminController(DataUsageService dataUsageService,
                           BillingRecordService billingRecordService,
                           SubscriberService subscriberService,
                           DiscrepancyService discrepancyService,
                           FetchDataUsageService fetchService) {
        this.dataUsageService = dataUsageService;
        this.billingRecordService = billingRecordService;
        this.subscriberService = subscriberService;
        this.discrepancyService = discrepancyService;
        this.fetchService = fetchService;
    }
    
    public void setDataUsageService(DataUsageService dataUsageService) {
        this.dataUsageService = dataUsageService;
    }
    
    public void setFetchService(FetchDataUsageService fetchService) {
        this.fetchService = fetchService;
    }
    
    public void setBillingRecordService(BillingRecordService billingRecordService) {
        this.billingRecordService = billingRecordService;
    }
    
    public void setDiscrepancyService(DiscrepancyService discrepancyService) {
        this.discrepancyService = discrepancyService;
    }
    
    public void setSubscriberService(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

	@GetMapping("/fetchdatausagedetails")
	public ResponseEntity<List<DataUsage>> fetchDataUsage() throws FileNotFoundException, IOException, SubscriberNotFoundException {
		logger.info("Fetching datausage");
		List<DataUsage> dataList = fetchService.fetchData();
		return new ResponseEntity<List<DataUsage>>(dataList, HttpStatus.OK);
	}

	@GetMapping("/calculatecost")
	public ResponseEntity<List<DataUsageCost>> calculateDataUsageCosts() {
		logger.info("Calculating data usage costs");
		List<DataUsageCost> dataUsageCostList = dataUsageService.calculateDataUsageCost();
		return new ResponseEntity<List<DataUsageCost>>(dataUsageCostList, HttpStatus.OK);
	}
	
	@GetMapping("/reconcile")
	public ResponseEntity<List<Discrepancy>> reconcileAllBillingRecords() throws DiscrepancyNotFoundException {
		logger.info("Reconciling all billing records");
		List<Discrepancy> discrepancyList = billingRecordService.reconcileAllBillingRecords();
		return new ResponseEntity<List<Discrepancy>>(discrepancyList, HttpStatus.OK);
	}
	
	@GetMapping("/resolvediscrepancies")
	public ResponseEntity<List<BillingRecord>> resolveDiscrepancies() throws BillingRecordNotFoundException {
		logger.info("Resolving discrepancies");
		List<BillingRecord> billingRecords = billingRecordService.resolveDiscrepancies();
		return new ResponseEntity<List<BillingRecord>>(billingRecords, HttpStatus.OK);
	}

	@PostMapping("/billdetailsbysubscriber")
	public ResponseEntity<List<Bill>> getBillingDetails(@RequestBody GetByIdLong subscriber) throws SubscriberNotFoundException {
		 logger.info("Getting billing details for subscriber ID: {}", subscriber.getId());
		 List<Bill> bills = subscriberService.getBillingDetailsBySubscriberId(subscriber.getId());
		 return new ResponseEntity<List<Bill>>(bills, HttpStatus.OK);
	}
	
	@GetMapping("/getalldiscrepancy")
    public ResponseEntity<List<Discrepancy>> getAllDiscrepancy() throws DiscrepancyNotFoundException {
        logger.info("Getting all discrepancies");
        List<Discrepancy> message = discrepancyService.getAllDiscrepancy();
        return new ResponseEntity<List<Discrepancy>>(message, HttpStatus.OK);
    }
}