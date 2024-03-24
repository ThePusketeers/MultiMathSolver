package com.example.multimathsolver.data;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BooleanFunction {
    HashSet<String> parameters = new HashSet<>();
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
}
