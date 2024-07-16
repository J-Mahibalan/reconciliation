package com.project.reconciliation.service;

import java.util.List;

import com.project.reconciliation.entities.BillingRecord;
import com.project.reconciliation.entities.Discrepancy;
import com.project.reconciliation.exceptions.BillingRecordNotFoundException;
import com.project.reconciliation.exceptions.DiscrepancyNotFoundException;

public interface BillingRecordService {
    List<Discrepancy> reconcileAllBillingRecords()  throws DiscrepancyNotFoundException;

	List<BillingRecord> resolveDiscrepancies() throws BillingRecordNotFoundException;
}
