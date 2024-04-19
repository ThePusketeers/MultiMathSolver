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
public class CorrectMultiplyOnNumberTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    double number;

    MatrixOperations expectedMatrix;

    public CorrectMultiplyOnNumberTest(double [][] firstMatrix, double number, double [][]  expectedMatrix) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.number = number;
        this.expectedMatrix = new MatrixOperations(expectedMatrix);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{1, 2, 3, 4, 5}, {4, 8, 3, 9, 1}}, 3, new double[][] {{3, 6, 9, 12, 15}, {12, 24, 9, 27, 3}}},
        });
    }

    @Test
    public void testAddTwoMatrix() throws Exception {
        Assert.assertArrayEquals(expectedMatrix.getMatrix(), repository.multiplyOnNumber(firstMatrix, number).getMatrix());
    }



}

