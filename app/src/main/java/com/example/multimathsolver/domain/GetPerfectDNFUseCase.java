package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public class GetPerfectDNFUseCase {
    private final Repository repository;

    public GetPerfectDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getPerfectDNFUseCase(BooleanFunction b) {
        return repository.getPerfectDNF(b);
    }
}
