package com.edu.fersko.smartcalc.service;

import com.edu.fersko.smartcalc.models.type.GraphData;

public interface GraphCalculatorService {
	GraphData calculateGraphPoints(double start, double end, String expression);
}
