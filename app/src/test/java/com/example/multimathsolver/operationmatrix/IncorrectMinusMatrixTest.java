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
public class IncorrectMinusMatrixTest {
    MatrixOperations firstMatrix;
    MatrixOperations secondMatrix;

    MatrixOperations expectedMatrix;

    public IncorrectMinusMatrixTest(double [][] firstMatrix, double [][] secondMatrix, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.secondMatrix = new MatrixOperations(secondMatrix);
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{1, 2, 3, 4}, {4, 8, 3, 9}}, new double[][] {{5, 1, 8, 2, 1}, {9, 22, 13, 7, 4}}, new double[][] {{-4, 1, -5, 2, 4}, {-5, -14, -10, 2, -3}}},
                {new double[][] {{4, 2, 34}, {6, 11, 1}}, new double[][] {{15, 5}, {2, 14}}, new double[][] {{-11, -3}, {4, -3}}},
                {new double[][] {{5}}, new double[][] {{7, 24, 2}}, new double[][] {{-2}}},
                {new double[][] {{5}, {23}}, new double[][] {{11, 62, 14}, {451, 4, 9}}, new double[][] {{-6, -61, -9}, {-443, 10, 14}}},
        });
    }

    @Test
    public void testAddTwoMatrix() {
        Assert.assertThrows(IncorrectMatrixSize.class, () -> firstMatrix.add_or_minus(secondMatrix, '-').getMatrix());
    }
}
