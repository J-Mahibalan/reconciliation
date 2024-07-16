package com.project.reconciliation.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.project.reconciliation.entities.DataUsage;

public interface FetchDataUsageService {
	List<DataUsage> fetchData() throws FileNotFoundException,IOException;
}
