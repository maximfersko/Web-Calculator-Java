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
    private static final String HISTORY_FILE_PATH = System.getProperty("user.dir") + File.separator + "data" + File.separator + "history.txt";

    private final List<String> history = new ArrayList<>();

    public void writeHistoryToFile() {
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

    public void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getHistory() {
        return history;
    }

    public static String getHistoryFilePath() {
        return HISTORY_FILE_PATH;
    }
}
