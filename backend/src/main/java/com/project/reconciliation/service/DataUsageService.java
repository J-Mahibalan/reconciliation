package com.project.reconciliation.service;


import java.util.List;

import com.project.reconciliation.entities.DataUsage;
import com.project.reconciliation.entities.DataUsageCost;

public interface DataUsageService {
	public DataUsage addData(DataUsage dataUse);
	public List<DataUsageCost> calculateDataUsageCost();
}
