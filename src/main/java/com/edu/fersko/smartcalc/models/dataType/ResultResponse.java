package com.edu.fersko.smartcalc.models.dataType;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultResponse {
    private double result;
    private String errorMessage;

    public ResultResponse(String errorDuringCalculation) {

    }
}