package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public class GetAbbreviatedDNFUseCase {
    private final Repository repository;

    public GetAbbreviatedDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getAbbreviatedDNFUseCase(BooleanFunction b) {
        return repository.getAbbreviatedDNF(b);
    }
}