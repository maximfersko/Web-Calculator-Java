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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final RPN rpn;
    private final List<String> history = new ArrayList<>(); 

    @Autowired
    public MainController(RPN rpn) {
        this.rpn = rpn;
        loadHistory();
    }

    private static final String HISTORY_FILE_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator + "history.txt";


    private void writeHistoryToFile() {
        try {
            File dataFolder = new File("data");
            if (!dataFolder.exists()) {
                dataFolder.mkdir();
            }

            File historyFile = new File(HISTORY_FILE_PATH);
            if (!historyFile.exists()) {
                historyFile.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile))) {
                for (String calculation : history) {
                    writer.write(calculation);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/")
    public String showCalculator() {
        return "calculator";
    }

    @GetMapping("/history")
    @ResponseBody
    public List<String> getHistory() {
        return history;
    }
    private void loadHistoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
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
        history.add(calculation);

        writeHistoryToFile();

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/clearHistory")
    @ResponseBody
    public ResponseEntity<String> clearHistory() {
        history.clear();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE_PATH))) {
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

     private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
