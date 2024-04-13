package com.example.multimathsolver.data.booleanalgebra;

public enum UnaryOperation {
    NEGATION("¬"),
    ;
    public final String charOfOperation;

    UnaryOperation(String charOfOperation) {
        this.charOfOperation = charOfOperation;
    }

    public static boolean contains(String operation) {
        return NEGATION.charOfOperation.equals(operation);
    }

    public static int resultOfOperation(int value) {
        if (value == 0) return 1;
        else return 0;
    }
}
