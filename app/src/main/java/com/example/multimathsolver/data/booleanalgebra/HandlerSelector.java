package com.example.multimathsolver.data.booleanalgebra;

import com.example.multimathsolver.domain.IncorrectFunctionInput;

public class HandlerSelector {

    public static ExpressionHandler getHandler(String expression) throws IncorrectFunctionInput {
        try {
            expression = expression.replace(" ", "");
            for (int i = 0; i < expression.length(); ++i) {
                if (expression.charAt(i) != '0' && expression.charAt(i) != '1')
                    return new StringExpressionHandler(expression);
            }
            return new VectorExpressionHandler(expression);
        } catch (Exception e) {
            throw new IncorrectFunctionInput("Неверный ввод функции");
        }
    }
}
