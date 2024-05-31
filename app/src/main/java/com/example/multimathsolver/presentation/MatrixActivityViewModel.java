package com.example.multimathsolver.presentation;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetAddOrMinusUseCase;
import com.example.multimathsolver.domain.GetMultiplicationUseCase;
import com.example.multimathsolver.domain.GetMultiplyOnNumberUseCase;
import com.example.multimathsolver.domain.GetRaiseToDegreeUseCase;
import com.example.multimathsolver.domain.GetSearchDeterminantUseCase;
import com.example.multimathsolver.domain.GetSearchRankUseCase;
import com.example.multimathsolver.domain.MatrixOperations;
import com.example.multimathsolver.domain.Repository;

public class MatrixActivityViewModel {
    private final Repository repository = new RepositoryImpl();
    private final static int[][] DEFAULT_MATRIX = new int[3][3];
    private final GetSearchDeterminantUseCase useCaseGetSearchDeterminant = new GetSearchDeterminantUseCase(repository);
    private final GetAddOrMinusUseCase useCaseGetAddOrMinus = new GetAddOrMinusUseCase(repository);
    private final GetMultiplicationUseCase useCaseGetMultiplication = new GetMultiplicationUseCase(repository);
    private final GetMultiplyOnNumberUseCase useCaseGetMultiplyOnNumber = new GetMultiplyOnNumberUseCase(repository);
    private final GetRaiseToDegreeUseCase useCaseGetRaiseToDegree = new GetRaiseToDegreeUseCase(repository);
    private final GetSearchRankUseCase useCaseGetSearchRank = new GetSearchRankUseCase(repository);

    public void solve(int[][] matrix){

    }
}
