package com.example.multimathsolver.operationmatrix;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.IncorrectMatrixSize;
import com.example.multimathsolver.domain.MatrixOperations;
import com.example.multimathsolver.domain.Repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class IncorrectSearchDeterminantTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    double determinant;

    public IncorrectSearchDeterminantTest(double [][] firstMatrix, double determinant) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.determinant = determinant;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{5, 1, 5}, {8, 2, 4}}, 2},
                {new double[][] {{5, 1, 5}, {8, 2, 4}, {13, 5, 2}, {6, 1, 77}}, 65},
                {new double[][] {{5, 1}, {2, 4}, {4, 1}}, 1},
        });
    }

    @Test
    public void testAddTwoMatrix() {
        Assert.assertThrows(IncorrectMatrixSize.class, () -> repository.searchDeterminant(firstMatrix));
    }
}
