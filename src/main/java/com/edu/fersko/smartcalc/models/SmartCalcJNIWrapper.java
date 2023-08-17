package com.edu.fersko.smartcalc.models;



import org.springframework.stereotype.Component;

import java.util.List;

import static com.edu.fersko.smartcalc.models.NativeLibraryLoader.getLibraryPath;

@Component
public class SmartCalcJNIWrapper {

    static {
        try {
            String libraryPath = getLibraryPath(true);
            System.load(libraryPath);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public native double getResult(String str, double x) throws NativeCalculationException;
    public native void clean();
    public native GraphData graphBuilder(double[] data, String expression);
}