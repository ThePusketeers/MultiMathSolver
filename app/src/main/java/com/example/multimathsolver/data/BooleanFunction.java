package com.example.multimathsolver.data;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BooleanFunction {
    HashSet<String> parameters = new HashSet<>();
    int[][] table;
    public BooleanFunction(String expression) {
        LinkedList<String> list = new LinkedList<>();
        String temp = "";
        for (int i = 0; i < expression.length(); ++i) {
            char value = expression.charAt(i);
            if ((value >= '0' && value <= '9') || (value >= 'a' && value <= 'z') || (value >= 'A' && value <= 'Z')) {
                temp += value;
                if (i == expression.length() - 1) {
                    list.add(temp);
                    parameters.add(temp);
                }
            } else {
                if (!temp.isEmpty()) {
                    list.add(temp);
                    parameters.add(temp);
                    temp = "";
                }
                if (value != ' ') {
                    list.add(String.valueOf(value));
                }
            }
        }
        table = fillTheTable();
        list = processingOfUnaryOperation(list);
        List<String> operations = new ArrayList<>();
        operations.add("∧");
        operations.add("∨");
        operations.add("→");
        operations.add("⇔");
        operations.add("⊕");
        for (String operation : operations) {
            list = processingOfBinaryOperation(list, operation);
        }
    }

    private LinkedList<String> processingOfUnaryOperation(LinkedList<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            String value = list.get(i);
            LinkedList<String> copy = new LinkedList<>(list);
            if (value.equals("¬")) {
                i++;
                if (i < list.size() && list.get(i).equals("(")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.add("(");
                    int k = i + 1;
                    while (!deque.isEmpty() && k < list.size()) {
                        if (list.get(k).equals(")")) deque.pop();
                        else if (list.get(k).equals("(")) deque.add("(");
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

    private LinkedList<String> processingOfBinaryOperation(LinkedList<String> list, String operation) {
        for (int i = 0; i < list.size(); ++i) {
            String value = list.get(i);
            LinkedList<String> copy = new LinkedList<>(list);
            if (value.equals(operation)) {
                if (i - 1 > 0 && list.get(i-1).equals(")")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.add(")");
                    int k = i - 2;
                    while (!deque.isEmpty() && k > 0) {
                        if (list.get(k).equals(")")) deque.add(")");
                        else if (list.get(k).equals("(")) deque.pop();
                        k--;
                    }
                    copy.add(k, "(");
                } else {
                    copy.add(i - 2, "(");
                }
                if (i + 1 < list.size() && list.get(i + 1).equals("(")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.add("(");
                    int k = i + 1;
                    while (!deque.isEmpty() && k < list.size()) {
                        if (list.get(k).equals(")")) deque.pop();
                        else if (list.get(k).equals("(")) deque.add("(");
                        k++;
                    }
                    copy.add(k, ")");
                } else {
                    copy.add(i + 2, ")" );
                }
                i++;
            }
            list = copy;
        }
        return list;
    }

    private int[][] fillTheTable() {
        int[][] table = new int[(int) Math.pow(2, parameters.size())][parameters.size() + 1];
        int x = (int) Math.pow(2, parameters.size());
        for(int col = 0; x != 1; ++col) {
            x /= 2;
            boolean res = true;

            for(int i = 0; i < this.table.length; ++i) {
                if (i % x == 0) {
                    res = !res;
                }

                table[i][col] = res ? 1 : 0;
            }
        }
        return table;
    }

    private void fillTableWithValueOfFunction(List<String> subdivision) {
        for (int i = 0; i < Math.pow(2, parameters.size()); ++i) {
            List<String> params = new ArrayList<>(parameters);
            Deque<String> staples = new LinkedList<>();
            Deque<Integer> values = new LinkedList<>();
            Deque<String> operations = new LinkedList<>();
            for (int j = 0; j < subdivision.size(); ++j) {
                String value = subdivision.get(i);
                if (value.equals("(")) {
                    staples.add("(");
                } else if (value.equals(")")) {
                    String operation = operations.pop();
                    if (operation.equals("¬")) {
                        Integer value1 = values.pop();
                        values.add(unaryFunction(value1));
                    } else {
                        Integer value1 = values.pop();
                        Integer value2 = values.pop();
                        values.add(binaryFunction(operation, value2, value1));
                    }
                    staples.pop();
                } else if (params.contains(value)) {
                    values.add(table[i][params.indexOf(value)]);
                } else
                    operations.add(value);
                table[i][parameters.size()] = values.pop();
            }
        }
    }

    private int unaryFunction(int value) {
        if (value == 0) return 1;
        else return 0;
    }

    private int binaryFunction(String operation, int value1, int value2) {
        return 0;
    }
}
