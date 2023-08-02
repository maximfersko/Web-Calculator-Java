package com.edu.fersko.smartcalc.models;

import static com.edu.fersko.smartcalc.models.NativeLibraryLoader.getLibraryPath;

public class CreditModelJNIWrapper {

    static {
        try {
            String libraryPath = getLibraryPath(false);
            System.load(libraryPath);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public native void annuity(double summa, double period, double rate);
    public native void deffirentated(double summa, double rate, double period);
    public native CreditData getResult();

}
