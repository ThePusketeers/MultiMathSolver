package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.data.booleanalgebra.PostClass;

import java.util.Map;

public class GetBelongingToPostClassesUseCase {
    private final Repository repository;

    public GetBelongingToPostClassesUseCase(Repository repository) {
        this.repository = repository;
    }

    public Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction b) {
        return repository.getBelongingToPostClasses(b);
    }
}
