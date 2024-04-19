package com.example.multimathsolver.domain;


import java.util.Arrays;

public class SLAY {
    /** Точность. Значения меньше этого будут приняты за ноль */
    private final double precision = 0.00000000001;
    private final int outputAccuracy;

    /** Количество строк */
    protected int rowsCount;

    /** Количество столбцов */
    protected int colsCount;

    /** Массив со значениями матрицы */
    protected double[][] array;

    /**
     * Конструктор Matrix
     * @param rowsCount количество строк матрицы
     * @param colsCount количество столбцов матрицы
     * @param array массив элементов
     */
    public SLAY(int rowsCount, int colsCount, double[][] array, int outputAccuracy) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        this.array = array;
        this.outputAccuracy = outputAccuracy;
    }

    /**
     * Конструктор Matrix
     * @param rowsCount количество строк матрицы
     * @param colsCount количество столбцов матрицы
     */
    public SLAY(int rowsCount, int colsCount, int outputAccuracy) {
        this(rowsCount, colsCount, new double[rowsCount][colsCount], outputAccuracy);
    }

    /**
     * Конструктор Matrix
     * @param array массив элементов
     */
    public SLAY(double[][] array, int outputAccuracy) {
        this(array.length, array[0].length, array, outputAccuracy);
    }

    /**
     * Конструктор копирования
     * @param matrix матрица, которую нужно скопировать
     */
    public SLAY(SLAY matrix) {
        this(matrix.rowsCount, matrix.colsCount, getDeepCopyOfArray(matrix.array), matrix.outputAccuracy);
    }

    /**
     * Метод для копирования двумерного массива
     * @param array массив, который нужно скопировать
     * @return массив, которые является копией заданного
     */
    public static double[][] getDeepCopyOfArray(double[][] array) {
        double[][] copy = new double[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = Arrays.copyOf(array[i], array[i].length);
        }

        return copy;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public int getColsCount() {
        return colsCount;
    }

    public double[][] getArray() {
        return array;
    }

    public double getPrecision() {
        return precision;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Матрица ( ").append(this.rowsCount).append(" * ").append(this.colsCount).append(" )\n");
        for (double[] row : this.array) {
            res.append("[ ");
            for (double elem : row) {
                res.append(String.format("%." + this.outputAccuracy + "f", elem)).append(" ");
            }
            res.append("]\n");
        }
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }

}
