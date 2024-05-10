package com.example.multimathsolver.data;

import com.example.multimathsolver.data.mathematicalAnalysis.ApiFactory;
import com.example.multimathsolver.data.mathematicalAnalysis.SequenceLimit;
import com.example.multimathsolver.domain.models.LimitResponse;
import com.example.multimathsolver.data.mathematicalAnalysis.FunctionLimit;
import com.example.multimathsolver.data.slay.GaussianElimination;
import com.example.multimathsolver.domain.SLAY;
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

import io.reactivex.rxjava3.core.Single;

public class RepositoryImpl implements Repository {
    @Override
    public Single<LimitResponse> getFunctionLimit(String function, Double strivesFor) {
        String functionCall = "";
        if (strivesFor == Double.POSITIVE_INFINITY)
            functionCall = "lim x->inf (" + function + ")";
        else if (strivesFor == Double.NEGATIVE_INFINITY)
            functionCall = "lim x->(-inf) (" + function + ")";
        else
            functionCall = "lim x->" + strivesFor + "(" + function + ")";
        return ApiFactory.apiService.getLimitResponse(functionCall);
    }


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
    public String getSolutionOfSLAY (SLAY coeffSLAY, SLAY additionalSLAY){
        return solve(coeffSLAY, additionalSLAY).toString();
    }

    /**
     * Метод для решения системы линейных уравнений.
     * @param coeffSLAY матрица коэфициентов
     * @param additionalSLAY матрица-столбец свободных членов
     * @return матрица-столбец значений неизвестных
     */

    public double[][] solve(SLAY coeffSLAY, SLAY additionalSLAY){
        GaussianElimination gaussianElimination = new GaussianElimination(coeffSLAY.getPrecision());
        double[][] array = coeffSLAY.getArray();
        gaussianElimination.forward(coeffSLAY, additionalSLAY);

        if (
                this.isZeroRow(coeffSLAY.getRowsCount()-1, coeffSLAY) &&
                        Math.abs(array[additionalSLAY.getRowsCount()-1][0]) >= coeffSLAY.getPrecision()
        ) {
            System.out.println("СЛАУ не имеет решений");
            return null;
        }

        if (
                this.isZeroRow(coeffSLAY.getRowsCount()-1,coeffSLAY) &&
                        Math.abs(array[additionalSLAY.getRowsCount()-1][0]) < coeffSLAY.getPrecision()
        ) {
            System.out.println("СЛАУ не имеет бесконечно много решений");
            return null;
        }

        gaussianElimination.backward(coeffSLAY, additionalSLAY);
        gaussianElimination.mainDiagonalToOne(coeffSLAY, additionalSLAY);

        return additionalSLAY.getArray();
    }

    @Override
    public MatrixOperations addOrMinus(MatrixOperations mainMatrix, MatrixOperations otherMatrix, char operation) throws IncorrectMatrixSize {
        if (mainMatrix.getColumns() == otherMatrix.getColumns() && mainMatrix.getRows() == otherMatrix.getRows()) {
            MatrixOperations summaryMatrix = new MatrixOperations(mainMatrix.getRows(), mainMatrix.getColumns());
            double [][] summaryMatrixTable = summaryMatrix.getMatrix();
            double [][] mainMatrixTable = mainMatrix.getMatrix();
            double [][] otherMatrixTable = otherMatrix.getMatrix();

            for (int i = 0; i < mainMatrix.getRows(); i++) {
                for (int j = 0; j < mainMatrix.getColumns(); j++) {
                    if (operation == '+') {
                        summaryMatrixTable[i][j] = mainMatrixTable[i][j] + otherMatrixTable[i][j];
                    } else {
                        summaryMatrixTable[i][j] = mainMatrixTable[i][j] - otherMatrixTable[i][j];
                    }
                }
            }

            return summaryMatrix;
        } else {
            if (operation == '+') {
                throw new IncorrectMatrixSize("Сложение матриц невозможно. Матрицы должны быть одинакового размера!");
            }
            throw new IncorrectMatrixSize("Вычитание матриц невозможно. Матрицы должны быть одинакового размера!");
        }
    }

    @Override
    public MatrixOperations multiplyOnNumber(MatrixOperations mainMatrix, double number) {
        MatrixOperations multiplyNumMatrix = new MatrixOperations(mainMatrix.getRows(), mainMatrix.getColumns());

        double [][] mainMatrixTable = mainMatrix.getMatrix();
        double [][] multiplyNumMatrixTable = multiplyNumMatrix.getMatrix();

        for (int i = 0; i < mainMatrix.getRows(); i++) {
            for (int j = 0; j < mainMatrix.getColumns(); j++) {
                multiplyNumMatrixTable[i][j] = mainMatrixTable[i][j] * number;
            }
        }

        return multiplyNumMatrix;
    }

    @Override
    public MatrixOperations multiplication(MatrixOperations mainMatrix, MatrixOperations otherMatrix) throws IncorrectMatrixSize {
        MatrixOperations multiplicationMatrix;

        if (mainMatrix.getColumns() == otherMatrix.getRows()) {
            multiplicationMatrix = new MatrixOperations(mainMatrix.getRows(), otherMatrix.getColumns());

            double [][] multiplicationMatrixTable = multiplicationMatrix.getMatrix();
            double [][] mainMatrixTable = mainMatrix.getMatrix();
            double [][] otherMatrixTable = otherMatrix.getMatrix();

            for (int i = 0; i < mainMatrix.getRows(); i++) {
                for (int w = 0; w < otherMatrix.getColumns(); w++) {
                    double summary = 0;
                    for (int j = 0; j < otherMatrix.getRows(); j++) {
                        summary = summary + (mainMatrixTable[i][j] * otherMatrixTable[j][w]);
                    }
                    multiplicationMatrixTable[i][w] = summary;
                }
            }
            return multiplicationMatrix;
        }
        else {
            throw new IncorrectMatrixSize("Умножение матриц невозможно. Количество столбцов в первой матрице не равно количеству строк во второй матрице!");
        }
    }

