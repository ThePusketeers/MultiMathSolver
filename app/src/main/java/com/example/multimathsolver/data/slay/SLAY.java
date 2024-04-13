package com.example.multimathsolver.data.slay;

import java.util.Arrays;
import java.util.Scanner;

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
     * Метод для решения системы линейных уравнений. Метод изменяет текущую матрицу коэффициентов (this),
     * но не изменяет матрицу-столбец свободных членов.
     * @param b матрица-столбец свободных членов
     * @return матрица-столбец значений неизвестных
     */
    public SLAY solve(SLAY b) {
        GaussianElimination gaussianElimination = new GaussianElimination(precision);
        SLAY answer = new SLAY(b);
        gaussianElimination.forward(this, answer);

        if (
                this.isZeroRow(this.rowsCount-1) &&
                        Math.abs(answer.array[answer.rowsCount-1][0]) >= precision
        ) {
            System.out.println("СЛАУ не имеет решений");
            return null;
        }

        if (
                this.isZeroRow(this.rowsCount-1) &&
                        Math.abs(answer.array[answer.rowsCount-1][0]) < precision
        ) {
            System.out.println("СЛАУ не имеет бесконечно много решений");
            return null;
        }

        gaussianElimination.backward(this, answer);
        gaussianElimination.mainDiagonalToOne(this, answer);

        return answer;
    }


    /**
     * Метод проверяет, является ли строка нулевой, т.е. в ней только нулевые элементы
     * @param row индекс строки, которую нужно проверить
     * @return true, если в строке только нулевые элементы, и false в противном случае
     */
    private boolean isZeroRow(int row) {
        for(double elem : this.array[row]) {
            if (Math.abs(elem) >= precision) {
                return false;
            }
        }
        return true;
    }


    /**
     * Метод для копирования двумерного массива
     * @param array массив, который нужно скопировать
     * @return массив, которые является копией заданного
     */
    private static double[][] getDeepCopyOfArray(double[][] array) {
        double[][] copy = new double[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = Arrays.copyOf(array[i], array[i].length);
        }

        return copy;
    }


    /**
     * Метод, которые спрашивает у пользователя количество строк матрицы
     * @return количество строк матрицы
     */
    private static int enterRowsCount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите количество строк матрицы:");

        int rowsCount = -1;
        try {
            rowsCount = Integer.parseInt(sc.next());
            if (rowsCount < 1) {
                System.out.println("Некорректный ввод. Количество строк матрицы не может быть меньше 1. " +
                        "Попробуйте снова.");
                return enterRowsCount();
            }
            return rowsCount;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод, попробуйте снова.");
            return enterRowsCount();
        }
    }

    /**
     * Метод, который спрашивает у пользователя количество столбцов матрицы
     * @return количество столбцов матрицы
     */
    private static int enterColsCount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите количество столбцов матрицы:");

        int rowsCount = -1;
        try {
            rowsCount = Integer.parseInt(sc.next());
            if (rowsCount < 1) {
                System.out.println("Некорректный ввод. Количество столбцов матрицы не может быть меньше 1. " +
                        "Попробуйте снова.");
                return enterRowsCount();
            }
            return rowsCount;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод, попробуйте снова.");
            return enterRowsCount();
        }
    }

    /**
     * Метод, который спрашивает у пользователя количество цифр после точки.
     * @return количество цифр после точки
     */
    private static int enterOutputAccuracy() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Какое количество цифр после точки выводить? ");

        int outputAccuracy = -1;
        try {
            outputAccuracy = Integer.parseInt(sc.next());
            if (outputAccuracy < 0) {
                System.out.println("Некорректный ввод. Количество цифр после точки не может быть меньше 0. " +
                        "Попробуйте снова.");
                return enterRowsCount();
            }
            return outputAccuracy;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод, попробуйте снова.");
            return enterRowsCount();
        }
    }

    /**
     * Метод, который спрашивает у пользователя способ ввода матрицы
     * @return способ ввода (1 - поэлементно, 2 - построчно)
     */
    private static int enterType() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Если вы хотите вводить матрицу поэлементно, введите 1. Если хотите вводить матрицу" +
                " построчно, введите 2.");

        int type = -1;
        try {
            type = Integer.parseInt(sc.next());
            if (type != 1 && type != 2) {
                System.out.println("Неизвестный способ ввода. Попробуйте снова");
                return enterType();
            }

            return type;
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод, попробуйте снова.");
            return enterType();
        }
    }

    /**
     * Метод, который считывает матрицу поэлементно
     * @param mat матрица, в которую нужно записать результат
     */
    public static void enterMatrixByElements(SLAY mat) {
        for (int i = 0; i < mat.rowsCount; i++) {
            for (int j = 0; j < mat.colsCount; j++) {
                enterElement(mat, i, j);
            }
        }
    }

    /**
     * Метод спрашивает у пользователя конкретный элемент.
     * @param mat матрица, в которую нужно записать элемент
     * @param row индекс строки элемента, который нужно ввести
     * @param col индекс столбца элемента, который нужно ввести
     */
    private static void enterElement(SLAY mat, int row, int col) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите " + (col + 1) + "-й элемент в " + (row + 1) + "-й строке: ");

        try {
            mat.array[row][col] = Double.parseDouble(sc.next());
        } catch (NumberFormatException e) {
            System.out.print("Некорректный ввод. Попробуйте снова.");
            enterElement(mat, row, col);
        }
    }

//    /**
//     * Метод, который считывает матрицу построчно
//     * @param mat матрица, в которую нужно записать результат
//     */
//    private static void enterMatrixByRows(Matrix mat) {
//        for (int i = 0; i < mat.rowsCount; i++) {
//            enterRow(mat, i);
//        }
//    }

//    /**
//     * Метод спрашивает у пользователя конкретную строку
//     * @param mat матрица, в которую нужно записать строку
//     * @param row индекс строки, которую нужно ввести
//     */
//    private static void enterRow(Matrix mat, int row) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("Введите " + (row + 1) + "-ю строку:");
//        String line = sc.nextLine();
//
//        List<String> data = new ArrayList<>(List.of(line.split(" ")));
//        data.removeAll(Arrays.asList("", null));
//
//        if (data.size() != mat.colsCount) {
//            System.out.print("Некорректное количество элементов. Попробуйте заново. ");
//            enterRow(mat, row);
//            return;
//        }
//
//        for (int i = 0; i < mat.colsCount; i++) {
//            try {
//                mat.array[row][i] = Double.parseDouble(data.get(i));
//            } catch (NumberFormatException e) {
//                System.out.print("Некорректный ввод. Попробуйте заново. ");
//                enterRow(mat, row);
//            }
//        }
//    }

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

    public String getsolutionOfSLAY() {
        return this.toString();
    }
}
