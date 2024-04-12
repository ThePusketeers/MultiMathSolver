package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public class GetPerfectCNFUseCase {
    private final Repository repository;

    public GetPerfectCNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getPerfectCNFUseCase(BooleanFunction b) {
        return repository.getPerfectCNF(b);
    }
}