    @Override
    public MatrixOperations raiseToDegree(MatrixOperations mainMatrix, int degree) throws IncorrectMatrixSize {
        if (mainMatrix.getRows() == mainMatrix.getColumns() && degree > 0) {
            MatrixOperations degreeMatrix = new MatrixOperations(mainMatrix.getRows(), mainMatrix.getColumns());


            for (int i = 0; i < degree - 1; i++) {
                degreeMatrix.setMatrix(this.multiplication(mainMatrix, mainMatrix).getMatrix());
            }

            return degreeMatrix;
        } else {
            throw new IncorrectMatrixSize("Возведение в степень невозможно. Матрица должна быть квадратной!");
        }
    }

    private MatrixOperations gauss(MatrixOperations mainMatrix) {
        MatrixOperations gaussMatrix = new MatrixOperations(mainMatrix.getMatrix());
        double multDeter = 1;
        gaussMatrix.setMainCoef(1);
        double [][] gaussMatrixTable = gaussMatrix.getMatrix();
        for (int i = 0; i < Math.min(gaussMatrix.getRows(), gaussMatrix.getColumns()); i++) {
            double maxNumber = Math.abs(gaussMatrixTable[i][i]);
            int maxRow = i;
            for (int j = i + 1; j < gaussMatrix.getRows(); j++) {
                if (Math.abs(gaussMatrixTable[j][i]) > maxNumber) {
                    maxNumber = Math.abs(gaussMatrixTable[j][i]);
                    maxRow = j;
                }
            }

            if (i != maxRow) {
                double[] tempArray = gaussMatrixTable[i];
                gaussMatrixTable[i] = gaussMatrixTable[maxRow];
                gaussMatrixTable[maxRow] = tempArray;

                multDeter *= -1;
            }

            // В треугольную форму снизу
            for (int k = i + 1; k < gaussMatrix.getRows(); k++) {
                double coef = -(gaussMatrixTable[k][i] / gaussMatrixTable[i][i]);
                // Чтоб не было много чисел после точки
                if (coef % 2 != 1 && coef % 2 != 0) {
                    double temp = gaussMatrixTable[i][i];
                    for (int j = 0; j < gaussMatrix.getColumns(); j++) {
                        gaussMatrixTable[i][j] *= gaussMatrixTable[k][i];
                    }

                    multDeter *= (gaussMatrixTable[i][i]);

                    for (int j = 0; j < gaussMatrix.getColumns(); j++) {
                        gaussMatrixTable[k][j] *= temp;
                    }
                    coef = -(gaussMatrixTable[k][i] / gaussMatrixTable[i][i]);
                }

                if (Double.isNaN(coef)) {
                    gaussMatrix.setMainCoef(1);
                    return gaussMatrix;
                }

                for (int j = i; j < gaussMatrix.getColumns(); j++) {
                    if (i == j) {
                        gaussMatrixTable[k][j] = 0;
                    } else {
                        gaussMatrixTable[k][j] += coef * gaussMatrixTable[i][j];
                    }
                }
            }
        }

        gaussMatrix.setMainCoef(multDeter);

        return gaussMatrix;
    }

    @Override
    public int searchRank(MatrixOperations mainMatrix) {
        MatrixOperations gaussMatrix = this.gauss(mainMatrix);
        double [][] gaussMatrixTable = gaussMatrix.getMatrix();
        int count = 0;
        for (int i = 0; i < gaussMatrix.getRows(); i++) {
            boolean flag = false;
            for (int j = 0; j < gaussMatrix.getColumns(); j++) {
                if (gaussMatrixTable[i][j] != 0) {
                    flag = true;
                }
            }
            if (!flag) {
                count++;
            }
        }
        return gaussMatrix.getRows() - count;
    }

    @Override
    public double searchDeterminant(MatrixOperations mainMatrix) throws IncorrectMatrixSize {
        if (mainMatrix.getColumns() == mainMatrix.getRows()) {
            MatrixOperations gaussMatrix = this.gauss(mainMatrix);

            double[][] gaussMatrixTable = gaussMatrix.getMatrix();

            double multiply = 1;

            for (int i = 0; i < gaussMatrix.getColumns(); i++) {
                multiply *= gaussMatrixTable[i][i];
            }

            return multiply / (gaussMatrix.getMainCoef() != 0 ? gaussMatrix.getMainCoef() : 1);
        } else {
            throw new IncorrectMatrixSize("Не получится найти определитель, матрица должна быть квадратной!");
        }
    }

    /**
     * Метод проверяет, является ли строка нулевой, т.е. в ней только нулевые элементы
     * @param row индекс строки, которую нужно проверить
     * @return true, если в строке только нулевые элементы, и false в противном случае
     */
    private boolean isZeroRow(int row, SLAY b) {
        double[][] array = b.getArray();
        for(double elem : array[row]  ) {
            if (Math.abs(elem) >= b.getPrecision()) {
                return false;
            }
        }
        return true;
    }
}