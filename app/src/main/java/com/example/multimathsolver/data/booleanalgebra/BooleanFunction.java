package com.example.multimathsolver.data.booleanalgebra;

import java.util.List;

public class BooleanFunction {
    List<String> parameters;
    public int[][] table;
    public BooleanFunction(String expression) {
        ExpressionHandler expressionHandler = new ExpressionHandler(expression);
        this.parameters = expressionHandler.getExpressionParameters();
        this.table = fillTheTable();
        boolean isException = expressionHandler.fillTableWithValueOfFunction(this.table);
        if (isException) {
            System.out.println("Функция введена неверно");
        }
    }

    private int[][] fillTheTable() {
        int[][] table = new int[(int) Math.pow(2, parameters.size())][parameters.size() + 1];
        int x = (int) Math.pow(2, parameters.size());
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
}
