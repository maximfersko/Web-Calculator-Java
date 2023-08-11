package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.fersko.smartcalc.services.CalculatorUtilitiesService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final SmartCalcJNIWrapper coreSmartCalc;
    private final CalculatorUtilitiesService service;
    private final CreditModelJNIWrapper creditModelWrapper;

    @Autowired
    public MainController(SmartCalcJNIWrapper coreSmartCalc, CalculatorUtilitiesService service) {
        this.coreSmartCalc = coreSmartCalc;
        this.service = service;
        this.service.loadHistory();
        this.creditModelWrapper = new CreditModelJNIWrapper();
    }

    @GetMapping("/")
    public String showCalculator() {
        return "calculator";
    }

    @GetMapping("/history")
    @ResponseBody
    public List < String > getHistory() {
        return service.getHistory();
    }

    @PostMapping("/calculate")
    public ResponseEntity < ResultResponse > calculate(@RequestBody Map < String, String > requestBody) {
        String expression = requestBody.get("expression");
        double result;

        try {
            result = coreSmartCalc.getResult(expression, 0);
        } catch (NativeCalculationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultResponse("Error during calculation"));
        }

        String calculation = expression + " = " + result;
        service.getHistory().add(calculation);
        service.writeHistoryToFile();

        ResultResponse resultResponse = new ResultResponse("Error during calculation");
        resultResponse.setResult(result);

        return ResponseEntity.ok(resultResponse);
    }

    @PostMapping("/clearHistory")
    @ResponseBody
    public ResponseEntity < String > clearHistory() {
        service.getHistory().clear();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CalculatorUtilitiesService.getHistoryFilePath()))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("History cleared.");
    }

    @PostMapping("/calculateGraph")
    @ResponseBody
    public ResponseEntity < List < Point >> calculateGraph(@RequestBody @NotNull Map < String, String > requestBody) {
        String expression = requestBody.get("expression");
        double xStart = Double.parseDouble(requestBody.get("xStart"));
        double xEnd = Double.parseDouble(requestBody.get("xEnd"));
        double step = Double.parseDouble(requestBody.get("step"));

        List < Point > points = coreSmartCalc.graphBuilder(new double[]{xStart, xEnd, step}, expression);

        return ResponseEntity.ok(points);
    }

    @PostMapping("/calculateCredit")
    @ResponseBody
    public ResponseEntity < Map < String, Double >> calculateCredit(@RequestBody Map < String, String > requestBody) {
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

        Map < String, Double > responseData = new HashMap < > ();
        responseData.put("monthlyPayment", data.getMonthlyPayment());
        responseData.put("overPayment", data.getOverPayment());
        responseData.put("totalPayment", data.getTotalPayment());
        responseData.put("maxMonthlyPayment", data.getMaxMonthlyPayment());
        responseData.put("minMonthlyPayment", data.getMinMonthlyPayment());

        return ResponseEntity.ok(responseData);
    }

}