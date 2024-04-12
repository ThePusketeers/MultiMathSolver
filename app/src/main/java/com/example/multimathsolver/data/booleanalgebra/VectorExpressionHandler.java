package com.example.multimathsolver.data.booleanalgebra;

import java.util.ArrayList;
import java.util.List;

public class VectorExpressionHandler implements ExpressionHandlerInterface {
    private final List<String> listOfParameters;
    private final String valueOfFunction;
    public VectorExpressionHandler(String expression) {
        int counterOfParameters = 0;
        valueOfFunction = expression.replace(" ", "");
        while (Math.pow(2, counterOfParameters) < valueOfFunction.length()) counterOfParameters++;
        listOfParameters = new ArrayList<>();
        for (int i = 1; i <= counterOfParameters; ++i) listOfParameters.add("X" + i);
    }

    @Override
    public List<String> getExpressionParameters() {
        return listOfParameters;
    }

    @Override
    public boolean fillTableWithValueOfFunction(int[][] table) {
        if (valueOfFunction.length() != table.length) return true;
        for (int i = 0; i < table.length; ++i) table[i][listOfParameters.size()] = valueOfFunction.charAt(i) == '0' ? 0 : 1;
        return false;
    }
}
