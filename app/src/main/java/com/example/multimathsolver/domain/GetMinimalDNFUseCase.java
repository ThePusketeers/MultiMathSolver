package com.example.multimathsolver.domain;

public class GetMinimalDNFUseCase {
    private final Repository repository;

    public GetMinimalDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getMinimalDNFUseCase(BooleanFunction function) {
        return repository.getMinimalDNF(function);
    }
}

