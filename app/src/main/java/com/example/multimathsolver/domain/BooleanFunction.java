package com.example.multimathsolver.domain;

import java.util.ArrayList;
import java.util.List;

public class BooleanFunction {
    private List<String> parameters;
    private int[][] table;

    public List<String> getParameters() {
        return new ArrayList<>(parameters);
    }

    public int[][] getTable() {
        int[][] output = new int[table.length][table[0].length];
        for (int i = 0; i < table.length; ++i) {
            System.arraycopy(table[i], 0, output[i], 0, table[0].length);
        }
        return output;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}
