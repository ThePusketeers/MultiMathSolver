package com.example.multimathsolver.domain;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;

public interface Repository {

    public int incrementByOne(int a);

    public BooleanFunction getBooleanFunction(String s);

    public String perfectDNF(BooleanFunction b);

    public String perfectCNF(BooleanFunction b);
}
