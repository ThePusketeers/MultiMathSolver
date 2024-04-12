package com.example.multimathsolver.operationmatrix;

import com.example.multimathsolver.data.operationmatrix.MatrixOperations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MultiplicationMatrixTest {
    MatrixOperations firstMatrix;
    MatrixOperations secondMatrix;

    MatrixOperations expectedMatrix;

    public MultiplicationMatrixTest(double [][] firstMatrix, double [][] secondMatrix, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.secondMatrix = new MatrixOperations(secondMatrix);
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{5, 1, 7}, {12, 5, 8}, {9, 12, 11}}, new double[][] {{8, 4, 9}, {4, 7, 7}, {1, 9, 5}}, new double[][] {{51, 90, 87}, {124, 155, 183}, {131, 219, 220}}},
        });
    }

    @Test
    public void testMultiplyTwoMatrix() throws Exception {
        Assert.assertEquals(expectedMatrix.getMatrix(), firstMatrix.multiplication(secondMatrix).getMatrix());
    }
}
