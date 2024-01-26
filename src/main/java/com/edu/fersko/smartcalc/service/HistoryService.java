package com.edu.fersko.smartcalc.service;

import java.util.List;

public interface HistoryService {
	void addItemToHistory(String item);

	void writeHistoryToFile();

	void clear();

	List<String> getHistory();
}
