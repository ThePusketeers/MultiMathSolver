package com.example.multimathsolver.data.booleanalgebra;

enum UnaryOperation {
    NEGATION("¬"),
    ;
    final String charOfOperation;

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
