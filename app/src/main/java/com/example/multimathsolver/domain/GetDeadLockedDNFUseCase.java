package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public class GetDeadLockedDNFUseCase {
    private final Repository repository;

    public GetDeadLockedDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getDeadLockedDNFUseCase(BooleanFunction b) {
        return repository.getDeadLockedDNF(b);
    }
}

