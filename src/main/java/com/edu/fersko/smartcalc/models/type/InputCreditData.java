package com.edu.fersko.smartcalc.models.type;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InputCreditData {
	double loanAmount;
	int loanTerm;
	double interestRate;
	String creditType;
}
