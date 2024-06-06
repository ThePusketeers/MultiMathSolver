package com.example.multimathsolver.domain;

public class MatrixOperations {
    private double[][] matrix;
    private int rows;
    private int columns;
    private double mainCoef;

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setMainCoef(double mainCoef) {
        this.mainCoef = mainCoef;
    }

    public double getMainCoef() {
        return mainCoef;
    }


    public double[][] getMatrix() {
        return matrix;
    }

    public MatrixOperations(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

    public MatrixOperations(double[][] enterMatrix) {
        rows = enterMatrix.length;
        columns = enterMatrix[0].length;
        matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(enterMatrix[i], 0, matrix[i], 0, columns);
        }
    }
}
