package com.edu.fersko.smartcalc.models;

public class CreditData {
    private double totalPayment;
    private double monthlyPayment;
    private double overPayment;
    private double minMonthlyPayment;
    private double maxMonthlyPayment;
    private double payments;


    public CreditData(double totalPayment, double monthlyPayment, double overPayment, double minMonthlyPayment, double maxMonthlyPayment, double payments) {
        this.totalPayment = totalPayment;
        this.monthlyPayment = monthlyPayment;
        this.overPayment = overPayment;
        this.minMonthlyPayment = minMonthlyPayment;
        this.maxMonthlyPayment = maxMonthlyPayment;
        this.payments = payments;
    }

    public CreditData() {

    }

    public double getOverPayment() {
        return overPayment;
    }

    public double getMinMonthlyPayment() {
        return minMonthlyPayment;
    }

    public double getMaxMonthlyPayment() {
        return maxMonthlyPayment;
    }

    public double getPayments() {
        return payments;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getTotalPayment() {
        return totalPayment;
    }
}
