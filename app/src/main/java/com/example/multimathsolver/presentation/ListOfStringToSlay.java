package com.example.multimathsolver.presentation;

import java.util.*;
import java.util.regex.Pattern;

public class ListOfStringToSlay {

    public static double[][] parse(List<String> SLAY_ROWS) {
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

    private static Map<Character, Double> getRowMap(String row) {
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

    private static boolean checkRow(String str) {
        Pattern pattern = Pattern.compile("^[a-z0-9+\\-*/=]+$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str).matches();
    }
}

