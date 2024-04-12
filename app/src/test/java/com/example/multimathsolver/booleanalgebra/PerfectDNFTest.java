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
public class PerfectDNFTest {

    BooleanFunction function;
    String expectedPerfectDNF;

    public PerfectDNFTest(String expression, String expectedPerfectDNF) throws IncorrectFunctionInput {
        this.function = new BooleanFunction(new StringExpressionHandler(expression));
        this.expectedPerfectDNF = expectedPerfectDNF;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(X2→X3)⊕X1", "(¬X1 ∧ ¬X2 ∧ ¬X3) ∨ (¬X1 ∧ ¬X2 ∧ X3) ∨ (¬X1 ∧ X2 ∧ X3) ∨ (X1 ∧ X2 ∧ ¬X3)"},
                {"(a)→(¬b∧c)⊕a", "(¬a ∧ ¬b ∧ ¬c) ∨ (¬a ∧ ¬b ∧ c) ∨ (¬a ∧ b ∧ ¬c) ∨ (¬a ∧ b ∧ c) ∨ (a ∧ ¬b ∧ ¬c) ∨ (a ∧ b ∧ ¬c) ∨ (a ∧ b ∧ c)"},
                {"¬((X1⇔X3)→(X1∧X2∨X3))", "(¬X1 ∧ ¬X2 ∧ ¬X3) ∨ (¬X1 ∧ X2 ∧ ¬X3)"},
                {"(a⊕c)→(c∧b⇔b∨a)", "(¬a ∧ ¬b ∧ ¬c) ∨ (¬a ∧ ¬b ∧ c) ∨ (¬a ∧ b ∧ ¬c) ∨ (¬a ∧ b ∧ c) ∨ (a ∧ ¬b ∧ c) ∨ (a ∧ b ∧ c)"}
        });
    }

    @Test
    public void testPerfectDNF() {
        Assert.assertEquals(expectedPerfectDNF, function.getPerfectDNF());
    }
}
