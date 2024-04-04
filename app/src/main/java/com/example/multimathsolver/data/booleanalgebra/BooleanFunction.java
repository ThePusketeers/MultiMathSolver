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

    public String perfectDNF() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < table.length ; ++i) {
            if (table[i][table[0].length-1] == 1) {
                output.append("(");
                for (int j = 0; j < table[0].length - 1; ++j) {
                    if (table[i][j] == 0) {
                        output.append(UnaryOperation.NEGATION.charOfOperation);
                    }
                    output.append(parameters.get(j))
                            .append(" ")
                            .append(BinaryOperation.CONJUNCTION.charOfOperation)
                            .append(" ");
                }
                if (output.length() >= 3) {
                    output = new StringBuilder(output.substring(0, output.length() - 3));
                }
                output.append(") ").append(BinaryOperation.DISJUNCTION.charOfOperation).append(" ");
            }
        }
        if (output.length() >= 3) {
            output = new StringBuilder(output.substring(0, output.length() - 3));
        }
        return output.toString();
    }

    public String perfectCNF() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < table.length ; ++i) {
            if (table[i][table[0].length-1] == 0) {
                output.append("(");
                for (int j = 0; j < table[0].length - 1; ++j) {
                    if (table[i][j] == 1) {
                        output.append(UnaryOperation.NEGATION.charOfOperation);
                    }
                    output.append(parameters.get(j))
                            .append(" ")
                            .append(BinaryOperation.DISJUNCTION.charOfOperation)
                            .append(" ");
                }
                if (output.length() >= 3) {
                    output = new StringBuilder(output.substring(0, output.length() - 3));
                }
                output.append(") ").append(BinaryOperation.CONJUNCTION.charOfOperation).append(" ");
            }
        }
        if (output.length() >= 3) {
            output = new StringBuilder(output.substring(0, output.length() - 3));
        }
        return output.toString();
    }
}
