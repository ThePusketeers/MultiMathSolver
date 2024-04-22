package com.example.multimathsolver.domain;

public class GetPolynomialUseCase {
    private final Repository repository;

    public GetPolynomialUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getPolynomialUseCase(BooleanFunction function) {
        return repository.getPolynomial(function);
    }
}
