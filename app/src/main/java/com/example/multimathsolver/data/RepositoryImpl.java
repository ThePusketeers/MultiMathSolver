package com.example.multimathsolver.data;

import com.example.multimathsolver.data.mathematicalAnalysis.SequenceLimit;
import com.example.multimathsolver.domain.Repository;

public class RepositoryImpl implements Repository {
    @Override
    public String getSequenceLimit(String sequence) {
        return SequenceLimit.solve(sequence);
    }
}