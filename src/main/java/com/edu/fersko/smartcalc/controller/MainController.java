package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.ReversePolishNotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    private final ReversePolishNotation reversePolishNotation;

    @Autowired
    public MainController(ReversePolishNotation reversePolishNotation) {
        this.reversePolishNotation = reversePolishNotation;
    }

    @GetMapping("/")
    public String showCalculator() {
        return "calculator";
    }


    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Double>> calculate(@RequestBody Map<String, String> requestBody) {
        String expression = requestBody.get("expression");
        double result = reversePolishNotation.getResult(expression);

        Map<String, Double> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return ResponseEntity.ok(resultMap);
    }

}

