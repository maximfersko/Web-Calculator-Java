package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.SmartCalcJNIWrapper;
import com.edu.fersko.smartcalc.models.dataType.GraphData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class GraphController {
	private final SmartCalcJNIWrapper graphCore;

	@Autowired
	public GraphController(SmartCalcJNIWrapper graphCore) {
		this.graphCore = graphCore;
	}

	public ResponseEntity<GraphData> calculateGraph(
			@RequestParam String expression,
			@RequestParam double xStart,
			@RequestParam double xEnd) {
		try {
			double[] data = { xStart, xEnd };

			GraphData graphData = graphCore.graphBuilder(data, expression);

			return ResponseEntity.ok(graphData);
		} catch (Exception e) {
			log.error("Error while processing request: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
