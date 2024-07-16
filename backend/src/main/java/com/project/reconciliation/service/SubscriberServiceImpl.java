package com.project.reconciliation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.dto.Bill;
import com.project.reconciliation.entities.BillingRecord;
import com.project.reconciliation.entities.Subscriber;
import com.project.reconciliation.exceptions.SubscriberNotFoundException;
import com.project.reconciliation.repositories.BillingRecordRepository;
import com.project.reconciliation.repositories.SubscriberRepository;

@Service
public class SubscriberServiceImpl implements SubscriberService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubscriberServiceImpl.class);
	
	@Autowired
	private SubscriberRepository repo;

	@Autowired
	private BillingRecordRepository billingRecordRepository;

	@Override
	public Subscriber getSubscriberById(Long subscriberId) throws SubscriberNotFoundException {
		logger.info("Fetching subscriber by ID: {}", subscriberId);
		return repo.findById(subscriberId)
				.orElseThrow(() -> new SubscriberNotFoundException("Subscriber not found with ID: " + subscriberId));
	}

	public List<Bill> getBillingDetailsBySubscriberId(Long subscriberId) throws SubscriberNotFoundException {
		logger.info("Fetching billing details for subscriber ID: {}", subscriberId);
		Subscriber subscriber = getSubscriberById(subscriberId);
		List<BillingRecord> billingRecords = new ArrayList<BillingRecord>();
		List<BillingRecord> billRec = (List<BillingRecord>) billingRecordRepository.findAll();
		for(BillingRecord br : billRec) {
			if(br.getSubscriber().getSubscriberId() == subscriberId) {
				billingRecords.add(br);
			}
		}
		if (billingRecords.isEmpty()) {
			logger.warn("No billing records found for subscriber ID: {}", subscriberId);
			throw new SubscriberNotFoundException("No billing records found for subscriber with ID: " + subscriberId);
		}

		return billingRecords.stream().map(record -> {
			Bill dto = new Bill();
			dto.setName(subscriber.getName());
			dto.setEmail(subscriber.getEmail());
			dto.setBillingAmount(record.getTotalAmount());
			dto.setBillingPeriod(record.getBillingPeriod());
			dto.setDueDate(record.getDueDate());
			return dto;
		}).collect(Collectors.toList());
	}
}

