package com.example.multimathsolver.data.booleanalgebra;

import com.example.multimathsolver.domain.IncorrectFunctionInput;

import java.util.List;

public interface ExpressionHandler {
    List<String> getExpressionParameters();
    int[][] getTableWithValueOfFunction() throws IncorrectFunctionInput;
}
