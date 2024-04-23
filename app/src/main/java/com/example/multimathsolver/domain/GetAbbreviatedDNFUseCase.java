package com.example.multimathsolver.domain;

public class GetAbbreviatedDNFUseCase {
    private final Repository repository;

    public GetAbbreviatedDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getAbbreviatedDNFUseCase(BooleanFunction function) {
        return repository.getAbbreviatedDNF(function);
    }
}