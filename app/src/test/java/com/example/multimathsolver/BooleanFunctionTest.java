package com.example.multimathsolver;

import com.example.multimathsolver.data.BooleanFunction;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BooleanFunctionTest {
    private final BooleanFunction function;
    private final int[] expectedTableResult;

    public BooleanFunctionTest(String expression, int[] list) {
        this.function = new BooleanFunction(expression);
        this.expectedTableResult = list;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"(X1⊕X2)→X3∧X2", new int[]{1, 1, 0, 1, 0, 0, 1, 1}},
                {"¬(X1→X3)⊕X2", new int[]{0, 0, 1, 1, 1, 0, 0, 1}},
                {"(X1∧X3)⊕(¬X2)", new int[]{1, 1, 0, 0, 1, 0, 0, 1}},
                {"(X1)∨X2∧X3", new int[]{0, 0, 0, 1, 1, 1, 1, 1}},
                {"¬X2→X3∧X4", new int[]{0, 0, 0, 1, 1, 1, 1, 1}},
                {"(X1⊕X2)→X3∧X4∨X1", new int[]{1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1}},
                {"(((X1⊕X2)→X3)⇔X2)", new int[]{0, 0, 0, 1, 1, 0, 1, 1}},
                {"(((X1⊕X2)→(X3))⇔X2)", new int[]{0, 0, 0, 1, 1, 0, 1, 1}},
                {"(X2→X3)∧¬(X1⇔X3)", new int[]{0, 1, 0, 1, 1, 0, 0, 0}},
                {"(X1⊕X2)∧(¬X1⊕(¬X3))", new int[]{0, 0, 0, 1, 1, 0, 0, 0}}
        });
    }


    @Test
    public void testTableResult() {
        for (int i = 0; i < expectedTableResult.length; ++i) {
            if (function.table[i][function.table[0].length-1] != expectedTableResult[i]) Assert.fail();
        }
    }
}
