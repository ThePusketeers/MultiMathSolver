package com.example.multimathsolver.domain;

public interface Repository {

    MatrixOperations add_or_minus(MatrixOperations main_matrix, MatrixOperations other_matrix, char operation) throws IncorrectMatrixSize;
    MatrixOperations multiplyOnNumber(MatrixOperations main_matrix, double number);
    MatrixOperations multiplication(MatrixOperations main_matrix, MatrixOperations other_matrix) throws IncorrectMatrixSize;
    MatrixOperations raise_to_degree(MatrixOperations main_matrix, int degree) throws IncorrectMatrixSize;
//    MatrixOperations gauss(MatrixOperations main_matrix);
    int search_rank(MatrixOperations main_matrix);
    double search_determinant(MatrixOperations main_matrix) throws IncorrectMatrixSize;
}
