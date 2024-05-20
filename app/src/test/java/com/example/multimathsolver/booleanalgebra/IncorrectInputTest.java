package com.example.multimathsolver.booleanalgebra;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.IncorrectFunctionInput;
import com.example.multimathsolver.domain.Repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class IncorrectInputTest {
    Repository repository = new RepositoryImpl();
    String expression;

    public IncorrectInputTest(String expression) {
        this.expression = expression;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"(>)"},
                {"(X1∧X2))"},
                {"X1∧X2)(→X3"},
                {")abab( "},
                {"X1⊕"}
        });
    }

    @Test
    public void testIncorrectInput() {
        Assert.assertThrows(IncorrectFunctionInput.class, () -> repository.getBooleanFunction(expression));
    }
}