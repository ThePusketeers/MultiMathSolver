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
public class CorrectMultiplicationMatrixTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    MatrixOperations secondMatrix;
    MatrixOperations expectedMatrix;

    public CorrectMultiplicationMatrixTest(double [][] firstMatrix, double [][] secondMatrix, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.secondMatrix = new MatrixOperations(secondMatrix);
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{5, 1, 7}, {12, 5, 8}, {9, 12, 11}}, new double[][] {{8, 4, 9}, {4, 7, 7}, {1, 9, 5}}, new double[][] {{51, 90, 87}, {124, 155, 183}, {131, 219, 220}}},
                {new double[][] {{15, 14, 5}, {1, 4, 7}}, new double[][] {{18, 8, 1}, {4, 1, 4}, {9, 2, 7}}, new double[][] {{371, 144, 106}, {97, 26, 66}}},

        });
    }

    @Test
    public void testMultiplyTwoMatrix() throws IncorrectMatrixSize {
        Assert.assertArrayEquals(expectedMatrix.getMatrix(), repository.multiplication(firstMatrix, secondMatrix).getMatrix());
    }
}
