package com.edu.fersko.smartcalc.service.impl;

import com.edu.fersko.smartcalc.exceptions.NativeCalculationException;
import com.edu.fersko.smartcalc.models.SmartCalcJNIWrapper;
import com.edu.fersko.smartcalc.models.type.ResultResponse;
import com.edu.fersko.smartcalc.service.HistoryService;
import com.edu.fersko.smartcalc.service.SmartCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SmartCalculatorServiceImpl implements SmartCalculatorService {
	private SmartCalcJNIWrapper smartCalcJNIWrapper;
	private HistoryService historyService;

	@Override
	public ResultResponse calculateExpression(String expression, double x) throws NativeCalculationException {
		double result = smartCalcJNIWrapper.getResult(expression, x);
		historyService.addItemToHistory(expression + " = " + result);
		historyService.writeHistoryToFile();
		ResultResponse resultResponse = new ResultResponse("Success");
		resultResponse.setResult(result);
		return resultResponse;
	}
}
