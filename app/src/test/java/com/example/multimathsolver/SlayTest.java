package com.example.multimathsolver;

import com.example.multimathsolver.data.RepositoryImpl;
import com.example.multimathsolver.domain.GetSLAYSolutionUseCase;
import com.example.multimathsolver.domain.Repository;
import com.example.multimathsolver.domain.SLAY;

import org.junit.Assert;
import org.junit.Test;

public class SlayTest {
    @Test
    public void solveTest() {
        SLAY coeffSLAY = new SLAY(new double[][]{{2,1,1}, {1,-1,0}, {3, -1, 2}}, 1);
        SLAY additionalSLAY = new SLAY(new double[][]{{2}, {-2}, {-2}}, 1);
        Repository repository = new RepositoryImpl();
        Assert.assertEquals("", repository.getSolutionOfSLAY(coeffSLAY, additionalSLAY).toString());
    }
}
