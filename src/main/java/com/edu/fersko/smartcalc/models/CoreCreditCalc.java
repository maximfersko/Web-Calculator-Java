package com.edu.fersko.smartcalc.models;

public class CoreCreditCalc {
    public native void annuity(double summa, double period, double rate);
    public native void differentiated(double summa, double rate, double period);
    public native CreditData getResult();
}
