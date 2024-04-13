package com.example.multimathsolver.domain;


public class GetBooleanFunctionUseCase {
    private final Repository repository;

    public GetBooleanFunctionUseCase(Repository repository) {
        this.repository = repository;
    }

    public BooleanFunction getBooleanFunctionUseCase(String expression) throws IncorrectFunctionInput {
        return repository.getBooleanFunction(expression);
    }
}
