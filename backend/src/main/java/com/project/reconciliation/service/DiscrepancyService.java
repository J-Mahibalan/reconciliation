package com.project.reconciliation.service;

import java.util.List;

import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.exceptions.DiscrepancyNotFoundException;

public interface DiscrepancyService {
	public List<Discrepancy> getAllDiscrepancy() throws DiscrepancyNotFoundException;
}
