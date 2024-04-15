package com.example.multimathsolver.data;

import com.example.multimathsolver.data.booleanalgebra.BinaryOperation;
import com.example.multimathsolver.data.booleanalgebra.HandlerSelector;
import com.example.multimathsolver.data.booleanalgebra.UnaryOperation;
import com.example.multimathsolver.domain.BooleanFunction;
import com.example.multimathsolver.domain.PostClass;
import com.example.multimathsolver.data.booleanalgebra.ExpressionHandler;
import com.example.multimathsolver.domain.IncorrectFunctionInput;
import com.example.multimathsolver.domain.IncorrectMatrixSize;
import com.example.multimathsolver.domain.MatrixOperations;
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
    public MatrixOperations add_or_minus(MatrixOperations main_matrix, MatrixOperations other_matrix, char operation) throws IncorrectMatrixSize {
        if (main_matrix.getColumns() == other_matrix.getColumns() && main_matrix.getRows() == other_matrix.getRows()) {
            MatrixOperations summary_matrix = new MatrixOperations(main_matrix.getRows(), main_matrix.getColumns());
            double [][] summary_matrix_table = summary_matrix.getMatrix();
            double [][] main_matrix_table = main_matrix.getMatrix();
            double [][] other_matrix_table = other_matrix.getMatrix();

            for (int i = 0; i < main_matrix.getRows(); i++) {
                for (int j = 0; j < main_matrix.getColumns(); j++) {
                    if (operation == '+') {
                        summary_matrix_table[i][j] = main_matrix_table[i][j] + other_matrix_table[i][j];
                    } else {
                        summary_matrix_table[i][j] = main_matrix_table[i][j] - other_matrix_table[i][j];
                    }
                }
            }

            return summary_matrix;
        } else {
            if (operation == '+') {
                throw new IncorrectMatrixSize("Сложение матриц невозможно. Матрицы должны быть одинакового размера!");
            }
            throw new IncorrectMatrixSize("Вычитание матриц невозможно. Матрицы должны быть одинакового размера!");
        }
    }

    public MatrixOperations multiplyOnNumber(MatrixOperations main_matrix, double number) {
        MatrixOperations multiply_num_matrix = new MatrixOperations(main_matrix.getRows(), main_matrix.getColumns());

        double [][] main_matrix_table = main_matrix.getMatrix();
        double [][] multiply_num_matrix_table = multiply_num_matrix.getMatrix();

        for (int i = 0; i < main_matrix.getRows(); i++) {
            for (int j = 0; j < main_matrix.getColumns(); j++) {
                multiply_num_matrix_table[i][j] = main_matrix_table[i][j] * number;
            }
        }

        return multiply_num_matrix;
    }

    public MatrixOperations multiplication(MatrixOperations main_matrix, MatrixOperations other_matrix) throws IncorrectMatrixSize {
        MatrixOperations multiplication_matrix;

        if (main_matrix.getColumns() == other_matrix.getRows()) {
            multiplication_matrix = new MatrixOperations(main_matrix.getRows(), other_matrix.getColumns());

            double [][] multiplication_matrix_table = multiplication_matrix.getMatrix();
            double [][] main_matrix_table = main_matrix.getMatrix();
            double [][] other_matrix_table = other_matrix.getMatrix();

            for (int i = 0; i < main_matrix.getRows(); i++) {
                for (int w = 0; w < other_matrix.getColumns(); w++) {
                    double summary = 0;
                    for (int j = 0; j < other_matrix.getRows(); j++) {
                        summary = summary + (main_matrix_table[i][j] * other_matrix_table[j][w]);
                    }
                    multiplication_matrix_table[i][w] = summary;
                }
            }
            return multiplication_matrix;
        }
//        else if (this.rows == other_matrix.columns) {
//            Если прямое умножение невозможно, то проверяем на возможность умножения в обратном порядке
//
//            multiplication_matrix = new MatrixOperations(this.rows, other_matrix.columns);
//
//            for (int i = 0; i < other_matrix.rows; i++) {
//                for (int w = 0; w < this.columns; w++) {
//                    double summary = 0;
//                    for (int j = 0; j < this.rows; j++) {
//                        summary = summary + (other_matrix.matrix[i][j] * this.matrix[j][w]);
//                    }
//                    multiplication_matrix.matrix[i][w] = summary;
//                }
//            }
//            return multiplication_matrix;
//        }
        else {
            throw new IncorrectMatrixSize("Умножение матриц невозможно. Количество столбцов в первой матрице не равно количеству строк во второй матрице!");
        }
    }

    public MatrixOperations raise_to_degree(MatrixOperations main_matrix, int degree) throws IncorrectMatrixSize {
        if (main_matrix.getRows() == main_matrix.getColumns() && degree > 0) {
            MatrixOperations degree_matrix = new MatrixOperations(main_matrix.getRows(), main_matrix.getColumns());


            for (int i = 0; i < degree - 1; i++) {
                degree_matrix.setMatrix(this.multiplication(main_matrix, main_matrix).getMatrix());
            }

            return degree_matrix;
        } else {
            throw new IncorrectMatrixSize("Возведение в степень невозможно. Матрица должна быть квадратной!");
        }
    }

    private MatrixOperations gauss(MatrixOperations main_matrix) {
        MatrixOperations gauss_matrix = new MatrixOperations(main_matrix.getMatrix());
        double mult_deter = 1;
        gauss_matrix.setMain_coef(1);
        double [][] gauss_matrix_table = gauss_matrix.getMatrix();
        for (int i = 0; i < Math.min(gauss_matrix.getRows(), gauss_matrix.getColumns()); i++) {
            double maxNumber = Math.abs(gauss_matrix_table[i][i]);
            int maxRow = i;
            for (int j = i + 1; j < gauss_matrix.getRows(); j++) {
                if (Math.abs(gauss_matrix_table[j][i]) > maxNumber) {
                    maxNumber = Math.abs(gauss_matrix_table[j][i]);
                    maxRow = j;
                }
            }

            if (i != maxRow) {
                double[] temp_array = gauss_matrix_table[i];
                gauss_matrix_table[i] = gauss_matrix_table[maxRow];
                gauss_matrix_table[maxRow] = temp_array;

                mult_deter *= -1;
            }




            // В треугольную форму снизу
            for (int k = i + 1; k < gauss_matrix.getRows(); k++) {
                double coef = -(gauss_matrix_table[k][i] / gauss_matrix_table[i][i]);
                // Чтоб не было много чисел после точки
                if (coef % 2 != 1 && coef % 2 != 0) {
                    double temp = gauss_matrix_table[i][i];
                    for (int j = 0; j < gauss_matrix.getColumns(); j++) {
                        gauss_matrix_table[i][j] *= gauss_matrix_table[k][i];
                    }

                    mult_deter *= (gauss_matrix_table[i][i]);

                    for (int j = 0; j < gauss_matrix.getColumns(); j++) {
                        gauss_matrix_table[k][j] *= temp;
                    }
                    coef = -(gauss_matrix_table[k][i] / gauss_matrix_table[i][i]);
                }

                if (Double.isNaN(coef)) {
                    gauss_matrix.setMain_coef(1);
                    return gauss_matrix;
                }

                for (int j = i; j < gauss_matrix.getColumns(); j++) {
                    if (i == j) {
                        gauss_matrix_table[k][j] = 0;
                    } else {
                        gauss_matrix_table[k][j] += coef * gauss_matrix_table[i][j];
                    }
                }
            }
        }

        gauss_matrix.setMain_coef(mult_deter);

        return gauss_matrix;
    }

    public int search_rank(MatrixOperations main_matrix) {
        MatrixOperations gauss_matrix = this.gauss(main_matrix);
        double [][] gauss_matrix_table = gauss_matrix.getMatrix();
        int count = 0;
        for (int i = 0; i < gauss_matrix.getRows(); i++) {
            boolean flag = false;
            for (int j = 0; j < gauss_matrix.getColumns(); j++) {
                if (gauss_matrix_table[i][j] != 0) {
                    flag = true;
                }
            }
            if (!flag) {
                count++;
            }
        }
        return gauss_matrix.getRows() - count;
    }

    public double search_determinant(MatrixOperations main_matrix) throws IncorrectMatrixSize {
        if (main_matrix.getColumns() == main_matrix.getRows()) {
            MatrixOperations gauss_matrix = this.gauss(main_matrix);

            double [][] gauss_matrix_table = gauss_matrix.getMatrix();

            double multiply = 1;

            for (int i = 0; i < gauss_matrix.getColumns(); i++) {
                multiply *= gauss_matrix_table[i][i];
            }

            return multiply / (gauss_matrix.getMain_coef() != 0 ? gauss_matrix.getMain_coef() : 1);
        } else {
            throw new IncorrectMatrixSize("Не получится найти определитель, матрица должна быть квадратной!");
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
