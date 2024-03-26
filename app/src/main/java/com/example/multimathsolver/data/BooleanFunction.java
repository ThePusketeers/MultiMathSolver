package com.example.multimathsolver.data;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BooleanFunction {
    HashSet<String> parameters = new HashSet<>();
    public int[][] table;
    List<String> operations;
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
        deleteStaplesAroundParameters(list);
        table = fillTheTable();
        list = processingOfUnaryOperation(list);
        operations = new ArrayList<>();
        operations.add("∧");
        operations.add("∨");
        operations.add("→");
        operations.add("⇔");
        operations.add("⊕");
        for (String operation : operations) {
            list = processingOfBinaryOperation(list, operation);
        }
        list = removeExtraBrackets(list);
        boolean isException = fillTableWithValueOfFunction(list);
        if (isException) {
            System.out.println("Функция введена неверно");
        }
    }

    private LinkedList<String> processingOfUnaryOperation(LinkedList<String> list) {
        for (int i = 0; i < list.size(); ++i) {
            String value = list.get(i);
            LinkedList<String> copy = new LinkedList<>(list);
            if (value.equals("¬")) {
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

    private LinkedList<String> processingOfBinaryOperation(LinkedList<String> list, String operation) {
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

    private int[][] fillTheTable() {
        int[][] table = new int[(int) Math.pow(2, parameters.size())][parameters.size() + 1];
        int x = (int) Math.pow(2, parameters.size());
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

    private LinkedList<String> removeExtraBrackets(LinkedList<String> list) {
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
                    if (operations.contains(list.get(l)) || list.get(l).equals("¬")) {
                        flag = true;
                        break;
                    }
                    l++;
                }
                r--;
                while (r >= 0 && !list.get(r).equals(")") && !flag) {
                    if (operations.contains(list.get(r)) || list.get(r).equals("¬")) {
                        flag = true;
                        break;
                    } else if (parameters.contains(list.get(r)) && l > r) {
                        r--;
                        break;
                    }
                    r--;
                }
                if (!flag) {
                    LinkedList<String> copy = new LinkedList<>(list);
                    if (r > l) {
                        copy.remove(r);
                        copy.remove(l);
                    } else {
                        copy.remove(l);
                        copy.remove(r);
                    }
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

    private boolean fillTableWithValueOfFunction(List<String> subdivision) {
        for (int i = 0; i < Math.pow(2, parameters.size()); ++i) {
            List<String> params = new ArrayList<>(parameters);
            params.sort(String::compareTo);
            Deque<String> staples = new LinkedList<>();
            Deque<Integer> values = new LinkedList<>();
            Deque<String> operations = new LinkedList<>();
            try {
                for (int j = 0; j < subdivision.size(); ++j) {
                    String value = subdivision.get(j);
                    if (value.equals("(")) {
                        staples.push("(");
                    } else if (value.equals(")")) {
                        String operation = operations.pop();
                        if (operation.equals("¬")) {
                            Integer value1 = values.pop();
                            values.push(unaryFunction(value1));
                        } else {
                            Integer value1 = values.pop();
                            Integer value2 = values.pop();
                            values.push(binaryFunction(operation, value2, value1));
                        }
                        staples.pop();
                    } else if (params.contains(value)) {
                        values.push(table[i][params.indexOf(value)]);
                    } else
                        operations.push(value);
                }
                while (!operations.isEmpty()) {
                    String operation = operations.pop();
                    if (operation.equals("¬")) {
                        Integer value1 = values.pop();
                        values.push(unaryFunction(value1));
                    } else {
                        Integer value1 = values.pop();
                        Integer value2 = values.pop();
                        values.push(binaryFunction(operation, value2, value1));
                    }
                }
                table[i][parameters.size()] = values.pop();
            } catch (Exception ex) {
                return true;
            }
        }
        return false;
    }

    private void deleteStaplesAroundParameters(LinkedList<String> list) {
        for (int i = 1; i < list.size() - 1; ++i) {
            if (parameters.contains(list.get(i)) &&  list.get(i-1).equals("(") && list.get(i+1).equals(")")) {
                list.remove(i+1);
                list.remove(i-1);
            }
        }
    }

    private int unaryFunction(int value) {
        if (value == 0) return 1;
        else return 0;
    }

    private int binaryFunction(String operation, int value1, int value2) {
        switch (operation) {
            case "∧":
                return value1 * value2;
            case "∨":
                if (value1 + value2 == 0) return 0;
                else return 1;
            case "→":
                if (value1 == 1 && value2 == 0) return 0;
                else return 1;
            case "⇔":
                if (value1 == value2) return 1;
                else return 0;
            case "⊕":
                return (value1 + value2) % 2;
        }
        return 0;
    }
}

// сделать enum для операций.
