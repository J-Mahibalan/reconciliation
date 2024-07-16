package com.project.reconciliation.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.exceptions.DiscrepancyNotFoundException;
import com.project.reconciliation.repositories.DiscrepancyRepository;

@Service
public class DiscrepancyServiceImpl implements DiscrepancyService {
	
	private static final Logger logger = LoggerFactory.getLogger(DiscrepancyServiceImpl.class);

	@Autowired
	private DiscrepancyRepository discrepancyRepository;

	@Override
	public List<Discrepancy> getAllDiscrepancy() throws DiscrepancyNotFoundException {
		 logger.info("Fetching all discrepancies");
		 List<Discrepancy> discrepancies = (List<Discrepancy>) discrepancyRepository.findAll();
	        if (discrepancies.isEmpty()) {
	            logger.error("No discrepancies found");
	            throw new DiscrepancyNotFoundException("No discrepancies found");
	        }
	        return discrepancies;
	}
}