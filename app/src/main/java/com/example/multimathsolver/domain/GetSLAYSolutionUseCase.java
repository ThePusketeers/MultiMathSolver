package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.slay.SLAY;

public class GetSLAYSolutionUseCase {
    private final Repository repository;

    public GetSLAYSolutionUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getSLAYSolutionUseCase(SLAY matrix){
        return repository.solutionOfSLAY(matrix);
    }
}
