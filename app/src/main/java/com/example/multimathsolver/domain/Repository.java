package com.example.multimathsolver.domain;


import com.example.multimathsolver.data.slay.SLAY;

public interface Repository {

    public int incrementByOne(int a);

    public String solutionOfSLAY(SLAY matrix);
}
