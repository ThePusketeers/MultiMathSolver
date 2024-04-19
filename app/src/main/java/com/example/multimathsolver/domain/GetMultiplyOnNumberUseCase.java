package com.example.multimathsolver.domain;

public class GetMultiplyOnNumberUseCase {
    private final Repository repository;

    public GetMultiplyOnNumberUseCase(Repository repository) {
        this.repository = repository;
    }

    public MatrixOperations getMultiplyOnNumber(MatrixOperations firstMatrix, double number) {
        return repository.multiplyOnNumber(firstMatrix, number);
    }
}
