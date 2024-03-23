package com.example.multimathsolver.data;

import com.example.multimathsolver.domain.Repository;

public class RepositoryImpl implements Repository {

    @Override
    public int incrementByOne(int a) {
        a++;
        return a;
    }
}
