package com.example.multimathsolver.domain;

import com.example.multimathsolver.domain.models.LimitResponse;

import io.reactivex.rxjava3.core.Single;

public class GetFunctionLimitUseCase {
    private final Repository repository;

    public GetFunctionLimitUseCase(Repository repository) {
        this.repository = repository;
    }

    public Single<LimitResponse> getFunctionLimitUseCase(String function, Double strivesFor) {
        return repository.getFunctionLimit(function, strivesFor);
    }
}
