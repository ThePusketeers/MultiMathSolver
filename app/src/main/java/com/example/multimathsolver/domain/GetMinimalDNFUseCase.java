package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public class GetMinimalDNFUseCase {
    private final Repository repository;

    public GetMinimalDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getMinimalDNFUseCase(BooleanFunction b) {
        return repository.getMinimalDNF(b);
    }
}

