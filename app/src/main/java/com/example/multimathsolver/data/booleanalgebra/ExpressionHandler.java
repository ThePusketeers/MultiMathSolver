package com.example.multimathsolver.data.booleanalgebra;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ExpressionHandler {
    HashSet<String> expressionParameters = new HashSet<>();
    List<String> listOfExpression;
    public ExpressionHandler(String expression) {
        List<String> listOfExpression = expressionToList(expression);
        deleteStaplesAroundParameters(listOfExpression);
        listOfExpression = processingOfUnaryOperation(listOfExpression,
                UnaryOperation.NEGATION.charOfOperation);
        for (BinaryOperation operation : BinaryOperation.values()) {
            listOfExpression = processingOfBinaryOperation(listOfExpression, operation.charOfOperation);
        }
        this.listOfExpression = removeExtraBrackets(listOfExpression);
    }

    private List<String> expressionToList(String expression) {
        LinkedList<String> list = new LinkedList<>();
        String temp = "";
        for (int i = 0; i < expression.length(); ++i) {
            char value = expression.charAt(i);
            if ((value >= '0' && value <= '9') || (value >= 'a' && value <= 'z') || (value >= 'A' && value <= 'Z')) {
                temp += value;
                if (i == expression.length() - 1) {
                    list.add(temp);
                    expressionParameters.add(temp);
                }
            } else {
                if (!temp.isEmpty()) {
                    list.add(temp);
                    expressionParameters.add(temp);
                    temp = "";
                }
                if (value != ' ') {
                    list.add(String.valueOf(value));
                }
            }
        }
        return list;
    }
    public List<String> getExpressionParameters() {
        List<String> parameters = new LinkedList<>(expressionParameters);
        parameters.sort(String::compareTo);
        return parameters;
    }

    private void deleteStaplesAroundParameters(List<String> list) {
        for (int i = 1; i < list.size() - 1; ++i) {
            if (expressionParameters.contains(list.get(i)) &&  list.get(i-1).equals("(") && list.get(i+1).equals(")")) {
                list.remove(i+1);
                list.remove(i-1);
            }
        }
    }

    private List<String> processingOfUnaryOperation(List<String> list, String operation) {
        for (int i = 0; i < list.size(); ++i) {
            String value = list.get(i);
            LinkedList<String> copy = new LinkedList<>(list);
            if (value.equals(operation)) {
                copy.add(Math.max(i, 0), "(");
                i++;
                if (i < list.size() && list.get(i).equals("(")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.push("(");
                    int k = i + 1;
                    while (!deque.isEmpty() && k < list.size()) {
                        if (list.get(k).equals(")")) deque.pop();
                        else if (list.get(k).equals("(")) deque.push("(");
                        k++;
                    }
                    copy.add(k, ")");
                } else {
                    copy.add(i + 2, ")" );
                }
            }
            list = copy;
        }
        return list;
    }

    private List<String> processingOfBinaryOperation(List<String> list, String operation) {
        for (int i = 0; i < list.size(); ++i) {
            String value = list.get(i);
            LinkedList<String> copy = new LinkedList<>(list);
            if (value.equals(operation)) {
                if (i - 1 > 0 && list.get(i-1).equals(")")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.push(")");
                    int k = i - 2;
                    while (!deque.isEmpty() && k > 0) {
                        if (list.get(k).equals(")")) deque.push(")");
                        else if (list.get(k).equals("(")) deque.pop();
                        k--;
                    }
                    copy.add(k, "(");
                } else {
                    copy.add(Math.max(i - 1, 0), "(");
                }
                if (i + 1 < list.size() && list.get(i + 1).equals("(")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.push("(");
                    int k = i + 1;
                    while (!deque.isEmpty() && k < list.size()) {
                        if (list.get(k).equals(")")) deque.pop();
                        else if (list.get(k).equals("(")) deque.push("(");
                        k++;
                    }
                    copy.add(k, ")");
                } else {
                    copy.add(i + 3, ")" );
                }
                i++;
            }
            list = copy;
        }
        return list;
    }

    private List<String> removeExtraBrackets(List<String> list) {
        int i = 0;
        while (i < list.size()){
            String value = list.get(i);
            if (value.equals("(")) {
                Deque<String> staples = new LinkedList<>();
                staples.push("(");
                int r = i + 1;
                while (!staples.isEmpty() && r < list.size()) {
                    if (list.get(r).equals(")")) staples.pop();
                    else if (list.get(r).equals("(")) staples.push("(");
                    r++;
                }
                r--;
                int l = i + 1;
                boolean flag = false;
                while (l < list.size() && !list.get(l).equals("(") && l < r) {
                    if (BinaryOperation.contains(list.get(l)) || UnaryOperation.contains(list.get(l))) {
                        flag = true;
                        break;
                    }
                    l++;
                }
                r--;
                while (r >= 0 && !list.get(r).equals(")") && !flag) {
                    if (BinaryOperation.contains(list.get(r)) || UnaryOperation.contains(list.get(r))) {
                        flag = true;
                        break;
                    }
                    r--;
                }
                if (!flag) {
                    LinkedList<String> copy = new LinkedList<>(list);
                    copy.remove(r);
                    copy.remove(l);
                    list = copy;
                    i = 0;
                }
                else {
                    i++;
                }
            } else {
                i++;
            }
        }
        return list;
    }

    public boolean fillTableWithValueOfFunction(int[][] table) {
        for (int i = 0; i < Math.pow(2, expressionParameters.size()); ++i) {
            int staples = 0;
            Deque<Integer> values = new LinkedList<>();
            Deque<String> operations = new LinkedList<>();
            try {
                for (int j = 0; j < listOfExpression.size(); ++j) {
                    String value = listOfExpression.get(j);
                    if (value.equals("(")) {
                        staples++;
                    } else if (value.equals(")")) {
                        String operation = operations.pop();
                        Integer value1 = values.pop();
                        if (UnaryOperation.NEGATION.charOfOperation.equals(operation)) {
                            values.push(UnaryOperation.resultOfOperation(value1));
                        } else {
                            Integer value2 = values.pop();
                            values.push(BinaryOperation.valueOfString(operation).resultOfOperation(value2, value1));
                        }
                        staples--;
                    } else if (expressionParameters.contains(value)) {
                        values.push(table[i][getExpressionParameters().indexOf(value)]);
                    } else
                        operations.push(value);
                }
                while (!operations.isEmpty()) {
                    String operation = operations.pop();
                    if (UnaryOperation.NEGATION.charOfOperation.equals(operation)) {
                        Integer value1 = values.pop();
                        values.push(UnaryOperation.resultOfOperation(value1));
                    } else {
                        Integer value1 = values.pop();
                        Integer value2 = values.pop();
                        values.push(BinaryOperation.valueOfString(operation).resultOfOperation(value2, value1));
                    }
                }
                table[i][expressionParameters.size()] = values.pop();
                if (staples < 0) throw new Exception();
            } catch (Exception ex) {
                return true;
            }
        }
        return false;
    }
}
