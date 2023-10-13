package com.edu.fersko.smartcalc.model;

import com.edu.fersko.smartcalc.models.CreditModelJNIWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditModelJNIWrapperTest {
    private static final double DELTA = 0.1;
    private static CreditModelJNIWrapper creditCalculator;

    @BeforeAll
    public static void setup() {
        creditCalculator = new CreditModelJNIWrapper();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/creditAnnuity.csv")
    public void testCreditAnnuity(double amount, int term, double rate,
                                  double expectedMonthlyPayment,
                                  double expectedOverPayment,
                                  double expectedTotalPayment) {
        creditCalculator.annuity(amount, term, rate);
        assertEquals(expectedMonthlyPayment, creditCalculator.getResult().getMonthlyPayment(), DELTA);
        assertEquals(expectedOverPayment, creditCalculator.getResult().getOverPayment(), DELTA);
        assertEquals(expectedTotalPayment, creditCalculator.getResult().getTotalPayment(), DELTA);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/creditDiffenrented.csv")
    public void testCreditDiffenrennted(double amount, int term, double rate,
                                        double minExpectedMonthlyPayment, double maxExpectedMonthlyPayment,
                                        double expectedOverPayment,
                                        double expectedTotalPayment) {
        creditCalculator.deffirentated(amount, term, rate);
        assertEquals(minExpectedMonthlyPayment, Math.round(creditCalculator.getResult().getMinMonthlyPayment()), DELTA);
        assertEquals(maxExpectedMonthlyPayment, Math.round(creditCalculator.getResult().getMaxMonthlyPayment()), DELTA);
        assertEquals(expectedOverPayment, Math.round(creditCalculator.getResult().getOverPayment()), DELTA);
        assertEquals(expectedTotalPayment, Math.round(creditCalculator.getResult().getTotalPayment()), DELTA);
    }

}