package com.edu.fersko.smartcalc.models;

public class ResultResponse {
    private double result;
    private String errorMessage;

    public ResultResponse(String errorDuringCalculation) {

    }

    public void setResult(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
