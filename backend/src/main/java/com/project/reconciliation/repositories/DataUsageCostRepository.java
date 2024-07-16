package com.project.reconciliation.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.reconciliation.entities.DataUsageCost;
import com.project.reconciliation.entities.Subscriber;

public interface DataUsageCostRepository extends CrudRepository<DataUsageCost, Integer> {

	boolean existsBySubscriberAndStartTimeAndEndTime(Subscriber subscriber, LocalDateTime startTime, LocalDateTime endTime);

	List<DataUsageCost> findBySubscriber(Subscriber subscriber);

}
