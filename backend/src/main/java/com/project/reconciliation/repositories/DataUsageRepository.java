package com.project.reconciliation.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.reconciliation.entities.DataUsage;
import com.project.reconciliation.entities.Subscriber;

@Repository
public interface DataUsageRepository extends CrudRepository<DataUsage, Integer>{
	List<DataUsage> findBySubscriber(Subscriber subscriber);
}
