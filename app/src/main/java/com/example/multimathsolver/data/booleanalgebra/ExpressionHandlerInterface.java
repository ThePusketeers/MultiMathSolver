package com.example.multimathsolver.data.booleanalgebra;

import java.util.List;

public interface ExpressionHandlerInterface {
    List<String> getExpressionParameters();
    boolean fillTableWithValueOfFunction(int[][] table);
}
