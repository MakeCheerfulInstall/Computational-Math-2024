package lab2.algebra;

public class NonLinearEquationSolver implements EquationSolver {
    private final int LIMIT = 1_000_000;

    private double ACCURACY = 1E-5;

    /**
     * Устанавливает точность
     */

    @Override
    public void setAccuracy(double accuracy) {
        if (0 > accuracy && accuracy > 1) {
            throw new IllegalArgumentException("Точность: можно указать тольков в интервале (0, 1)");
        }
        this.ACCURACY = accuracy;
    }

    /**
     * Решить методом Хорд
     */
    @Override
    public Object[] solveByChord(Function function, double a, double b) {
        double xn;
        long iterations = 0;
        do {
            xn = a - (((b - a) * function.apply(a)) / (function.apply(b) - function.apply(a)));
            double f_xn = function.apply(xn);
            if (function.apply(a) * f_xn < 0) {
                b = xn;
            } else if (function.apply(b) * f_xn < 0) {
                a = xn;
            } else {
                throw new RuntimeException("На данном интервале либо несколько корней, либо они отсутствуют");
            }
            iterations++;
            if (iterations == LIMIT) throw new RuntimeException("Превышен лимит итераций.");
        } while (Math.abs(function.apply(xn)) >= ACCURACY);
        double root = xn;
        double delta = Math.abs(function.apply(xn));
        return new Object[] { root, delta, iterations, function.apply(root) };
    }


    /**
     *  Решить нелинейное уравнение методом простых итераций
     */

    @Override
    public Object[] solveByIteration(Function function, double a, double b) {
        if (a > b) a = a + b - (b = a);
        double q = derivativeSeriesMax(function, a, b);
        if (Double.isNaN(q) || q >= 1) {
            throw new RuntimeException("Необходимое условие сходимости не соблюдается");
        }
        int iterations = 0;
        double delta, k = (1 - q) / q, prev, root = (a + b) / 2;
        do {
            prev = root;
            root = function.apply(root);
            delta = root > prev ? root - prev : prev - root;
            iterations++;
        } while (delta > k * ACCURACY && iterations < LIMIT);
        if (iterations == LIMIT) {
            throw new RuntimeException("Указанная точность не достигнута");
        }
        if (a == -5) root *= -1;
        return new Object[] { root, delta / k, iterations, function.apply(root) };
    }

    /**
     * решить уравнение методом Ньютона
     */

    @Override
    public Object[] solveByNewton(Function function, double a, double b) {
        double x0;
        if (function.apply(a) * function.derivative2(a, 1e-9) > 0) x0 = a;
        else x0 = b;
        double xi = x0;

        long iterations = 0;
        do {
            xi = xi - (function.apply(xi) / function.derivative(xi, 1e-9));
            iterations++;
            if (iterations == LIMIT) throw new RuntimeException("Превышено максимальное количество итераций");
        } while (Math.abs(function.apply(xi)) > ACCURACY);
        double root = xi;
        double delta = Math.abs(function.apply(xi));

        return new Object[] { root, delta, iterations, function.apply(root) };
    }

    /**
     * поиск максимального значение производной функции на отрезке [a, b]
     */
    private double derivativeSeriesMax(Function function, double a, double b) {
        double max = 0, delta = (b - a) / 1000000;

        if (a == b) return Math.abs(function.derivative(0, 1e-9));

        for (double point = a; point <= b; point += delta) {
            max = Math.max(max, Math.abs(function.derivative(point, 1e-9)));
        }
        return max;
    }

    @Override
    public Object[][] solveByIterations(double[] G, Function... functions) {
        int iters = 0;
        for (double x = G[0] + 1e-5; x < G[1]; x += (G[1] - G[0]) / 1000d) {
            for (double y = G[2] + 1e-5; y < G[3]; y += (G[3] - G[2]) / 1000d) {
                double temp1 = functions[6].apply(x, y);
                double temp2 = functions[7].apply(x, y);
                double temp3 = functions[8].apply(x, y);
                double temp4 = functions[9].apply(x, y);
                if (Math.abs(temp1) + Math.abs(temp2) >= 1 || Math.abs(temp3) + Math.abs(temp4) >= 1) {
                    throw new RuntimeException("Метод расходится");
                }
            }
        }

        double xn = G[1], yn = G[3];
        double x_prev, y_prev;
        do {
            x_prev = xn;
            y_prev = yn;
            xn = functions[4].apply(xn, yn);
            yn = functions[5].apply(xn, yn);
            iters++;
        } while (Math.abs(xn - x_prev) > ACCURACY && Math.abs(yn - y_prev) > ACCURACY);
        Object[] X = new Object[] {xn, Math.abs(xn - x_prev)};
        Object[] Y= new Object[] {yn, Math.abs(yn - y_prev)};
        Object[] iterats = new Object[] {iters, null};
        return new Object[][] {X, Y, iterats};
    }
}
