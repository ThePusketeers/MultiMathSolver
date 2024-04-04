package com.example.multimathsolver.data.booleanalgebra;

public enum BinaryOperation {
    CONJUNCTION("∧"),
    DISJUNCTION("∨"),
    XOR("⊕"),
    PIERCE_ARROW("↓"),
    SHEFFER_STROKE("↑"),
    IMPLICATION("→"),
    EQUIVALENCE("⇔")
    ;
    final String charOfOperation;
    BinaryOperation(String charOfOperation) {
        this.charOfOperation = charOfOperation;
    }

    public static boolean contains(String operation) {
        for (BinaryOperation operation1 : BinaryOperation.values()) {
            if (operation1.charOfOperation.equals(operation)) return true;
        }
        return false;
    }

    public static BinaryOperation valueOfString(String string) {
        for (BinaryOperation operation : BinaryOperation.values()) {
            if (operation.charOfOperation.equals(string)) return operation;
        }
        return CONJUNCTION;
    }

    public int resultOfOperation(int value1, int value2) {
        switch (this) {
            case CONJUNCTION:
                return value1 * value2;
            case DISJUNCTION:
                if (value1 + value2 == 0) return 0;
                else return 1;
            case IMPLICATION:
                if (value1 == 1 && value2 == 0) return 0;
                else return 1;
            case EQUIVALENCE:
                if (value1 == value2) return 1;
                else return 0;
            case XOR:
                return (value1 + value2) % 2;
            case PIERCE_ARROW:
                if (value1 + value2 == 0) return 1;
                else return 0;
            case SHEFFER_STROKE:
                if (value1 * value2 == 0) return 1;
                else  return 0;
        }
        return 0;
    }
}
