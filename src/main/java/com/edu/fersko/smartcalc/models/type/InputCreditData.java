package com.edu.fersko.smartcalc.models.type;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InputCreditData {
	private double loanAmount;
	private int loanTerm;
	private double interestRate;
	private String creditType;
}
