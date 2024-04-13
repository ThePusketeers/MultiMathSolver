package com.example.multimathsolver.domain;

public class GetPerfectCNFUseCase {
    private final Repository repository;

    public GetPerfectCNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getPerfectCNFUseCase(BooleanFunction function) {
        return repository.getPerfectCNF(function);
    }
}
