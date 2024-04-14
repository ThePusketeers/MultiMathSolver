package com.example.multimathsolver.domain;

public class GetMultiplicationUseCase {
    private final Repository repository;

    public GetMultiplicationUseCase(Repository repository) {
        this.repository = repository;
    }

    public MatrixOperations getMultiplication(MatrixOperations firstMatrix, MatrixOperations secondMatrix) throws IncorrectMatrixSize {
        return repository.multiplication(firstMatrix, secondMatrix);
    }
}
