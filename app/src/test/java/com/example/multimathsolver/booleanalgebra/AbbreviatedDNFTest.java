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
public class AbbreviatedDNFTest {

    BooleanFunction function;
    String expectedAbbreviatedDNF;

    public AbbreviatedDNFTest(String expression, String expectedAbbreviatedDNF) throws IncorrectFunctionInput {
        this.function = new BooleanFunction(new StringExpressionHandler(expression));
        this.expectedAbbreviatedDNF = expectedAbbreviatedDNF;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"¬(X1⊕X2)→(¬X3∧X4)", "(¬X1 ∧ X2) ∨ (X1 ∧ ¬X2) ∨ (¬X3 ∧ X4)"},
                {"(X1⊕X2)↓(X3→(¬X4))", "(¬X1 ∧ ¬X2 ∧ X3 ∧ X4) ∨ (X1 ∧ X2 ∧ X3 ∧ X4)"},
                {"(¬X3∧X2)∨(¬X4↑X1)", "(X4) ∨ (¬X1) ∨ (X2 ∧ ¬X3)"},
                {"(a)∧¬b⊕c", "(a ∧ ¬b ∧ ¬c) ∨ (¬a ∧ c) ∨ (b ∧ c)"}
        });
    }

    @Test
    public void testAbbreviatedDNF() {
        Assert.assertEquals(expectedAbbreviatedDNF, function.getAbbreviatedDNF());
    }
}