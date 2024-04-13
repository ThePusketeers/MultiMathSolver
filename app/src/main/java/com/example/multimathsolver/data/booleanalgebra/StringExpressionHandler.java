package com.example.multimathsolver.data.booleanalgebra;

import com.example.multimathsolver.domain.IncorrectFunctionInput;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class StringExpressionHandler implements ExpressionHandler {
    private final HashSet<String> expressionParameters = new HashSet<>();
    private final List<String> listOfExpression;
    private final int[][] table;
    public StringExpressionHandler(String expression) {
        List<String> listOfExpression = expressionToList(expression);
        deleteStaplesAroundParameters(listOfExpression);
        listOfExpression = processingOfUnaryOperation(listOfExpression,
                UnaryOperation.NEGATION.charOfOperation);
        for (BinaryOperation operation : BinaryOperation.values()) {
            listOfExpression = processingOfBinaryOperation(listOfExpression, operation.charOfOperation);
        }
        this.listOfExpression = removeExtraBrackets(listOfExpression);
        table = fillTheTable();
    }

    private int[][] fillTheTable() {
        int[][] table = new int[(int) Math.pow(2, expressionParameters.size())][expressionParameters.size() + 1];
        int x = (int) Math.pow(2, expressionParameters.size());
        for(int col = 0; x != 1; ++col) {
            x /= 2;
            boolean res = true;
            for(int i = 0; i < table.length; ++i) {
                if (i % x == 0) {
                    res = !res;
                }
                table[i][col] = res ? 1 : 0;
            }
        }
        return table;
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

    @Override
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
                    copy.add(k+1, "(");
                } else {
                    copy.add(Math.max(i - 1, 0), "(");
                }
                if (i + 1 < list.size() && list.get(i + 1).equals("(")) {
                    Deque<String> deque = new LinkedList<>();
                    deque.push("(");
                    int k = i + 2;
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

    /** @noinspection DataFlowIssue*/
    private List<String> removeExtraBrackets(List<String> list) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int counter = -1;
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equals("(")) {
                counter++;
                map.put(i, counter);

            }
            if (list.get(i).equals(")")) {
                counter--;
                map.put(i, counter);

            }
        }
        HashSet<Integer> removable = new HashSet<>();
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).equals("(")) {
                int count = map.get(i);
                int j = i + 1;
                boolean flag = false;
                while (j < list.size() && count != map.get(i) - 1) {
                    if (list.get(j).equals("(")) count++;
                    if (list.get(j).equals(")")) count--;
                    if (count == map.get(i) && (UnaryOperation.contains(list.get(j)) || BinaryOperation.contains((list.get(j))))) {
                        flag = true;
                        break;
                    }
                    j++;
                }
                if (!flag) {
                    removable.add(i);
                    removable.add(j-1);
                }
            }
        }
        List<Integer> remove = new ArrayList<>(removable);
        remove.sort(((o1, o2) -> (-1) * (o1 - o2)));
        for (int i = 0; i < remove.size(); ++i) {
            list.remove((int) remove.get(i));
        }
        return list;
    }

    @Override
    public int[][] getTableWithValueOfFunction() throws IncorrectFunctionInput {
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
                table[i][expressionParameters.size()] = values.pop();
                if (staples < 0) throw new Exception();
            } catch (Exception ex) {
                throw new IncorrectFunctionInput("Неправильный ввод функции");
            }
        }
        return table;
    }
}
