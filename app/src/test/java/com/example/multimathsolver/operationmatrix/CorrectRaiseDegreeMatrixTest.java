package com.example.multimathsolver.operationmatrix;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.MatrixOperations;
import com.example.multimathsolver.domain.Repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CorrectRaiseDegreeMatrixTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    int degree;

    MatrixOperations expectedMatrix;

    public CorrectRaiseDegreeMatrixTest(double [][] firstMatrix, int degree, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.degree = degree;
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{8, 2, 6}, {1, 11, 8}, {5, 1, 8}}, 2, new double[][] {{96, 44, 112}, {59, 131, 158}, {81, 29, 102}}},
                {new double[][] {{8, 2}, {1, 8}}, 7, new double[][] {{66, 32}, {16, 66}}},
        });
    }

    @Test
    public void testMultiplyTwoMatrix() throws Exception {
        Assert.assertArrayEquals(expectedMatrix.getMatrix(), repository.raise_to_degree(firstMatrix, degree).getMatrix());
    }
}
