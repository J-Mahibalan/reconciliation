package com.project.reconciliation.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.entities.DataUsage;
import com.project.reconciliation.repositories.DataUsageRepository;
import com.project.reconciliation.repositories.SubscriberRepository;

@Service
public class FetchDataUsageServiceImpl implements FetchDataUsageService {
	
	private static final Logger logger = LoggerFactory.getLogger(FetchDataUsageServiceImpl.class);
	
	@Autowired
	private SubscriberRepository subscriberRepository;
	
	@Autowired
	private DataUsageRepository dataUsageRepository;
	
	public List<DataUsage> fetchData() throws FileNotFoundException, IOException{
		logger.info("Fetching data usage from file");
		String file = "data_usage.csv";
		var path = Paths.get("src/main/resources/data/", file).toString()	;
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				System.out.println(parts);
				Integer dataId = Integer.parseInt(parts[0]);
				Long subscriberId = Long.parseLong(parts[1]);
				LocalDateTime startTime = LocalDateTime.parse(parts[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime endTime = LocalDateTime.parse(parts[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				Integer dataConsumed = Integer.parseInt(parts[4]);

				DataUsage data = new DataUsage();

				data.setDataId(dataId);
				data.setSubscriber(subscriberRepository.findById(subscriberId).get());
				data.setStartTime(startTime);
				data.setEndTime(endTime);
				data.setDataConsumed(dataConsumed);

				dataUsageRepository.save(data);
				logger.info("Saved data usage entry with ID: {}", dataId);
			}
		}
		logger.info("Fetched all data usage entries");
		return (List<DataUsage>) dataUsageRepository.findAll();
	}
}
