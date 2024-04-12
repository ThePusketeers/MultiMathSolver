package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.data.booleanalgebra.ExpressionHandlerInterface;
import com.example.multimathsolver.data.booleanalgebra.IncorrectFunctionInput;

public class GetBooleanFunctionUseCase {
    private final Repository repository;

    public GetBooleanFunctionUseCase(Repository repository) {
        this.repository = repository;
    }

    public BooleanFunction getBooleanFunctionUseCase(ExpressionHandlerInterface expressionHandler) throws IncorrectFunctionInput {
        return repository.getBooleanFunction(expressionHandler);
    }
}
