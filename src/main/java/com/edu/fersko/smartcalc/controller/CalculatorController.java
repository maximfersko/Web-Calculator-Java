package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.exceptions.NativeCalculationException;
import com.edu.fersko.smartcalc.models.type.ResultResponse;
import com.edu.fersko.smartcalc.service.HistoryService;
import com.edu.fersko.smartcalc.service.SmartCalculatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
public class CalculatorController {
	private final SmartCalculatorService smartCalculatorService;
	private final HistoryService historyService;

	@GetMapping("/history")
	public List<String> getHistory() {
		return historyService.getHistory();
	}

	@PostMapping("/calculate")
	public ResponseEntity<ResultResponse> calculate(@RequestBody Map<String, String> requestBody) {
		String expression = requestBody.get("expression");
		try {
			return ResponseEntity.ok(smartCalculatorService.calculateExpression(expression, 0));
		} catch (
				NativeCalculationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultResponse("Error during calculation"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultResponse("Internal server error"));
		}

	}

	@PostMapping("/clearHistory")
	public ResponseEntity<String> clearHistory() {
		historyService.clear();
		return ResponseEntity.ok("History cleared.");
	}
}
