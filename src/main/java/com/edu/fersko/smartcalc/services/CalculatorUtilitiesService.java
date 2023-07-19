package com.edu.fersko.smartcalc.services;



import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class CalculatorUtilitiesService {
    private final String HISTORY_PATH = "../../../../../history.txt";

    public List<String> getHistory() {
        List<String> result = new ArrayList<>();
        try(FileReader reader = new FileReader(HISTORY_PATH)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
        return result;
    }

}
