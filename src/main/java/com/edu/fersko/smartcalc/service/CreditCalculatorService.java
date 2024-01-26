package com.edu.fersko.smartcalc.service;

import com.edu.fersko.smartcalc.models.type.CreditData;
import com.edu.fersko.smartcalc.models.type.InputCreditData;

import java.util.Map;

public interface CreditCalculatorService {
	Map<String, Double> calculateCredit(InputCreditData data);
}
