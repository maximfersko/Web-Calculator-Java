package com.edu.fersko.smartcalc.models;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
public class RPN {
    static {
        try {
            System.load("C:\\Users\\mrmak\\Desktop\\Web-Calculator-Java\\src\\main\\java\\com\\edu\\fersko\\smartcalc\\models\\core.dll");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public native double getResult(String str, double x);
    public native void  clean();
    public native List<Point> graphBuilder(double[] data, String expression);
}
