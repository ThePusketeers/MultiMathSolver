package com.example.multimathsolver.domain;

public class GetDeadLockedDNFUseCase {
    private final Repository repository;

    public GetDeadLockedDNFUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getDeadLockedDNFUseCase(BooleanFunction function) {
        return repository.getDeadLockedDNF(function);
    }
}

