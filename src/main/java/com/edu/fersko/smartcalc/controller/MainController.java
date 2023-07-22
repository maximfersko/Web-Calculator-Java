package com.edu.fersko.smartcalc.controller;

import com.edu.fersko.smartcalc.models.ReversePolishNotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String calculate(@RequestParam("expression") String expression,
                            @RequestParam(value = "x", required = false) Double x,
                            Model model) {
        double result;
        if (x != null) {
            result = reversePolishNotation.getResult(expression, x);
        } else {
            result = reversePolishNotation.getResult(expression);
        }
        model.addAttribute("result", result);
        return "calculator";
    }
}
