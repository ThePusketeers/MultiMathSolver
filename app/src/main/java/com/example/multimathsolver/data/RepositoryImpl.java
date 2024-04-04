package com.example.multimathsolver.data;

import com.example.multimathsolver.data.booleanalgebra.BooleanFunction;
import com.example.multimathsolver.domain.Repository;

public class RepositoryImpl implements Repository {

    @Override
    public int incrementByOne(int a) {
        a++;
        return a;
    }

    @Override
    public BooleanFunction getBooleanFunction(String s) {
        return new BooleanFunction(s);
    }

    @Override
    public String perfectDNF(BooleanFunction b) {
        return b.getPerfectDNF();
    }

    @Override
    public String perfectCNF(BooleanFunction b) {
        return b.getPerfectCNF();
    }

}
