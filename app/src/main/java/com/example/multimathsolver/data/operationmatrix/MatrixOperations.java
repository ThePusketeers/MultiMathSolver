package com.example.multimathsolver.data.operationmatrix;

public class MatrixOperations {
    private double[][] matrix;
    int rows;
    int columns;

    public double[][] getMatrix() {
        return matrix;
    }

    private MatrixOperations(int rows, int columns) {
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

    public MatrixOperations add_or_minus(MatrixOperations other_matrix, char operation) throws Exception {
        if (this.columns == other_matrix.columns && this.rows == other_matrix.rows) {
            MatrixOperations summary_matrix = new MatrixOperations(this.rows, this.columns);

            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    if (operation == '+') {
                        summary_matrix.matrix[i][j] = this.matrix[i][j] + other_matrix.matrix[i][j];
                    } else {
                        summary_matrix.matrix[i][j] = this.matrix[i][j] - other_matrix.matrix[i][j];
                    }
                }
            }
            return summary_matrix;
        } else {
            throw new Exception("Операция невозможна.");
        }
    }

    public MatrixOperations multiplyOnNumber(double number) {
        MatrixOperations multiply_num_matrix = new MatrixOperations(this.rows, this.columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                multiply_num_matrix.matrix[i][j] = matrix[i][j] * number;
            }
        }
        return multiply_num_matrix;
    }

    public MatrixOperations multiplication(MatrixOperations other_matrix) throws Exception {
        MatrixOperations multiplication_matrix;

        if (this.columns == other_matrix.rows) {
            multiplication_matrix = new MatrixOperations(this.columns, other_matrix.rows);

            for (int i = 0; i < this.rows; i++) {
                for (int w = 0; w < other_matrix.columns; w++) {
                    double summary = 0;
                    for (int j = 0; j < other_matrix.rows; j++) {
                        summary = summary + (this.matrix[i][j] * other_matrix.matrix[j][w]);
                    }
                    multiplication_matrix.matrix[i][w] = summary;
                }
            }
            return multiplication_matrix;
        } else if (this.rows == other_matrix.columns) {
            // Если прямое умножение невозможно, то проверяем на возможность умножения в обратном порядке

            multiplication_matrix = new MatrixOperations(this.rows, other_matrix.columns);

            for (int i = 0; i < other_matrix.rows; i++) {
                for (int w = 0; w < this.columns; w++) {
                    double summary = 0;
                    for (int j = 0; j < this.rows; j++) {
                        summary = summary + (other_matrix.matrix[i][j] * this.matrix[j][w]);
                    }
                    multiplication_matrix.matrix[i][w] = summary;
                }
            }
            return multiplication_matrix;
        } else {
            throw new Exception("Операция невозможна.");
        }
    }

    public MatrixOperations raise_to_degree(int degree) throws Exception {
        if (this.rows == this.columns && degree > 0) {
            MatrixOperations degree_matrix = new MatrixOperations(this.rows, this.columns);

            for (int i = 0; i < degree - 1; i++) {
                degree_matrix.matrix = this.multiplication(this).matrix;
            }
            return degree_matrix;
        } else {
            throw new Exception("Операция невозможна.");
        }
    }


    public MatrixOperations gauss() {
        MatrixOperations gauss_matrix = new MatrixOperations(this.rows, this.columns);

        for (int i = 0; i < Math.min(gauss_matrix.rows, gauss_matrix.columns); i++) {
            double maxNumber = Math.abs(gauss_matrix.matrix[i][i]);
            int maxRow = i;
            for (int j = i + 1; j < gauss_matrix.rows; j++) {
                if (Math.abs(gauss_matrix.matrix[j][i]) > maxNumber) {
                    maxNumber = Math.abs(gauss_matrix.matrix[j][i]);
                    maxRow = j;
                }
            }

            double[] temp_array = gauss_matrix.matrix[i];
            gauss_matrix.matrix[i] = gauss_matrix.matrix[maxRow];
            gauss_matrix.matrix[maxRow] = temp_array;

            // В треугольную форму снизу
            for (int k = i + 1; k < gauss_matrix.rows; k++) {
                double coef = -(gauss_matrix.matrix[k][i] / gauss_matrix.matrix[i][i]);
                // Чтоб не было много чисел после точки
                if (coef % 2 != 1 && coef % 2 != 0) {
                    double temp = gauss_matrix.matrix[i][i];
                    for (int j = 0; j < gauss_matrix.columns; j++) {
                        gauss_matrix.matrix[i][j] *= gauss_matrix.matrix[k][i];
                    }
                    for (int j = 0; j < gauss_matrix.columns; j++) {
                        gauss_matrix.matrix[k][j] *= temp;
                    }
                    coef = -(gauss_matrix.matrix[k][i] / gauss_matrix.matrix[i][i]);
                }

                for (int j = i; j < gauss_matrix.columns; j++) {
                    if (i == j) {
                        gauss_matrix.matrix[k][j] = 0;
                    } else {
                        gauss_matrix.matrix[k][j] += coef * gauss_matrix.matrix[i][j];
                    }
                }
            }
        }
        return gauss_matrix;
    }

    public int search_rank() {
        MatrixOperations gauss_matrix = new MatrixOperations(this.gauss().matrix);
        int count = 0;
        for (int i = 0; i < gauss_matrix.rows; i++) {
            boolean flag = false;
            for (int j = 0; j < gauss_matrix.columns; j++) {
                if (gauss_matrix.matrix[i][j] != 0) {
                    flag = true;
                }
            }
            if (flag) {
                count++;
            }
        }
        return gauss_matrix.rows - count;
    }

}

