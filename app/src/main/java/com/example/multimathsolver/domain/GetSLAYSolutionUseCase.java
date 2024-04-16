package com.example.multimathsolver.domain;

public class GetSLAYSolutionUseCase {
    private final Repository repository;

    public GetSLAYSolutionUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getSLAYSolutionUseCase(SLAY matrix){
        return repository.getSolutionOfSLAY(matrix);
    }
}
