package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.NativeCalculationException;
import com.edu.fersko.smartcalc.models.Point;
import com.edu.fersko.smartcalc.models.RPN;
import com.edu.fersko.smartcalc.models.ResultResponse;
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
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private final RPN rpn;

    private final CalculatorUtilitiesService service;


    @Autowired
    public MainController(RPN rpn, CalculatorUtilitiesService service) {
        this.rpn = rpn;
        this.service = service;
        this.service.loadHistory();
    }




    @GetMapping("/")
    public String showCalculator() {
        return "calculator";
    }

    @GetMapping("/history")
    @ResponseBody
    public List<String> getHistory() {
        return service.getHistory();
    }

    @PostMapping("/calculate")
    public ResponseEntity<ResultResponse> calculate(@RequestBody Map<String, String> requestBody) {
        String expression = requestBody.get("expression");
        double result;

        try {
            result = rpn.getResult(expression, 0);
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
    public ResponseEntity<String> clearHistory() {
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
    public ResponseEntity<List<Point>> calculateGraph(@RequestBody @NotNull Map<String, String> requestBody) {
        String expression = requestBody.get("expression");
        double xStart = Double.parseDouble(requestBody.get("xStart"));
        double xEnd = Double.parseDouble(requestBody.get("xEnd"));
        double step = Double.parseDouble(requestBody.get("step"));


        List<Point> points = rpn.graphBuilder(null, expression);

        return ResponseEntity.ok(points);
    }


}
