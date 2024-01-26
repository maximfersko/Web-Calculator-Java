package com.edu.fersko.smartcalc.service;

import com.edu.fersko.smartcalc.exceptions.NativeCalculationException;
import com.edu.fersko.smartcalc.models.type.ResultResponse;

public interface SmartCalculatorService {

	ResultResponse calculateExpression(String expression, double x) throws NativeCalculationException;
}
