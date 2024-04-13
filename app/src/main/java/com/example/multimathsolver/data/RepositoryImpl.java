package com.example.multimathsolver.data;

import com.example.multimathsolver.data.slay.SLAY;
import com.example.multimathsolver.data.booleanalgebra.BinaryOperation;
import com.example.multimathsolver.data.booleanalgebra.HandlerSelector;
import com.example.multimathsolver.data.booleanalgebra.UnaryOperation;
import com.example.multimathsolver.domain.BooleanFunction;
import com.example.multimathsolver.domain.PostClass;
import com.example.multimathsolver.data.booleanalgebra.ExpressionHandler;
import com.example.multimathsolver.domain.IncorrectFunctionInput;
import com.example.multimathsolver.domain.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RepositoryImpl implements Repository {

    @Override
    public BooleanFunction getBooleanFunction(String expression) throws IncorrectFunctionInput {
        ExpressionHandler handler = HandlerSelector.getHandler(expression);
        BooleanFunction function = new BooleanFunction();
        function.setParameters(handler.getExpressionParameters());
        function.setTable(handler.getTableWithValueOfFunction());
        return function;
    }

    @Override
    public String getPerfectDNF(BooleanFunction function) {
        StringBuilder output = new StringBuilder();
        int[][] table = function.getTable();
        for (int[] ints : table) {
            if (ints[table[0].length - 1] == 1) {
                output.append("(");
                for (int j = 0; j < table[0].length - 1; ++j) {
                    if (ints[j] == 0) {
                        output.append(UnaryOperation.NEGATION.charOfOperation);
                    }
                    output.append(function.getParameters().get(j))
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

    @Override
    public String getPerfectCNF(BooleanFunction function) {
        StringBuilder output = new StringBuilder();
        int[][] table = function.getTable();
        for (int[] ints : table) {
            if (ints[table[0].length - 1] == 0) {
                output.append("(");
                for (int j = 0; j < table[0].length - 1; ++j) {
                    if (ints[j] == 1) {
                        output.append(UnaryOperation.NEGATION.charOfOperation);
                    }
                    output.append(function.getParameters().get(j))
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

    private List<List<Integer>> getListOfSDNF(BooleanFunction function) {
        List<List<Integer>> listOfSDNF = new ArrayList<>();
        for (int[] row : function.getTable()) {
            if (row[row.length - 1] == 1) {
                List<Integer> listOfValuesOfParameters = new ArrayList<>();
                for (int j = 0; j < row.length - 1; ++j) {
                    listOfValuesOfParameters.add(row[j]);
                }
                listOfSDNF.add(listOfValuesOfParameters);
            }
        }
        return listOfSDNF;
    }

    private List<List<Integer>> getListOfAbbreviatedDNF(BooleanFunction function) {
        List<List<Integer>> abbreviatedDNF = getListOfSDNF(function);
        do {
            List<List<Integer>> clone = new ArrayList<>(abbreviatedDNF);
            int counter = 0;
            for (int k = 0; k < function.getParameters().size(); ++k) {
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

    @Override
    public String getAbbreviatedDNF(BooleanFunction function) {
        List<String> parameters = function.getParameters();
        List<List<Integer>> listOfAbbreviatedDNF = getListOfAbbreviatedDNF(function);
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

    private List<List<List<Integer>>> getListOfDeadLockedDNF(BooleanFunction function) {
        List<List<List<Integer>>> tupic = new ArrayList<>();
        tupic.add(getListOfAbbreviatedDNF(function));
        int j = 0;
        while (j != tupic.size()) {
            List<List<Integer>> list = tupic.get(j);
            for (int i = 0; i < list.size(); ++i) {
                List<List<Integer>> copy = new ArrayList<>(list);
                //noinspection SuspiciousListRemoveInLoop
                copy.remove(i);
                if (isDNF(copy, function)) tupic.add(copy);
            }
            ++j;
        }
        tupic = cleanDeadLockedDNF(tupic);
        return tupic;
    }
    public String solutionOfSLAY(SLAY system){
        return system.getsolutionOfSLAY();
    }

    private boolean isDNF(List<List<Integer>> list, BooleanFunction function) {
        for (int[] row : function.getTable()) {
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

    @Override
    public String getDeadLockedDNF(BooleanFunction function) {
        StringBuilder output = new StringBuilder();
        List<String> parameters = function.getParameters();
        for (List<List<Integer>> dnf : getListOfDeadLockedDNF(function)) {
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

    private List<List<List<Integer>>> getListOfMinimalDNF(BooleanFunction function) {
        int minimum = Integer.MAX_VALUE;
        List<List<List<Integer>>> listOfMinimalDNF = new ArrayList<>();
        List<List<List<Integer>>> deadLockedDNF = getListOfDeadLockedDNF(function);
        for (List<List<Integer>> dnf : deadLockedDNF) {
            int counter = 0;
            for (List<Integer> conjunction : dnf) {
                for (Integer literal : conjunction) {
                    if (literal != -1) ++counter;
                }
            }
            minimum = Math.min(counter, minimum);
        }
        for (List<List<Integer>> dnf : deadLockedDNF) {
            int counter = 0;
            for (List<Integer> conjunction : dnf) {
                for (Integer literal : conjunction) {
                    if (literal != -1) ++counter;
                }
            }
            if (counter == minimum) listOfMinimalDNF.add(dnf);
        }
        return listOfMinimalDNF;
    }
    @Override
    public String getMinimalDNF(BooleanFunction function) {
        StringBuilder output = new StringBuilder();
        List<String> parameters = function.getParameters();
        for (List<List<Integer>> dnf : getListOfMinimalDNF(function)) {
            for (List<Integer> conjunction : dnf) {
                output.append("(");
                for (int i = 0; i < conjunction.size(); ++i) {
                    if (conjunction.get(i) != -1)
                        output.append((conjunction.get(i) == 1 ? parameters.get(i) :
                                        UnaryOperation.NEGATION.charOfOperation + parameters.get(i)))
                                .append(" ").append(BinaryOperation.CONJUNCTION.charOfOperation)
                                .append(" ");
                }
                if (output.length() >= 3) output = new StringBuilder(output.substring(0, output.length() - 3));
                output.append(") ").append(BinaryOperation.DISJUNCTION.charOfOperation).append(" ");
            }
            if (output.length() >= 3) output = new StringBuilder(output.substring(0, output.length() - 3));
            output.append("\n");
        }
        if (output.length() >= 1) output = new StringBuilder(output.substring(0, output.length() - 1));
        return output.toString();
    }

    private List<Integer> mergeLists(List<Integer> arr1, List<Integer> arr2) {
        List<Integer> res = new ArrayList<>(arr1);
        for (int i = 0; i < arr2.size(); ++i) {
            res.add((arr1.get(i) + arr2.get(i)) % 2);
        }
        return res;
    }

    @Override
    public String getPolynomial(BooleanFunction function) {
        StringBuilder output = new StringBuilder();
        int[][] table = function.getTable();
        List<String> parameters = function.getParameters();
        List<List<Integer>> entry = new ArrayList<>();
        for (int[] ints : table) {
            List<Integer> valueOfFunction = new ArrayList<>();
            valueOfFunction.add(ints[parameters.size()]);
            entry.add(valueOfFunction);
        }
        while (entry.size() != 1) {
            List<List<Integer>> clone = new ArrayList<>();
            for (int i = 0; i < entry.size() - 1; i += 2) {
                clone.add(mergeLists(entry.get(i), entry.get(i + 1)));
            }
            entry = clone;
        }
        List<Integer> result = entry.get(0);
        for (int i = table.length - 1; i > 0; --i) {
            if (result.get(i) == 1) {
                for (int j = 0; j < table[i].length - 1; ++j) {
                    if (table[i][j] == 1) output.append(parameters.get(j));
                }
                output.append(" ").append(BinaryOperation.XOR.charOfOperation).append(" ");
            }
        }
        if (result.get(0) == 1) output.append(1);
        else {
            if (output.length() >= 3) output = new StringBuilder(output.substring(0, output.length() - 3));
        }
        return output.toString();
    }

    private boolean belongsToT0(BooleanFunction function) {
        int[][] table = function.getTable();
        List<String> parameters = function.getParameters();
        return table[0][parameters.size()] == 0;
    }

    private boolean belongsToT1(BooleanFunction function) {
        int[][] table = function.getTable();
        List<String> parameters = function.getParameters();
        return table[table.length-1][parameters.size()] == 1;
    }

    private boolean belongsToL(BooleanFunction function) {
        List<String> parameters = function.getParameters();
        for (String string : getPolynomial(function).split(" ")) {
            if (!string.equals(BinaryOperation.XOR.charOfOperation)) {
                if (!parameters.contains(string)) return false;
            }
        }
        return true;
    }

    private boolean belongsToS(BooleanFunction function) {
        int[][] table = function.getTable();
        List<String> parameters = function.getParameters();
        for (int i = 0; i < table.length / 2; ++i) {
            if (table[i][parameters.size()] == table[table.length - 1 - i][parameters.size()]) return false;
        }
        return true;
    }

    private boolean belongsToM(int[] arr) {
        if (arr.length <= 1) return true;
        for (int i = 0; i < arr.length / 2; ++i) {
            if (arr[i] > arr[i + (arr.length / 2)]) return false;
        }
        return belongsToM(Arrays.copyOfRange(arr, 0, arr.length / 2)) && belongsToM(Arrays.copyOfRange(arr, arr.length / 2, arr.length));
    }

    @Override
    public Map<PostClass, Boolean> getBelongingToPostClasses(BooleanFunction function) {
        int[][] table = function.getTable();
        List<String> parameters = function.getParameters();
        int[] functionValue = new int[table.length];
        for (int i = 0 ; i < table.length; ++i) functionValue[i] = table[i][parameters.size()];
        Map<PostClass, Boolean> map = new HashMap<>();
        map.put(PostClass.T0, belongsToT0(function));
        map.put(PostClass.T1, belongsToT1(function));
        map.put(PostClass.L, belongsToL(function));
        map.put(PostClass.M, belongsToM(functionValue));
        map.put(PostClass.S, belongsToS(function));
        return map;
    }


}
