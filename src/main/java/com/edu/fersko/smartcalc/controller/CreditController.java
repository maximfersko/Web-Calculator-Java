package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.type.InputCreditData;
import com.edu.fersko.smartcalc.service.CreditCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class CreditController {
	private final CreditCalculatorService creditCalculatorService;

	@PostMapping("/calculateCredit")
	public ResponseEntity<Map<String, Double>> calculateCredit(@RequestBody Map<String, String> requestBody) {
		double loanAmount = Double.parseDouble(requestBody.get("amour"));
		int loanTerm = Integer.parseInt(requestBody.get("term"));
		double interestRate = Double.parseDouble(requestBody.get("rate"));
		String calcType = requestBody.get("calcType");

		InputCreditData inputCreditData = InputCreditData.builder()
				.creditType(calcType)
				.loanAmount(loanAmount)
				.interestRate(interestRate)
				.loanTerm(loanTerm)
				.build();

		return ResponseEntity.ok(creditCalculatorService.calculateCredit(inputCreditData));
	}
}
