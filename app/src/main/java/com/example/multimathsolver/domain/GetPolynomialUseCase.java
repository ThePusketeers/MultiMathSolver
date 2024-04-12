package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public class GetPolynomialUseCase {
    private final Repository repository;

    public GetPolynomialUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getPolynomialUseCase(BooleanFunction b) {
        return repository.getPolynomial(b);
    }
}
