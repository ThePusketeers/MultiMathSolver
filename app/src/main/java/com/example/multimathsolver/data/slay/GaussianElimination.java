package com.example.multimathsolver.data.slay;

public class GaussianElimination {
    /** Поле точность */
    private final double precision;

    /**
     * Конструктор создание объекта для метода Гаусса
     * @param precision точность, т.е. числа, меньшие этого будут приниматься за ноль
     */
    public GaussianElimination(double precision) {
        this.precision = precision;
    };

    /**
     * Прямой ход метода Гаусса. Метод производит вычисления, изменяя сами объекты
     * @param coeffSLAY матрица коэффициентов
     * @param additionalSLAY матрица свободных членов, если она равна null, то игнорируется
     */
    public void forward(SLAY coeffSLAY, SLAY additionalSLAY) {
        int row = 0; //номер строки элемента, под которым мы обнуляем
        int col = 0; //номер столбца элемента, под которым мы обнуляем

        //нам нужно обнулять до тех пор, пока не упремся с какого-то края
        while (col < coeffSLAY.colsCount && row < coeffSLAY.rowsCount) {
            //Если текущий элемент на главной диагонали равен нулю, его нужно заменить на другой не нулевой
            //элемент под ним (поменять строки местами)
            //Так как мы имеем дело с double, то нельзя сравнивать с нулем с помощью ==, потому что есть
            //погрешность, поэтому мы сравниваем значение по модулю с погрешностью. Если значение меньше,
            //значит, элемент можно считать равным нулю
            if (Math.abs(coeffSLAY.array[row][col]) < precision) {
                int notZeroRow = getFirstNotZeroRowAfter(row, col, coeffSLAY); //ищем первый не нулевой элемент под
                //текущим элементом
                if (notZeroRow == -1) {
                    //Если не нашлось ненулевого элемента, то нам нужно идти дальше, т.к. тут уже все нули. Но нам
                    //нужно двинуться только на следующий столбец, не на строку, т.к. на этой строке мы еще не
                    //образовали "ступеньку"
                    col++;
                    continue;
                }

                //если же нашелся не нулевой элемент, то нужно его поменять местами с текущим (поменять местами
                //строки)
                swapRows(row, notZeroRow, coeffSLAY);
                if (additionalSLAY != null) { //если матрица свободных членов не равна null, то там тоже меняем строки
                    swapRows(row, notZeroRow, additionalSLAY);
                }
            }

            //теперь текущий элемент точно не ноль, поэтому нужно обнулить все элементы под ним
            for (int i = row+1; i < coeffSLAY.rowsCount; i++) {
                //формируем коэффициент, на который нужно умножать строки
                double k = coeffSLAY.array[i][col] / coeffSLAY.array[row][col];

                //из i-ой строки вычитаем текущую строку, умноженную на k
                subtractFromRow(coeffSLAY.array[i], getMultipliedRow(coeffSLAY.array[row], k));
                if (additionalSLAY != null) { //если матрица свободных членов не равна null, проделываем то же самое
                    subtractFromRow(additionalSLAY.array[i], getMultipliedRow(additionalSLAY.array[row], k));
                }
            }

            //теперь мы можем перейти ко следующему элементу
            row++;
            col++;
        }
    }


    /**
     * Обратный ход метода Гаусса. Метод производит вычисления, изменяя сами объекты
     * @param coeffSLAY матрица коэффициентов
     * @param additionalSLAY матрица свободных членов, если она равна null, то игнорируется
     */
    void backward(SLAY coeffSLAY, SLAY additionalSLAY) {
        int row = coeffSLAY.rowsCount - 1;
        int col = coeffSLAY.colsCount - 1;

        while (col >= 0 && row >= 0) {
            if (Math.abs(coeffSLAY.array[row][col]) < precision) {
                int notZeroRow = getFirstNotZeroRowBefore(row, col, coeffSLAY);
                if (notZeroRow == -1) {
                    col--;
                    continue;
                }
                swapRows(row, notZeroRow, coeffSLAY);
                if (additionalSLAY != null) {
                    swapRows(row, notZeroRow, additionalSLAY);
                }
            }

            for (int i = row - 1; i >= 0; i--) {
                double k = coeffSLAY.array[i][col] / coeffSLAY.array[row][col];

                subtractFromRow(coeffSLAY.array[i], getMultipliedRow(coeffSLAY.array[row], k));
                if (additionalSLAY != null) {
                    subtractFromRow(additionalSLAY.array[i], getMultipliedRow(additionalSLAY.array[row], k));
                }
            }

            row--;
            col--;
        }
    }

