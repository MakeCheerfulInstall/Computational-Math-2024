package Lab2;

import Lab1.MatrixException;

public class MSysNewton implements IMethodSys {
    @Override
    public void Calculate(ISysFunctions funcs, CalcParams params) {
        double x0 = params.iaX;
        double y0 = params.iaY;
        double x, y;
        var fcs = funcs.getSysFunctions();
        _n_length = fcs.length;
        _matrix = new double[_n_length][_n_length];
        _bVector = new double[_n_length];
        ISysFunction f1 = (ISysFunction) fcs[0];
        ISysFunction f2 = (ISysFunction) fcs[1];
        int count = 0;
        do {
            count++;
            _matrix[0][0] = f1.f1x(x0, y0);
            _matrix[0][1] = f1.f1y(x0, y0);
            _matrix[1][0] = f2.f1x(x0, y0);
            _matrix[1][1] = f2.f1y(x0, y0);
            _bVector[0] = -f1.originalFunc(x0, y0);
            _bVector[1] = -f2.originalFunc(x0, y0);
            double[] result = calcMatrix();
            x = result[0] + x0;
            y = result[1] + y0;
            log(String.format("Xi %f Xi+1 %f |Xi+1 - Xi| %f Yi+1 %f Yi %f |Yi+1 - Yi| %f\n",
                    x, x0, Math.abs(x - x0), y, y0, Math.abs(y - y0)));
            if (Math.abs(x - x0) < params.precision && Math.abs(y - y0) < params.precision) break;
            x0 = x;
            y0 = y;

        } while (count <= 100);
        log("Результат расчета методом Ньютона:\n");
        log(String.format("Количество итераций: %d\n", count));
        log(String.format("Вектор решений: (%f,%f)\n", x, y));
        log(String.format("Вектор погрешностей: (%f,%f)\n", Math.abs(x - x0), Math.abs(y - y0)));
        log("Проверочный расчет уравнений:\n");
        log(String.format("Уравнение %s при x=%f,y=%f результат %f\n", f1.toString(), x, y, f1.originalFunc(x, y)));
        log(String.format("Уравнение %s при x=%f,y=%f результат %f\n", f2.toString(), x, y, f2.originalFunc(x, y)));

    }

    private double[] calcMatrix() {
        runForward();
        return runBackward();
    }

    /**
     * Обратный проход по методу Гаусса
     *
     * @return - вектор решений x
     */
    private double[] runBackward() {
        double[] result = new double[_n_length];
        for (int row = _n_length - 1; row >= 0; row--) {
            result[row] = (_bVector[row] - sumOfRightPart(row, result)) / _matrix[row][row];
        }
        return result;
    }

    /**
     * Вспомогательный метод - рассчитывает при обратном ходе сумму элементов с
     * известными значениями
     *
     * @param row    - строка, для которой производится расчет
     * @param result - вектор решений СЛАУ
     * @return - искомая сумма
     */
    private double sumOfRightPart(int row, double[] result) {
        double sum = 0;
        for (int c = row + 1; c < _n_length; c++) {
            sum += result[c] * _matrix[row][c];
        }
        return sum;
    }

    /**
     * Прямой проход по методу Гаусса
     *
     * @throws MatrixException
     */
    private void runForward() {
        int lMargin = 0;
        // Для каждой строки матрицы
        for (int l = 0; l < _n_length; l++) {
            // Если в текущей позиции строки стоит 0, то поищем замену
            if (_matrix[l][lMargin] == 0) {
                swapLine(l, lMargin);
            }
            // Производим исключение позиции lMargin из следующих строк
            // Для каждой следующей строки
            for (int l2 = l + 1; l2 < _n_length; l2++) {
                // Определяем коэффициент для исключения позиции lMargin
                double factor = 0 - _matrix[l2][lMargin] / _matrix[l][lMargin];
                // Получаем результат умножения текущей верхней строки на коэффициент
                double[] toAdd = multVc(l, factor);
                // Добавляем его к текущей строке
                addToLine(l2, toAdd);
                _bVector[l2] += _bVector[l] * factor;
            }
            lMargin++;
        }
    }

    /**
     * Вспомогательный метод добавляет к строке матрицы значения из расчетного
     * массива
     *
     * @param line
     * @param toAdd
     */
    private void addToLine(int line, double[] toAdd) {
        for (int i = 0; i < _n_length; i++) {
            _matrix[line][i] += toAdd[i];
        }
    }

    /**
     * Ищет строку с ненулевой величиной в позиции col и меняет эту строку местами
     * со строкой line. Если такой строки нет, то выбрасывается ошибка
     *
     * @param line - номе строки
     * @param col  - номер колонки
     * @throws CalcErrorException
     */
    private void swapLine(int line, int col) {
        for (int l = line + 1; l < _n_length; l++) {
            if (_matrix[l][col] != 0) {
                double[] temp = _matrix[line];
                _matrix[line] = _matrix[l];
                _matrix[l] = temp;

                double tb = _bVector[line];
                _bVector[line] = _bVector[l];
                _bVector[l] = tb;
                return;
            }
        }
        throw new CalcErrorException("Ошибка расчета матрицы - Нет строки с ненулевым элементом для перемены строк.");
    }

    /**
     * Метод умножает строку матрицы на указанный коэффициент
     *
     * @param line   - индекс строки матрицы
     * @param factor - коэффициент
     * @return - вектор результат умножения каждого элемента строки на коэффициент
     */
    private double[] multVc(int line, double factor) {
        double[] result = new double[_n_length];
        for (int i = 0; i < _n_length; i++) {
            result[i] = _matrix[line][i] == 0 ? 0 : _matrix[line][i] * factor;
        }
        return result;
    }

    private double[][] _matrix;
    private double[] _bVector;
    private int _n_length;

    @Override
    public void setLogger(ILogger logger) {
        _logger = logger;
    }

    private ILogger _logger;

    private void log(String message) {
        if (_logger != null) _logger.log(message);
    }

    @Override
    public String toString() {
        return "Метод Ньютона";
    }
}
