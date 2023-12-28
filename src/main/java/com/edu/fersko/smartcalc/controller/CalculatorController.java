package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.exceptions.NativeCalculationException;
import com.edu.fersko.smartcalc.models.SmartCalcJNIWrapper;
import com.edu.fersko.smartcalc.models.dataType.ResultResponse;
import com.edu.fersko.smartcalc.service.CalculatorUtilitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class CalculatorController {
	private final SmartCalcJNIWrapper coreSmartCalc;
	private final CalculatorUtilitiesService service;

	@Autowired
	public CalculatorController(SmartCalcJNIWrapper coreSmartCalc, CalculatorUtilitiesService service) {
		this.coreSmartCalc = coreSmartCalc;
		this.service = service;
		service.loadHistory();
	}

	@GetMapping("/history")
	public List<String> getHistory() {
		return service.getHistory();
	}

	@PostMapping("/calculate")
	public ResponseEntity<ResultResponse> calculate(@RequestBody Map<String, String> requestBody) {
		String expression = requestBody.get("expression");
		double result;

		try {
			result = coreSmartCalc.getResult(expression, 0);

			String calculation = expression + " = " + result;
			service.getHistory().add(calculation);
			service.writeHistoryToFile();

			ResultResponse resultResponse = new ResultResponse("Success");
			resultResponse.setResult(result);

			return ResponseEntity.ok(resultResponse);
		} catch (
				NativeCalculationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultResponse("Error during calculation"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultResponse("Internal server error"));
		}

	}

	@PostMapping("/clearHistory")
	public ResponseEntity<String> clearHistory() {
		service.getHistory().clear();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CalculatorUtilitiesService.getHistoryFilePath()))) {
			writer.write("");
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return ResponseEntity.ok("History cleared.");
	}
}
