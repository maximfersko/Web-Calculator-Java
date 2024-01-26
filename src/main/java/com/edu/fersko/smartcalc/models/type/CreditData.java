package com.edu.fersko.smartcalc.models.type;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreditData {
	private double totalPayment;
	private double monthlyPayment;
	private double overPayment;
	private double minMonthlyPayment;
	private double maxMonthlyPayment;
	private double payments;
}