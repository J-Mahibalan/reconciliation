package com.project.reconciliation.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.reconciliation.entities.Discrepancy;

@Repository
public interface DiscrepancyRepository extends CrudRepository<Discrepancy, Integer> {

	List<Discrepancy> findByResolvedFalse();
}
