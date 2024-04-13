package com.example.multimathsolver.domain;

import java.util.Map;

public class GetBelongingToPostClassesUseCase {
    private final Repository repository;

    public GetBelongingToPostClassesUseCase(Repository repository) {
        this.repository = repository;
    }

    public Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function) {
        return repository.getBelongingToPostClasses(function);
    }
}
