package com.edu.fersko.smartcalc.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@Slf4j
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
	        log.error(e.getMessage());
        }
    }

    public void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
	        log.error(e.getMessage());
        }
    }

    public static String getHistoryFilePath() {
        return HISTORY_FILE_PATH;
    }
}
