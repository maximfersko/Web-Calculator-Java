package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.CreditModelJNIWrapper;
import com.edu.fersko.smartcalc.models.dataType.CreditData;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CreditController {
	private final CreditModelJNIWrapper creditModelWrapper;

	public CreditController() {
		this.creditModelWrapper = new CreditModelJNIWrapper();
	}

	@PostMapping("/calculateCredit")
	public ResponseEntity<Map<String, Double>> calculateCredit(@RequestBody Map<String, String> requestBody) {
		double loanAmount = Double.parseDouble(requestBody.get("amour"));
		int loanTerm = Integer.parseInt(requestBody.get("term"));
		double interestRate = Double.parseDouble(requestBody.get("rate"));
		String calcType = requestBody.get("calcType");

		if (calcType.equals("annuity")) {
			creditModelWrapper.annuity(loanAmount, loanTerm, interestRate);
		} else if (calcType.equals("differentiated")) {
			creditModelWrapper.deffirentated(loanAmount, interestRate, loanTerm);
		}

		CreditData data = creditModelWrapper.getResult();

		Map<String, Double> responseData = new HashMap<>();
		responseData.put("monthlyPayment", data.getMonthlyPayment());
		responseData.put("overPayment", data.getOverPayment());
		responseData.put("totalPayment", data.getTotalPayment());
		responseData.put("maxMonthlyPayment", data.getMaxMonthlyPayment());
		responseData.put("minMonthlyPayment", data.getMinMonthlyPayment());

		return ResponseEntity.ok(responseData);
	}
}
