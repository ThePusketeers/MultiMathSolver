package com.example.multimathsolver.booleanalgebra;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.data.booleanalgebra.IncorrectFunctionInput;
import com.example.multimathsolver.data.booleanalgebra.PostClass;
import com.example.multimathsolver.data.booleanalgebra.VectorExpressionHandler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class BelongingToPostClassesTest {

    BooleanFunction function;
    Map<PostClass, Boolean> expectedMap;

    public BelongingToPostClassesTest(String expression, boolean[] map) throws IncorrectFunctionInput {
        this.function = new BooleanFunction(new VectorExpressionHandler(expression));
        Map<PostClass, Boolean> expectedMap = new HashMap<>();
        for (int i = 0; i < PostClass.values().length; ++i) {
            expectedMap.put(PostClass.values()[i], map[i]);
        }
        this.expectedMap = expectedMap;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1110", new boolean[]{false, false, false, false, false}},
                {"0011", new boolean[]{true, true, true, true, true}},
                {"1010 0101 1110 0111", new boolean[]{false, true, false, false, false}},
                {"0011 1111 0000 0011",  new boolean[]{true, true, false, false, true}}
        });
    }

    @Test
    public void testBelongingToPostClassesF() {
        Assert.assertEquals(expectedMap, function.getBelongingToPostClasses());
    }
}