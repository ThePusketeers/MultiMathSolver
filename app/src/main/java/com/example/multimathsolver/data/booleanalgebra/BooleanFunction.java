package com.example.multimathsolver.data.booleanalgebra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BooleanFunction {
    List<String> parameters;
    public int[][] table;
    public BooleanFunction(String expression) {
        ExpressionHandler expressionHandler = new ExpressionHandler(expression);
        this.parameters = expressionHandler.getExpressionParameters();
        this.table = fillTheTable();
        boolean isException = expressionHandler.fillTableWithValueOfFunction(this.table);
        if (isException) {
            System.out.println("Функция введена неверно");
        }
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

    public String getPerfectDNF() {
        StringBuilder output = new StringBuilder();
        for (int[] ints : table) {
            if (ints[table[0].length - 1] == 1) {
                output.append("(");
                for (int j = 0; j < table[0].length - 1; ++j) {
                    if (ints[j] == 0) {
                        output.append(UnaryOperation.NEGATION.charOfOperation);
                    }
                    output.append(parameters.get(j))
                            .append(" ")
                            .append(BinaryOperation.CONJUNCTION.charOfOperation)
                            .append(" ");
                }
                if (output.length() >= 3) {
                    output = new StringBuilder(output.substring(0, output.length() - 3));
                }
                output.append(") ").append(BinaryOperation.DISJUNCTION.charOfOperation).append(" ");
            }
        }
        if (output.length() >= 3) {
            output = new StringBuilder(output.substring(0, output.length() - 3));
        }
        return output.toString();
    }

    public String getPerfectCNF() {
        StringBuilder output = new StringBuilder();
        for (int[] ints : table) {
            if (ints[table[0].length - 1] == 0) {
                output.append("(");
                for (int j = 0; j < table[0].length - 1; ++j) {
                    if (ints[j] == 1) {
                        output.append(UnaryOperation.NEGATION.charOfOperation);
                    }
                    output.append(parameters.get(j))
                            .append(" ")
                            .append(BinaryOperation.DISJUNCTION.charOfOperation)
                            .append(" ");
                }
                if (output.length() >= 3) {
                    output = new StringBuilder(output.substring(0, output.length() - 3));
                }
                output.append(") ").append(BinaryOperation.CONJUNCTION.charOfOperation).append(" ");
            }
        }
        if (output.length() >= 3) {
            output = new StringBuilder(output.substring(0, output.length() - 3));
        }
        return output.toString();
    }

    private List<List<Integer>> getListOfSDNF() {
        List<List<Integer>> listOfAbbreviatedDNF = new ArrayList<>();
        for (int[] row : table) {
            if (row[row.length - 1] == 1) {
                List<Integer> listOfValuesOfParameters = new ArrayList<>();
                for (int j = 0; j < row.length - 1; ++j) {
                    listOfValuesOfParameters.add(row[j]);
                }
                listOfAbbreviatedDNF.add(listOfValuesOfParameters);
            }
        }
        return listOfAbbreviatedDNF;
    }

    private List<List<Integer>> getListOfAbbreviatedDNF() {
        List<List<Integer>> abbreviatedDNF = getListOfSDNF();
        do {
            List<List<Integer>> clone = new ArrayList<>(abbreviatedDNF);
            int counter = 0;
            for (int k = 0; k < parameters.size(); ++k) {
                for (int j = 0; j < abbreviatedDNF.size(); ++j) {
                    for (int i = j + 1; i < abbreviatedDNF.size(); ++i) {
                        if (!abbreviatedDNF.get(j).get(k).equals(abbreviatedDNF.get(i).get(k))) {
                            List<Integer> lst1 = new ArrayList<>(abbreviatedDNF.get(j));
                            List<Integer> lst2 = new ArrayList<>(abbreviatedDNF.get(i));
                            lst1.set(k, -1);
                            lst2.set(k, -1);
                            if (lst1.equals(lst2)) {
                                clone.add(lst1);
                                counter++;
                                clone.remove(abbreviatedDNF.get(i));
                                clone.remove(abbreviatedDNF.get(j));
                            }
                        }
                    }
                }
            }
            Set<List<Integer>> setClone = new HashSet<>(clone);
            abbreviatedDNF = new ArrayList<>(setClone);
            if (counter == 0) break;
        }
        while (true);
        return abbreviatedDNF;
    }

    public String getAbbreviatedDNF() {
        List<List<Integer>> listOfAbbreviatedDNF = getListOfAbbreviatedDNF();
        StringBuilder output = new StringBuilder();
        for (List<Integer> conjunction : listOfAbbreviatedDNF) {
            output.append("(");
            for (int j = 0; j < conjunction.size(); ++j) {
                Integer el = conjunction.get(j);
                if (el != -1) output
                        .append(el == 1 ? parameters.get(j) : UnaryOperation.NEGATION.charOfOperation + parameters.get(j))
                        .append(" ").append(BinaryOperation.CONJUNCTION.charOfOperation)
                        .append(" ");
            }
            if (output.length() >= 3) {
                output = new StringBuilder(output.substring(0, output.length() - 3));
            }
            output.append(") ")
                    .append(BinaryOperation.DISJUNCTION.charOfOperation)
                    .append(" ");

        }
        if (output.length() >= 3) {
            output = new StringBuilder(output.substring(0, output.length() - 3));
        }
        return output.toString();
    }

    private List<List<List<Integer>>> getListOfDeadLockedDNF() {
        List<List<List<Integer>>> tupic = new ArrayList<>();
        tupic.add(getListOfAbbreviatedDNF());
        int j = 0;
        while (j != tupic.size()) {
            List<List<Integer>> list = tupic.get(j);
            for (int i = 0; i < list.size(); ++i) {
                List<List<Integer>> copy = new ArrayList<>(list);
                //noinspection SuspiciousListRemoveInLoop
                copy.remove(i);
                if (isDNF(copy)) tupic.add(copy);
            }
            ++j;
        }
        tupic = cleanDeadLockedDNF(tupic);
        return tupic;
    }

    private boolean isDNF(List<List<Integer>> list) {
        for (int[] row : table) {
            if (row[row.length - 1] == 1) {
                int result = 0;
                for (List<Integer> integers : list) {
                    boolean flag = true;
                    for (int j = 0; j < integers.size(); ++j) {
                        if (integers.get(j) != -1) {
                            if (integers.get(j) != row[j]) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag) ++result;
                }
                if (result == 0) return false;
            }
        }
        return true;
    }

    private List<List<List<Integer>>> cleanDeadLockedDNF(List<List<List<Integer>>> list) {
        List<List<List<Integer>>> copy = new ArrayList<>(list);
        List<Integer> removable = new ArrayList<>();
        for (int i = list.size() - 1; i > 0; --i) {
            for (int j = 0; j < i; j++) {
                List<List<Integer>> lst1 = list.get(i);
                List<List<Integer>> lst2 = list.get(j);
                boolean res = true;
                for (List<Integer> vars : lst1) {
                    if (!lst2.contains(vars)) {
                        res = false;
                        break;
                    }
                }
                if (res) {
                    removable.add(j);
                }
            }
        }
        Set<Integer> set = new HashSet<>(removable);
        removable.clear();
        removable.addAll(set);
        Collections.sort(removable);
        Collections.reverse(removable);
        for (Integer index : removable) copy.remove((int) index);
        return copy;
    }

    public String getDeadLockedDNF() {
        StringBuilder output = new StringBuilder();
        for (List<List<Integer>> dnf : getListOfDeadLockedDNF()) {
            for (List<Integer> conjunction : dnf) {
                output.append("(");
                for (int i = 0; i < conjunction.size(); ++i) {
                    if (conjunction.get(i) != -1)
                        output.append(conjunction.get(i) == 1
                                ? parameters.get(i)
                                : UnaryOperation.NEGATION.charOfOperation + parameters.get(i))
                                .append(" ").append(BinaryOperation.CONJUNCTION.charOfOperation)
                                .append(" ");
                }
                if (output.length() >= 3) {
                    output = new StringBuilder(output.substring(0, output.length() - 3));
                }
                output.append(") ").append(BinaryOperation.DISJUNCTION.charOfOperation).append(" ");
            }
            if (output.length() >= 3) {
                output = new StringBuilder(output.substring(0, output.length() - 3));
            }
            output.append("\n");
        }
        if (output.length() >= 1) output = new StringBuilder(output.substring(0, output.length()-1));
        return output.toString();
    }
}
