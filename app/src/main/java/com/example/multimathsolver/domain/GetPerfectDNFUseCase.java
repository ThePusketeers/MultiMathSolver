package com.example.multimathsolver.domain;

public class GetPerfectDNFUseCase {
    private final Repository repository;

    public GetPerfectDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getPerfectDNFUseCase(BooleanFunction function) {
        return repository.getPerfectDNF(function);
    }
}
