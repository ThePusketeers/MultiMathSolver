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
public class IncorrectRaiseDegreeMatrixTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    int degree;

    MatrixOperations expectedMatrix;

    public IncorrectRaiseDegreeMatrixTest(double [][] firstMatrix, int degree, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.degree = degree;
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{8, 2, 6}, {1, 11, 8}}, 2, new double[][] {{96, 44, 112}, {59, 131, 158}, {81, 29, 102}}},
                {new double[][] {{8, 2, 6}, {1, 11, 8}, {1, 5, 1}, {6, 1, 6}}, 4, new double[][] {{12, 254, 112}, {534, 123, 451}, {53, 5, 2}}},
                {new double[][] {{8, 2}, {1, 8}, {1, 1}, {25, 6}}, 3, new double[][] {{35, 2, 6}, {24, 6, 54}, {12, 52, 75}}},
        });
    }

    @Test
    public void testMultiplyTwoMatrix() {
        Assert.assertThrows(IncorrectMatrixSize.class, () -> repository.raise_to_degree(firstMatrix, degree));
    }
}
