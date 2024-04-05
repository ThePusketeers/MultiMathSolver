package com.example.multimathsolver.booleanalgebra;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MinimalDNFTest {

    BooleanFunction function;
    String expectedMinimalDNF;

    public MinimalDNFTest(String expression, String expectedMinimalDNF) {
        this.function = new BooleanFunction(expression);
        this.expectedMinimalDNF = expectedMinimalDNF;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(¬X1∧¬X3)∨(¬X3∧¬X2)∨(¬X1∧X2)∨(X2∧X3)∨(X1∧¬X2)∨(X1∧X3)", "(¬X1 ∧ ¬X3) ∨ (X2 ∧ X3) ∨ (X1 ∧ ¬X2)\n" +
                        "(X1 ∧ X3) ∨ (¬X1 ∧ X2) ∨ (¬X2 ∧ ¬X3)"},
                {"(x1∨x2∨x3) ∧ (¬x1∨¬x2∨x3) ∧ (¬x1∨¬x2∨¬x3)", "(¬x1 ∧ x2) ∨ (¬x2 ∧ x3) ∨ (x1 ∧ ¬x2)\n" +
                        "(¬x1 ∧ x3) ∨ (¬x1 ∧ x2) ∨ (x1 ∧ ¬x2)"},
                {"(x1∨x2∨¬x3∨¬x4) ∧ (x1∨¬x2∨x3∨¬x4) ∧ (x1∨¬x2∨¬x3∨x4) ∧ (x1∨¬x2∨¬x3∨¬x4) ∧ (¬x1∨x2∨x3∨x4) ∧ (¬x1∨x2∨¬x3∨¬x4) ∧ (¬x1∨¬x2∨x3∨¬x4) ∧ (¬x1∨¬x2∨¬x3∨x4) ∧ (¬x1∨¬x2∨¬x3∨¬x4)",
                        "(¬x2 ∧ x3 ∧ ¬x4) ∨ (¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4) ∨ (¬x1 ∧ ¬x2 ∧ ¬x3)\n" +
                                "(¬x2 ∧ x3 ∧ ¬x4) ∨ (¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4) ∨ (¬x1 ∧ ¬x3 ∧ ¬x4)\n" +
                                "(¬x2 ∧ x3 ∧ ¬x4) ∨ (¬x1 ∧ ¬x2 ∧ ¬x4) ∨ (¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4)"},
                {"(x1∨x2∨¬x3∨¬x4) ∧ (x1∨¬x2∨x3∨x4) ∧ (x1∨¬x2∨¬x3∨¬x4) ∧ (¬x1∨x2∨x3∨x4) ∧ (¬x1∨x2∨x3∨¬x4) ∧ (¬x1∨x2∨¬x3∨x4) ∧ (¬x1∨¬x2∨x3∨x4) ∧ (¬x1∨¬x2∨¬x3∨x4)",
                        "(¬x1 ∧ x3 ∧ ¬x4) ∨ (x1 ∧ x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ x4) ∨ (¬x1 ∧ ¬x2 ∧ ¬x3)"}
        });
    }

    @Test
    public void testMinimalDNF() {
        Assert.assertEquals(expectedMinimalDNF, function.getMinimalDNF());
    }
}