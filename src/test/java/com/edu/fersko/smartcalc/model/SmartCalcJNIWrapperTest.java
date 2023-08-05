package com.edu.fersko.smartcalc.model;


import com.edu.fersko.smartcalc.models.CreditModelJNIWrapper;
import com.edu.fersko.smartcalc.models.NativeCalculationException;
import com.edu.fersko.smartcalc.models.SmartCalcJNIWrapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmartCalcJNIWrapperTest {
    private static final SmartCalcJNIWrapper rpn = new SmartCalcJNIWrapper();
    private static final CreditModelJNIWrapper creditCalculator = new CreditModelJNIWrapper();

    @ParameterizedTest
    @ValueSource(strings = {"(15 / ( 7 - ( 1 + 1 ) ) * 3 - ( 2 + ( 1 + 1 ) ) * 15 / ( 7 - ( 200 + 1 ) ) * 3 - ( 2 + ( 1 + 1 ) ) * ( 15 / ( 7 - ( 1 + 1 ) ) * 3 - ( 2 + ( 1 + 1 ) ) +  15 / ( 7 - ( 1 + 1 ) ) * 3 - ( 2 + ( 1 + 1 ) ) ) * 100.072165)"})
    public void calcWithBrackets(String expression) throws NativeCalculationException {
        assertEquals(-3993. ,Math.round(rpn.getResult(expression, 0)), 0.01);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.0008 * 10 ^ 6"})
    public void simnpleCalc(String expression) throws NativeCalculationException {
        assertEquals(800. ,Math.round(rpn.getResult(expression, 0)), 0.01);
    }

    @ParameterizedTest
    @ValueSource(strings = {"cos( 90 / 3 - 2 ) - 3 * (3 * 3 - 10)"})
    public void simnpleCalcWithMathFunction(String expression) throws NativeCalculationException {
        assertEquals(2. ,Math.round(rpn.getResult(expression, 0)), 0.01);
    }

    @ParameterizedTest
    @ValueSource(strings = {"( 1 + 2 ) * 4 * cos( 2 * 7 - 2 ) + sin( 2 * 90 )"})
    public void simnpleCalcWithMathFunctionSecondSuite(String expression) throws NativeCalculationException {
        assertEquals(9. ,Math.round(rpn.getResult(expression, 0)), 0.01);
    }
}
