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

    MatrixOperations addOrMinus(MatrixOperations mainMatrix, MatrixOperations otherMatrix, char operation) throws IncorrectMatrixSize;

    MatrixOperations multiplyOnNumber(MatrixOperations mainMatrix, double number);

    MatrixOperations multiplication(MatrixOperations mainMatrix, MatrixOperations otherMatrix) throws IncorrectMatrixSize;

    MatrixOperations raiseToDegree(MatrixOperations mainMatrix, int degree) throws IncorrectMatrixSize;

    //    MatrixOperations gauss(MatrixOperations main_matrix);

    int searchRank(MatrixOperations mainMatrix);

    double searchDeterminant(MatrixOperations mainMatrix) throws IncorrectMatrixSize;
  
    String getSolutionOfSLAY(SLAY coeffSLAY, SLAY additionalSLAY);
}
