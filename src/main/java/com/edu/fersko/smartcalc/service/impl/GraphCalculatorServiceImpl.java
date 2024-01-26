package com.edu.fersko.smartcalc.service.impl;

import com.edu.fersko.smartcalc.models.SmartCalcJNIWrapper;
import com.edu.fersko.smartcalc.models.type.GraphData;
import com.edu.fersko.smartcalc.service.GraphCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GraphCalculatorServiceImpl implements GraphCalculatorService {
	private final SmartCalcJNIWrapper smartCalcJNIWrapper;

	@Override
	public GraphData calculateGraphPoints(double start, double end, String expression) {

		return smartCalcJNIWrapper.graphBuilder(new double[]{ start, end }, expression);
	}
}
