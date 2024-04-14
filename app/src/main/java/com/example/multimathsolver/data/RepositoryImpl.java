package com.example.multimathsolver.data;

import com.example.multimathsolver.domain.IncorrectMatrixSize;
import com.example.multimathsolver.domain.MatrixOperations;
import com.example.multimathsolver.domain.Repository;

public class RepositoryImpl implements Repository {

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
    }
}
