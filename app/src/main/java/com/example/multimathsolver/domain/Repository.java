package com.example.multimathsolver.domain;

import java.util.Map;

public interface Repository {
    BooleanFunction getBooleanFunction(String expression) throws IncorrectFunctionInput;

    String getPerfectDNF(BooleanFunction function);

    String getPerfectCNF(BooleanFunction function);

    String getAbbreviatedDNF(BooleanFunction function);

    String getDeadLockedDNF(BooleanFunction function);

    String getMinimalDNF(BooleanFunction function);

    String getPolynomial(BooleanFunction function);

    Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function);

    MatrixOperations add_or_minus(MatrixOperations main_matrix, MatrixOperations other_matrix, char operation) throws IncorrectMatrixSize;

    MatrixOperations multiplyOnNumber(MatrixOperations main_matrix, double number);

    MatrixOperations multiplication(MatrixOperations main_matrix, MatrixOperations other_matrix) throws IncorrectMatrixSize;

    MatrixOperations raise_to_degree(MatrixOperations main_matrix, int degree) throws IncorrectMatrixSize;

    //    MatrixOperations gauss(MatrixOperations main_matrix);

    int search_rank(MatrixOperations main_matrix);

    double search_determinant(MatrixOperations main_matrix) throws IncorrectMatrixSize;
}
