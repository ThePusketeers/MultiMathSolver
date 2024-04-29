package com.example.multimathsolver.domain;

public class GetSequenceLimitUseCase {
    private final Repository repository;

    public GetSequenceLimitUseCase(Repository repository) {
        this.repository = repository;
    }

    public String getSequenceLimitUseCase(String sequence) {
        return repository.getSequenceLimit(sequence);
    }
}
