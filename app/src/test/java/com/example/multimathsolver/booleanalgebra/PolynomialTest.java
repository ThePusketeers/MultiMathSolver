package com.example.multimathsolver.booleanalgebra;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.data.booleanalgebra.IncorrectFunctionInput;
import com.example.multimathsolver.data.booleanalgebra.StringExpressionHandler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PolynomialTest {

    BooleanFunction function;
    String expectedPolynomial;

    public PolynomialTest(String expression, String expectedPolynomial) throws IncorrectFunctionInput {
        this.function = new BooleanFunction(new StringExpressionHandler(expression));
        this.expectedPolynomial = expectedPolynomial;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(x1∨x2∨x3∨¬x4) ∧ (x1∨x2∨¬x3∨x4) ∧ (x1∨x2∨¬x3∨¬x4) ∧ (x1∨¬x2∨¬x3∨¬x4) ∧ (¬x1∨x2∨x3∨x4) ∧ (¬x1∨x2∨x3∨¬x4) ∧ (¬x1∨¬x2∨¬x3∨x4) ∧ (¬x1∨¬x2∨¬x3∨¬x4)",
                        "x1x2x3 ⊕ x1x2x4 ⊕ x1x2 ⊕ x1x3x4 ⊕ x1x4 ⊕ x1 ⊕ x2x3 ⊕ x2x4 ⊕ x3x4 ⊕ x3 ⊕ x4 ⊕ 1"},
                {"(x1∨x2) ∧ (x1∨¬x2) ∧ (¬x1∨x2) ∧ (¬x1∨¬x2)", ""},
                {"¬x1∧¬x2 ∨ ¬x1∧x2 ∨ x1∧¬x2 ∨ x1∧x2", "1"},
                {"(x1∨x2∨x3∨x4) ∧ (x1∨x2∨x3∨¬x4) ∧ (x1∨¬x2∨¬x3∨x4) ∧ (¬x1∨x2∨x3∨x4) ∧ (¬x1∨x2∨x3∨¬x4) ∧ (¬x1∨x2∨¬x3∨x4) ∧ (¬x1∨¬x2∨¬x3∨x4) ∧ (¬x1∨¬x2∨¬x3∨¬x4)",
                        "x1x2x3 ⊕ x1x3x4 ⊕ x1x3 ⊕ x2x3x4 ⊕ x2 ⊕ x3"}
        });
    }

    @Test
    public void testPolynomial() {
        Assert.assertEquals(expectedPolynomial, function.getPolynomial());
    }
}