package com.example.multimathsolver.operationmatrix;

import com.example.multimathsolver.data.operationmatrix.MatrixOperations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AddOrMinusMatrixTest {
    MatrixOperations firstMatrix;
    MatrixOperations secondMatrix;

    MatrixOperations expectedMatrix;

    public AddOrMinusMatrixTest(double [][] firstMatrix, double [][] secondMatrix, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.secondMatrix = new MatrixOperations(secondMatrix);
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{1, 2, 3, 4, 5}, {4, 8, 3, 9, 1}}, new double[][] {{5, 1, 8, 2, 1}, {9, 22, 13, 7, 4}}, new double[][] {{6, 3, 11, 6, 6}, {13, 30, 16, 16, 5}}},
        });
    }

    @Test
    public void testAddTwoMatrix() throws Exception {
        Assert.assertEquals(expectedMatrix.getMatrix(), firstMatrix.add_or_minus(secondMatrix, '+').getMatrix());
    }



}
