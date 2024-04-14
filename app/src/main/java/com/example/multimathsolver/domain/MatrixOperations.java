package com.example.multimathsolver.domain;

public class MatrixOperations {
    private double[][] matrix;
    private int rows;
    private int columns;
    private double main_coef;


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

    public void setMain_coef(double main_coef) {
        this.main_coef = main_coef;
    }

    public double getMain_coef() {
        return main_coef;
    }


    public double[][] getMatrix() {
        return matrix;
    }

    public MatrixOperations(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

    public MatrixOperations(double[][] enter_matrix) {
        rows = enter_matrix.length;
        columns = enter_matrix[0].length;
        matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(enter_matrix[i], 0, matrix[i], 0, columns);
        }
    }
}
