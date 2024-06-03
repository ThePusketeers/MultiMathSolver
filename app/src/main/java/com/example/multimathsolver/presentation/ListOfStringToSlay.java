package com.example.multimathsolver.presentation;

import com.example.multimathsolver.domain.SLAY;

import java.util.*;
import java.util.regex.Pattern;

public class ListOfStringToSlay {
    private SLAY coeffSLAY;
    private SLAY additionalSLAY;

    public void doSLAY(List<String> SLAY_ROWS, int outputAccuracy) {
        double[][] matrix = parse(SLAY_ROWS);
        double[][] coeffMatrix = new double[matrix.length][matrix[0].length-1];
        double[][] additionalMatrix = new double[matrix.length][1];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if (j != matrix[0].length-1)
                    coeffMatrix[i][j] = matrix[i][j];
                else
                    additionalMatrix[i][0] = matrix[i][j];
            }
        }
        coeffSLAY = new SLAY(coeffMatrix, outputAccuracy);
        additionalSLAY = new SLAY(additionalMatrix, outputAccuracy);
    }

    public SLAY getCoeffSLAY() {
        return coeffSLAY;
    }

    public SLAY getAdditionalSLAY() {
        return additionalSLAY;
    }

    private double[][] parse(List<String> SLAY_ROWS) {
        List<Character> listOfArguments = new ArrayList<>();
        List<Map<Character, Double>> listOfMap = new ArrayList<>();
        for (String row : SLAY_ROWS) {
            if (!checkRow(row))
                return null;
            else {
                listOfMap.add(getRowMap(row));
                for (char c : getRowMap(row).keySet())
                    if ((!listOfArguments.contains(c)) && (c != 'R'))
                        listOfArguments.add(c);
            }
        }
        listOfArguments.add('R');
        double[][] matrix = new double[SLAY_ROWS.size()][listOfArguments.size()];
        for (int i = 0; i < SLAY_ROWS.size(); ++i) {
            for (int j = 0; j < listOfArguments.size(); ++j) {
                if (listOfMap.get(i).get(listOfArguments.get(j)) != null)
                    matrix[i][j] = listOfMap.get(i).get(listOfArguments.get(j));
                else
                    matrix[i][j] = 0;
            }
        }
        return matrix;

    }

    private Map<Character, Double> getRowMap(String row) {
        Map<Character, Double> rowMap = new TreeMap<>();
        StringBuilder tempArgumentValue = new StringBuilder();
        for (int i = 0; i < row.length(); ++i) {
            char c = row.charAt(i);
            if (('a' <= c) && (c <= 'z')) {
                if (!rowMap.containsKey(c)) {
                    if ((tempArgumentValue.toString().equals("+")) || (tempArgumentValue.toString().equals("+"))
                            || (tempArgumentValue.length() == 0)) {
                        tempArgumentValue.append("1");
                        rowMap.put(c, Double.parseDouble(tempArgumentValue.toString()));
                    } else
                        rowMap.put(c, Double.parseDouble(tempArgumentValue.toString()));
                    tempArgumentValue = new StringBuilder();
                } else {
                    if ((tempArgumentValue.toString().equals("+")) || (tempArgumentValue.toString().equals("+"))
                            || (tempArgumentValue.length() == 0)) {
                        tempArgumentValue.append("1");
                        rowMap.put(c, Double.parseDouble(tempArgumentValue.toString()));
                    }
                    else
                        rowMap.put(c, rowMap.get(c)+Double.parseDouble(tempArgumentValue.toString()));
                    tempArgumentValue = new StringBuilder();
                }
            } else if (c == '=') {
                tempArgumentValue = new StringBuilder();
            } else {
                tempArgumentValue.append(c);
            }
        }
        rowMap.put('R', Double.parseDouble(tempArgumentValue.toString()));
        return rowMap;
    }

    private boolean checkRow(String str) {
        Pattern pattern = Pattern.compile("^[a-z0-9+\\-*/=]+$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str).matches();
    }
}

