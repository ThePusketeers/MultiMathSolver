package com.example.multimathsolver.data.booleanalgebra;

public class HandlerSelector {

    public static ExpressionHandler getHandler(String expression) {
        expression = expression.replace(" ", "");
        for (int i = 0; i < expression.length(); ++i) {
            if (expression.charAt(i) != '0' && expression.charAt(i) != '1') return new StringExpressionHandler(expression);
        }
        return new VectorExpressionHandler(expression);
    }
}
