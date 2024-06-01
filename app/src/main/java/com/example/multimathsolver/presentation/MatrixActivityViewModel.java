package com.example.multimathsolver.presentation;

import androidx.lifecycle.MutableLiveData;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetAddOrMinusUseCase;
import com.example.multimathsolver.domain.GetMultiplicationUseCase;
import com.example.multimathsolver.domain.GetMultiplyOnNumberUseCase;
import com.example.multimathsolver.domain.GetRaiseToDegreeUseCase;
import com.example.multimathsolver.domain.GetSearchDeterminantUseCase;
import com.example.multimathsolver.domain.GetSearchRankUseCase;
import com.example.multimathsolver.domain.IncorrectMatrixSize;
import com.example.multimathsolver.domain.MatrixOperations;
import com.example.multimathsolver.domain.Repository;

public class MatrixActivityViewModel {
    private final Repository repository = new RepositoryImpl();
    private final GetSearchDeterminantUseCase useCaseGetSearchDeterminant = new GetSearchDeterminantUseCase(repository);
    private final GetAddOrMinusUseCase useCaseGetAddOrMinus = new GetAddOrMinusUseCase(repository);
    private final GetMultiplicationUseCase useCaseGetMultiplication = new GetMultiplicationUseCase(repository);
    private final GetMultiplyOnNumberUseCase useCaseGetMultiplyOnNumber = new GetMultiplyOnNumberUseCase(repository);
    private final GetRaiseToDegreeUseCase useCaseGetRaiseToDegree = new GetRaiseToDegreeUseCase(repository);
    private final GetSearchRankUseCase useCaseGetSearchRank = new GetSearchRankUseCase(repository);

    MutableLiveData<Double> determinant = new MutableLiveData<>();
    MutableLiveData<Integer> rang = new MutableLiveData<>();
    MutableLiveData<MatrixOperations> outputMatrix = new MutableLiveData<>();
    double[][] matrixA;
    double[][] matrixB;

//    MutableLiveData<Integer> rows = new MutableLiveData<>(5);
//    MutableLiveData<Integer> columns = new MutableLiveData<>(5);

    ///Input Example///
    double[][] matrixAsArray = new double[][] { {1, 2, 3, 4}, {4, 8, 3, 9},{5, 1, 8, 2}, {9, 22, 13, 7} };
    double[][] matrixAsArray2 = new double[][] { {1, 2, 3, 4}, {4, 8, 3, 9},{5, 1, 8, 2}, {9, 22, 13, 7} };
    ///Example///
    MatrixOperations matrixOperations = new MatrixOperations(matrixAsArray);
    MatrixOperations matrixOperations2 = new MatrixOperations(matrixAsArray2);

    public void solveDeterminant(MatrixOperations matrixOperations) throws IncorrectMatrixSize {
        determinant.setValue( useCaseGetSearchDeterminant.getSearchDeterminant(matrixOperations) );
    }
    public void solveRang(MatrixOperations matrixOperations){
        rang.setValue( useCaseGetSearchRank.getSearchRank(matrixOperations) );
    }
    public void solveMultiplyByConstant(MatrixOperations matrixOperations, double constantForMultiply){
        outputMatrix.setValue( useCaseGetMultiplyOnNumber.getMultiplyOnNumber(matrixOperations, constantForMultiply) );
    }
    public void solveRaiseToDegree(MatrixOperations matrixOperations, int degree) throws IncorrectMatrixSize {
        outputMatrix.setValue( useCaseGetRaiseToDegree.getRaiseToDegree(matrixOperations,degree) );
    }
    public void solveMultiplyMatrix(MatrixOperations matrixOperations, MatrixOperations matrixOperations2) throws IncorrectMatrixSize {
        outputMatrix.setValue( useCaseGetMultiplication.getMultiplication(matrixOperations,matrixOperations2));
    }
    public void solveSumMatrix(MatrixOperations matrixOperations, MatrixOperations matrixOperations2) throws IncorrectMatrixSize {
        outputMatrix.setValue( useCaseGetAddOrMinus.getAddOrMinus(matrixOperations,matrixOperations2,'+'));
    }
    public void solveSubtractMatrix(MatrixOperations matrixOperations, MatrixOperations matrixOperations2) throws IncorrectMatrixSize {
        outputMatrix.setValue( useCaseGetAddOrMinus.getAddOrMinus(matrixOperations,matrixOperations2,'-'));
    }
}
