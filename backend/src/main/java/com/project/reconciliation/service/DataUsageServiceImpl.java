package com.project.reconciliation.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.entities.DataUsage;
import com.project.reconciliation.entities.DataUsageCost;
import com.project.reconciliation.repositories.DataUsageCostRepository;
import com.project.reconciliation.repositories.DataUsageRepository;


@Service
public class DataUsageServiceImpl implements DataUsageService {

	private static final Logger logger = LoggerFactory.getLogger(DataUsageServiceImpl.class);
	@Autowired
	private DataUsageRepository dataUsageRepository;

	@Autowired
	private DataUsageCostRepository costRepository;

	@Override
	public DataUsage addData(DataUsage dataUse) {
		logger.info("Adding data usage entry with ID: {}", dataUse.getDataId());
		if (dataUse.getDataId() != null && dataUsageRepository.existsById(dataUse.getDataId())) {
			throw new IllegalArgumentException("Data with ID " + dataUse.getDataId() + " already exists");
		}
		DataUsage du = dataUsageRepository.save(dataUse);
		return du;
	}



	@Override
	public List<DataUsageCost> calculateDataUsageCost() {
		logger.info("Calculating data usage costs");
		Iterable<DataUsage> dataUsageList = dataUsageRepository.findAll();
		List<DataUsageCost> dataUsageCosts = new ArrayList<>();
		for (DataUsage dataUsage : dataUsageList) {
			boolean exists = costRepository.existsBySubscriberAndStartTimeAndEndTime(
					dataUsage.getSubscriber(), dataUsage.getStartTime(), dataUsage.getEndTime());
			if (!exists) {

				double dataConsumedInGB = dataUsage.getDataConsumed()/ 1000.0; // Convert MB to GB
				double cost = dataConsumedInGB * 6.0; // 1 Rs per GB

				DataUsageCost dataUsageCost = new DataUsageCost();
				dataUsageCost.setSubscriber(dataUsage.getSubscriber());
				dataUsageCost.setStartTime(dataUsage.getStartTime());
				dataUsageCost.setEndTime(dataUsage.getEndTime());
				dataUsageCost.setData(dataUsage.getDataConsumed());
				dataUsageCost.setDataCost(cost);
				dataUsageCost.setCostPerGB(6); // Set cost per GB to 1 Rs
				costRepository.save(dataUsageCost);
				dataUsageCosts.add(dataUsageCost);
				logger.info("Calculated and saved cost for data usage ID: {}", dataUsage.getDataId());
			}
			else {
				logger.info("Data usage cost already exists for subscriber ID: {}", dataUsage.getSubscriber().getSubscriberId());
				return (List<DataUsageCost>) costRepository.findAll();
			}
		}
		logger.info("Calculated data usage costs for all entries");
		return dataUsageCosts;
	}
}
