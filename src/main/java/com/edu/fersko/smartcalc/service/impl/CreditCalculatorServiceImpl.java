package com.edu.fersko.smartcalc.service.impl;

import com.edu.fersko.smartcalc.models.CreditModelJNIWrapper;
import com.edu.fersko.smartcalc.models.type.CreditData;
import com.edu.fersko.smartcalc.models.type.InputCreditData;
import com.edu.fersko.smartcalc.service.CreditCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class CreditCalculatorServiceImpl implements CreditCalculatorService {

	private final CreditModelJNIWrapper creditModelJNIWrapper;

	@Override
	public Map<String, Double> calculateCredit(InputCreditData data) {
		if (data.getCreditType().equals("annuity")) {
			creditModelJNIWrapper.annuity(data.getLoanAmount(), data.getLoanTerm(), data.getInterestRate());
		} else if (data.getCreditType().equals("differentiated")) {
			creditModelJNIWrapper.deffirentated(data.getLoanAmount(), data.getLoanTerm(), data.getInterestRate());
		}

		CreditData creditData = creditModelJNIWrapper.getResult();

		Map<String, Double> responseData = new HashMap<>();
		responseData.put("monthlyPayment", creditData.getMonthlyPayment());
		responseData.put("overPayment", creditData.getOverPayment());
		responseData.put("totalPayment", creditData.getTotalPayment());
		responseData.put("maxMonthlyPayment", creditData.getMaxMonthlyPayment());
		responseData.put("minMonthlyPayment", creditData.getMinMonthlyPayment());

		return responseData;
	}
}
