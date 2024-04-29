package com.example.multimathsolver.domain;

public class GetSLAYSolutionUseCase {
    private final Repository repository;

    public GetSLAYSolutionUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getSLAYSolutionUseCase(SLAY coeffSLAY, SLAY additionalSLAY){
        return repository.getSolutionOfSLAY(coeffSLAY, additionalSLAY);
    }
}
