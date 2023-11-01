package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        } catch (NativeCalculationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultResponse("Error during calculation"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultResponse("Internal server error"));
        } catch (Throwable t) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultResponse("Unexpected error"));
        }
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

    public ResponseEntity<GraphData> calculateGraph(
            @RequestParam String expression,
            @RequestParam double xStart,
            @RequestParam double xEnd)  {
        try {

            System.out.println("Received expression: " + expression);
            System.out.println("Received xStart: " + xStart);
            System.out.println("Received xEnd: " + xEnd);

            double[] data = {xStart, xEnd};

            GraphData graphData = coreSmartCalc.graphBuilder(data, expression);

            return ResponseEntity.ok(graphData);
        } catch (Exception e) {
            System.err.println("Error while processing request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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