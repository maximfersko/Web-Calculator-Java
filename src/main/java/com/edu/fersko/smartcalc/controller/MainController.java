package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.Point;
import com.edu.fersko.smartcalc.models.RPN;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
    private void loadHistoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CalculatorUtilitiesService.getHistoryFilePath()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                service.getHistory().add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/calculate")
    @ResponseBody
    public ResponseEntity<Map<String, Double>> calculate(@RequestBody @NotNull Map<String, String> requestBody) {
        String expression = requestBody.get("expression");
        double result = rpn.getResult(expression, 0);

        Map<String, Double> resultMap = new HashMap<>();
        resultMap.put("result", result);

        String calculation = expression + " = " + result;
        service.getHistory().add(calculation);

        service.writeHistoryToFile();

        return ResponseEntity.ok(resultMap);
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

        // Use the graphBuilder method from the RPN class
        List<Point> points = rpn.graphBuilder(null, expression);

        return ResponseEntity.ok(points);
    }


}