    /**
     * Метод приводит элементы на главной диагонали к единице, если это возможно (элемент не равен нулю).
     * Метод производит вычисления, изменяя сами объекты
     * @param coeffSLAY матрица коэффициентов
     * @param additionalSLAY матрица свободных членов, если она равна null, то игнорируется
     */
    void mainDiagonalToOne(SLAY coeffSLAY, SLAY additionalSLAY) {
        for (int i = 0; i < Math.min(coeffSLAY.rowsCount, coeffSLAY.colsCount); i++) {
            if (Math.abs(coeffSLAY.array[i][i]) >= precision) {
                double k = 1 / coeffSLAY.array[i][i];
                coeffSLAY.array[i] = getMultipliedRow(coeffSLAY.array[i], k);
                additionalSLAY.array[i] =  getMultipliedRow(additionalSLAY.array[i],k);
            }
        }
    }

    /**
     * Метод возвращает индекс строки первого ненулевого элемента под заданным
     * @param row индекс строки заданного элемента
     * @param col индекс столбца заданного элемента
     * @param matrix матрица, в которой осуществляется поиск
     * @return индекс строки первого ненулевого элемента под заданным
     */
    private int getFirstNotZeroRowAfter(int row, int col, SLAY matrix) {
        for (int i = row+1; i < matrix.rowsCount; i++) {
            if (Math.abs(matrix.array[i][col]) >= precision) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Метод возвращает индекс строки первого ненулевого элемента над заданным
     * @param row индекс строки заданного элемента
     * @param col индекс столбца заданного элемента
     * @param matrix матрица, в которой осуществляется поиск
     * @return индекс строки первого ненулевого элемента над заданным
     */
    private int getFirstNotZeroRowBefore(int row, int col, SLAY matrix) {
        for (int i = row-1; i >= 0; i--) {
            if (Math.abs(matrix.array[i][col]) >= precision) {
                return i;
            }
        }

        return -1;
    }


    /**
     * Метод меняет местами строки в матрице. Метод изменяет текущую матрицу.
     * Метод умножает первую строку на -1, чтобы не ломался поиск определителя.
     * @param row1 индекс первой строки
     * @param row2 индекс второй строки
     * @param matrix матрица, в которой нужно поменять местами строки
     */
    private void swapRows(int row1, int row2, SLAY matrix) {
        matrix.array[row1] = getMultipliedRow(matrix.array[row1], -1);
        double[] temp = matrix.array[row1];
        matrix.array[row1] = matrix.array[row2];
        matrix.array[row2] = temp;
    }

    /**
     * Метод возвращает строку, которая является умножением row на k. Метод не меняет текущую строку
     * @param row строка, которую умножают
     * @param k коэффициент, на который умножают
     * @return новая строка, которая является результатом умножения row на k
     */
    private double[] getMultipliedRow(double[] row, double k) {
        double[] res = new double[row.length];
        for (int i = 0; i < row.length; i++) {
            res[i] = row[i] * k;
        }

        return res;
    }

    /**
     * Метод вычитает из строки rowA строку rowB. Метод изменяет строку rowA
     * @param rowA строка, из которой вычитают
     * @param rowB строка, которую вычитают
     */
    private void subtractFromRow(double[] rowA, double[] rowB) {
        for (int i = 0; i < rowA.length; i++) {
            rowA[i] -= rowB[i];
        }
    }

}

