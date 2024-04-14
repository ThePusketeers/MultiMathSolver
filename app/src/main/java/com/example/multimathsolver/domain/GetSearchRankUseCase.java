package com.example.multimathsolver.domain;

public class GetSearchRankUseCase {
    private final Repository repository;

    public GetSearchRankUseCase(Repository repository) {
        this.repository = repository;
    }

    public int getSearchRank(MatrixOperations firstMatrix) {
        return repository.search_rank(firstMatrix);
    }
}
