package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.type.GraphData;
import com.edu.fersko.smartcalc.service.GraphCalculatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class GraphController {
	private final GraphCalculatorService graphBuilder;


	@PostMapping("/calculateGraph")
	public ResponseEntity<GraphData> calculateGraph(
			@RequestParam String expression,
			@RequestParam double xStart,
			@RequestParam double xEnd) {
		try {
			return ResponseEntity.ok(graphBuilder.calculateGraphPoints(xStart, xEnd,expression));
		} catch (Exception e) {
			log.error("Error while processing request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
