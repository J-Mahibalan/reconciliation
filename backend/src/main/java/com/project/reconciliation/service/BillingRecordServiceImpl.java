package com.project.reconciliation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.entities.BillingRecord;
import com.project.reconciliation.entities.DataUsageCost;
import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.entities.Subscriber;
import com.project.reconciliation.repositories.BillingRecordRepository;
import com.project.reconciliation.repositories.DataUsageCostRepository;
import com.project.reconciliation.repositories.DiscrepancyRepository;

@Service
public class BillingRecordServiceImpl implements BillingRecordService {

	private static final Logger logger = LoggerFactory.getLogger(BillingRecordServiceImpl.class);

	@Autowired
	private DataUsageCostRepository dataUsageCostRepository;

	@Autowired
	private BillingRecordRepository billingRecordRepository;

	@Autowired
	private DiscrepancyRepository discrepancyRepository;

	@Override
	public List<Discrepancy> reconcileAllBillingRecords() {
		logger.info("Starting reconciliation of all billing records");
		List<DataUsageCost> allDataUsageCosts = (List<DataUsageCost>) dataUsageCostRepository.findAll();
		List<BillingRecord> allBillingRecords = (List<BillingRecord>) billingRecordRepository.findAll();
		List<Discrepancy> discrepancies = new ArrayList<Discrepancy>();

		// Group data usage costs by subscriber
		Map<Subscriber, List<DataUsageCost>> dataUsageCostBySubscriber = allDataUsageCosts.stream()
				.collect(Collectors.groupingBy(dataUsageCost -> dataUsageCost.getSubscriber()));
//		if(allBillingRecords.isEmpty()) {
			for (BillingRecord billingRecord : allBillingRecords) {
				List<DataUsageCost> dataUsageCosts = dataUsageCostBySubscriber.get(billingRecord.getSubscriber());
				if (dataUsageCosts != null) {
					double totalDataUsageCost = dataUsageCosts.stream().mapToDouble(DataUsageCost::getDataCost).sum();

					if (totalDataUsageCost != billingRecord.getTotalAmount()) {
						String description = totalDataUsageCost > billingRecord.getTotalAmount() ? "Undercharged cost" : "Overcharged cost";
						Discrepancy discrepancy = new Discrepancy();
						discrepancy.setSubscriber(billingRecord.getSubscriber());
						discrepancy.setDescription(description);
						discrepancy.setResolved(false);
						discrepancyRepository.save(discrepancy);
						discrepancies.add(discrepancy);
						logger.info("Discrepancy added for subscriber ID: {}", billingRecord.getSubscriber().getSubscriberId());
					}
				}
			}
//		}
//		else {
//			discrepancies=(List<Discrepancy>) discrepancyRepository.findAll();
//		}
		logger.info("Reconciliation complete with {} discrepancies found", discrepancies.size());
		return discrepancies;
	}

	@Override
	public List<BillingRecord> resolveDiscrepancies() {
		logger.info("Starting to resolve discrepancies");
		List<Discrepancy> unresolvedDiscrepancies = discrepancyRepository.findByResolvedFalse();
		List<BillingRecord> updatedBillingRecords = new ArrayList<>();

		if(!unresolvedDiscrepancies.isEmpty()) {
			for (Discrepancy discrepancy : unresolvedDiscrepancies) {
				BillingRecord billingRecord = billingRecordRepository.findBySubscriber(discrepancy.getSubscriber());

				if (billingRecord != null) {
					List<DataUsageCost> dataUsageCosts = dataUsageCostRepository.findBySubscriber(billingRecord.getSubscriber());
					double totalDataUsageCost = dataUsageCosts.stream().mapToDouble(DataUsageCost::getDataCost).sum();

					if (totalDataUsageCost != billingRecord.getTotalAmount()) {
						billingRecord.setTotalAmount(totalDataUsageCost);
						billingRecordRepository.save(billingRecord);
						updatedBillingRecords.add(billingRecord);

						discrepancy.setResolved(true);
						discrepancyRepository.save(discrepancy);
						logger.info("Resolved discrepancy for subscriber ID: {}", billingRecord.getSubscriber().getSubscriberId());
					}
				}
			}
		} else {
			updatedBillingRecords = (List<BillingRecord>) billingRecordRepository.findAll();
		}
		logger.info("Resolution complete with {} billing records updated", updatedBillingRecords.size());
		return updatedBillingRecords;
	}
}
