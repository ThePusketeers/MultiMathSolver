package com.example.multimathsolver.booleanalgebra;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.BooleanFunction;
import com.example.multimathsolver.domain.IncorrectFunctionInput;
import com.example.multimathsolver.domain.Repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class DeadLockedDNFTest {
    Repository repository = new RepositoryImpl();
    BooleanFunction function;
    String expectedDeadLockedDNF;

    public DeadLockedDNFTest(String expression, String expectedDeadLockedDNF) throws IncorrectFunctionInput {
        this.function = repository.getBooleanFunction(expression);
        this.expectedDeadLockedDNF = expectedDeadLockedDNF;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"¬(X1⊕X2)→(¬X3∧X4)", "(¬X1 ∧ X2) ∨ (X1 ∧ ¬X2) ∨ (¬X3 ∧ X4)"},
                {"(X1⊕X2)∨¬X3∧X1", "(X2 ∧ ¬X3) ∨ (¬X1 ∧ X2) ∨ (X1 ∧ ¬X2)\n" +
                        "(X1 ∧ ¬X3) ∨ (¬X1 ∧ X2) ∨ (X1 ∧ ¬X2)"},
                {"¬( x2 ∧ x4 ⊕ x2 ∧ x3 ⊕ x2 ∧ x3 ∧x4 ⊕ x1 ⊕ x1 ∧ x4 ⊕ x1 ∧ x3 ∧ x4 ⊕ x1 ∧ x2)",
                        "(¬x1 ∧ ¬x3 ∧ ¬x4) ∨ (x1 ∧ ¬x3 ∧ x4) ∨ (x1 ∧ x2 ∧ ¬x3) ∨ (¬x1 ∧ ¬x2)\n" +
                        "(¬x2 ∧ ¬x3 ∧ x4) ∨ (¬x1 ∧ ¬x3 ∧ ¬x4) ∨ (x1 ∧ x2 ∧ ¬x3) ∨ (¬x1 ∧ ¬x2)\n" +
                        "(¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4) ∨ (x1 ∧ x2 ∧ ¬x3) ∨ (¬x1 ∧ ¬x2)\n" +
                        "(x2 ∧ ¬x3 ∧ ¬x4) ∨ (x1 ∧ ¬x3 ∧ x4) ∨ (¬x1 ∧ ¬x2)"},
                {"(¬X1∧¬X2∧¬X3)∨(X2∧¬X3∧X4)∨(¬X1∧X3∧¬X4)∨(X1∧X3∧X4)",
                        "(¬X1 ∧ X3 ∧ ¬X4) ∨ (X1 ∧ X3 ∧ X4) ∨ (X1 ∧ X2 ∧ X4) ∨ (¬X1 ∧ ¬X2 ∧ ¬X3) ∨ (¬X1 ∧ ¬X3 ∧ X4)\n" +
                                "(¬X1 ∧ X3 ∧ ¬X4) ∨ (X1 ∧ X3 ∧ X4) ∨ (¬X1 ∧ ¬X2 ∧ ¬X4) ∨ (X1 ∧ X2 ∧ X4) ∨ (¬X1 ∧ ¬X3 ∧ X4)\n" +
                                "(¬X1 ∧ X3 ∧ ¬X4) ∨ (X1 ∧ X3 ∧ X4) ∨ (X2 ∧ ¬X3 ∧ X4) ∨ (¬X1 ∧ ¬X2 ∧ ¬X4) ∨ (¬X1 ∧ ¬X3 ∧ X4)\n" +
                                "(¬X1 ∧ X3 ∧ ¬X4) ∨ (X1 ∧ X3 ∧ X4) ∨ (X2 ∧ ¬X3 ∧ X4) ∨ (¬X1 ∧ ¬X2 ∧ ¬X3)"},
                {"(x1∨x2∨¬x3∨¬x4) ∧ (x1∨¬x2∨x3∨¬x4) ∧ (x1∨¬x2∨¬x3∨x4) ∧ (x1∨¬x2∨¬x3∨¬x4) ∧ (¬x1∨x2∨x3∨x4) ∧ (¬x1∨x2∨¬x3∨¬x4) ∧ (¬x1∨¬x2∨x3∨¬x4) ∧ (¬x1∨¬x2∨¬x3∨x4) ∧ (¬x1∨¬x2∨¬x3∨¬x4)",
                        "(¬x2 ∧ x3 ∧ ¬x4) ∨ (¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4) ∨ (¬x1 ∧ ¬x2 ∧ ¬x3)\n" +
                                "(¬x2 ∧ x3 ∧ ¬x4) ∨ (¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4) ∨ (¬x1 ∧ ¬x3 ∧ ¬x4)\n" +
                                "(¬x2 ∧ x3 ∧ ¬x4) ∨ (¬x1 ∧ ¬x2 ∧ ¬x4) ∨ (¬x2 ∧ ¬x3 ∧ x4) ∨ (x2 ∧ ¬x3 ∧ ¬x4)"},
        });
    }

    @Test
    public void testDeadLockedDNF() {
        Assert.assertEquals(expectedDeadLockedDNF, repository.getDeadLockedDNF(function));
    }
}