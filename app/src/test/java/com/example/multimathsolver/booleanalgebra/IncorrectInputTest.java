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
public class IncorrectInputTest {

    String expression;

    public IncorrectInputTest(String expression) {
        this.expression = expression;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(>)"},
                {"(X1∧X2))"},
                {"X1∧X2)(→X3"}
        });
    }

    @Test
    public void testMinimalDNF() {
        Assert.assertThrows(IncorrectFunctionInput.class, () -> new BooleanFunction(new StringExpressionHandler(expression)));
    }
}