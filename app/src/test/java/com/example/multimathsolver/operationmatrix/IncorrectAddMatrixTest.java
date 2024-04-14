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
public class IncorrectAddMatrixTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    MatrixOperations secondMatrix;

    MatrixOperations expectedMatrix;

    public IncorrectAddMatrixTest(double [][] firstMatrix, double [][] secondMatrix, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.secondMatrix = new MatrixOperations(secondMatrix);
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{1, 2, 3, 4, 5}, {4, 8, 3, 9, 1}}, new double[][] {{5, 1, 8, 2, 1}, {9, 22, 13, 7, 4}, {29, 242, 135, 72, 45}}, new double[][] {{6, 3, 11, 6, 6}, {13, 30, 16, 16, 5}}},
                {new double[][] {{4, 2}}, new double[][] {{15, 5}, {2, 14}}, new double[][] {{19, 7}, {8, 25}}},
                {new double[][] {{5, 5, 1}}, new double[][] {{7}}, new double[][] {{12}}},
                {new double[][] {{5, 1}, {8, 14}}, new double[][] {{11, 62, 14}, {451, 4, 9}}, new double[][] {{16, 63, 19}, {459, 18, 32}}},
        });
    }

    @Test
    public void testAddTwoMatrix() {
        Assert.assertThrows(IncorrectMatrixSize.class, () -> repository.add_or_minus(firstMatrix, secondMatrix, '+'));
    }
}
