package com.example.multimathsolver.domain;

public class IncrementByOneUseCase {

    private final Repository repository;

    public IncrementByOneUseCase(Repository repository) {
        this.repository = repository;
    }

    public int incrementByOne(int a) {
        return repository.incrementByOne(a);
    }

}
