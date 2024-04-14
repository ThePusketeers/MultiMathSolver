package com.example.multimathsolver.domain;

public class GetAddOrMinusUseCase {
    private final Repository repository;

    public GetAddOrMinusUseCase(Repository repository) {
        this.repository = repository;
    }

    public MatrixOperations getAddOrMinus(MatrixOperations firstMatrix, MatrixOperations secondMatrix, char operation) throws IncorrectMatrixSize {
        return repository.add_or_minus(firstMatrix, secondMatrix, operation);
    }
}
