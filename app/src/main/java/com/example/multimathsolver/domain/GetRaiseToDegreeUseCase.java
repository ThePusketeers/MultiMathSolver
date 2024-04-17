package com.example.multimathsolver.domain;

public class GetRaiseToDegreeUseCase {
    private final Repository repository;

    public GetRaiseToDegreeUseCase(Repository repository) {
        this.repository = repository;
    }

    public MatrixOperations getRaiseToDegree(MatrixOperations firstMatrix, int degree) throws IncorrectMatrixSize {
        return repository.raiseToDegree(firstMatrix, degree);
    }
}
