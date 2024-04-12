package com.example.multimathsolver.operationmatrix;

import com.example.multimathsolver.data.operationmatrix.IncorrectMatrixSize;
import com.example.multimathsolver.data.operationmatrix.MatrixOperations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CorrectSearchDeterminantTest {
    MatrixOperations firstMatrix;
    double determinant;

    public CorrectSearchDeterminantTest(double [][] firstMatrix, double determinant) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.determinant = determinant;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{4, 2}, {3, -2}}, -14},
                {new double[][] {{4, 2}, {1, 3}}, 10},
                {new double[][] {{5, 2}, {1, 3}}, 13},
                {new double[][] {{5, 1, 5}, {8, 2, 4}, {13, 5, 2}}, 26},
                {new double[][] {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}, 0},
        });
    }

    @Test
    public void testAddTwoMatrix() throws IncorrectMatrixSize {
        Assert.assertEquals(determinant, firstMatrix.search_determinant(), 1);
    }
}
