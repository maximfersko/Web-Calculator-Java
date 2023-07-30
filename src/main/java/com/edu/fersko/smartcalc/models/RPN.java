package com.edu.fersko.smartcalc.models;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static com.edu.fersko.smartcalc.models.NativeLibraryLoader.getLibraryPath;

@Component
public class RPN {
    static {
        try {
            String libraryPath = getLibraryPath();
            System.load(libraryPath);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public native double getResult(String str, double x);
    public native void  clean();
    public native List<Point> graphBuilder(double[] data, String expression);
}
