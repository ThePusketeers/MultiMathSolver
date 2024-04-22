package com.example.multimathsolver.domain;

public class GetSearchDeterminantUseCase {
    private final Repository repository;

    public GetSearchDeterminantUseCase(Repository repository) {
        this.repository = repository;
    }

    public double getSearchDeterminant(MatrixOperations firstMatrix) throws IncorrectMatrixSize {
        return repository.searchDeterminant(firstMatrix);
    }
}
