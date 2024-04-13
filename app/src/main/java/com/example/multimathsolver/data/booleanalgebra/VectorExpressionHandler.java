package com.example.multimathsolver.data.booleanalgebra;

import com.example.multimathsolver.domain.IncorrectFunctionInput;

import java.util.ArrayList;
import java.util.List;

public class VectorExpressionHandler implements ExpressionHandler {
    private final List<String> expressionParameters;
    private final String valueOfFunction;
    private final int[][] table;
    public VectorExpressionHandler(String expression) {
        int counterOfParameters = 0;
        valueOfFunction = expression;
        while (Math.pow(2, counterOfParameters) < valueOfFunction.length()) counterOfParameters++;
        expressionParameters = new ArrayList<>();
        for (int i = 1; i <= counterOfParameters; ++i) expressionParameters.add("X" + i);
        table = fillTheTable();
    }

    @Override
    public List<String> getExpressionParameters() {
        return expressionParameters;
    }

    private int[][] fillTheTable() {
        int[][] table = new int[(int) Math.pow(2, expressionParameters.size())][expressionParameters.size() + 1];
        int x = (int) Math.pow(2, expressionParameters.size());
        for(int col = 0; x != 1; ++col) {
            x /= 2;
            boolean res = true;
            for(int i = 0; i < table.length; ++i) {
                if (i % x == 0) {
                    res = !res;
                }
                table[i][col] = res ? 1 : 0;
            }
        }
        return table;
    }

    @Override
    public int[][] getTableWithValueOfFunction() throws IncorrectFunctionInput {
        if (valueOfFunction.length() != table.length) throw new IncorrectFunctionInput("Неверный ввод вектора значений функции");
        for (int i = 0; i < table.length; ++i) table[i][expressionParameters.size()] = valueOfFunction.charAt(i) == '0' ? 0 : 1;
        return table;
    }
}
