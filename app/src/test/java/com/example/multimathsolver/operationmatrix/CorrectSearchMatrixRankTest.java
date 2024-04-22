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
public class CorrectSearchMatrixRankTest {
    Repository repository = new RepositoryImpl();
    MatrixOperations firstMatrix;
    int expectedRank;

    public CorrectSearchMatrixRankTest(double [][] firstMatrix, int expectedRank) {
        this.firstMatrix = new MatrixOperations(firstMatrix);
        this.expectedRank = expectedRank;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new double[][] {{8, 2, 6}, {1, 11, 8}, {5, 1, 8}}, 3},
                {new double[][] {{8, 2, 6}, {10, 2, 16}, {5, 1, 8}}, 2},
                {new double[][] {{8, 2, 6}, {10, 2, 16}, {5, 1, 8}, {15, 3, 24}}, 2},
                {new double[][] {{1, 6}, {2, 13}}, 2},
                {new double[][] {{1, 6}, {2, 12}}, 1},
        });
    }

    @Test
    public void testMultiplyTwoMatrix() throws Exception {
        Assert.assertEquals(expectedRank, repository.searchRank(firstMatrix));
    }
}
