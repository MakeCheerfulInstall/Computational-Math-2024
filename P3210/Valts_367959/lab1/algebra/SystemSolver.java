package lab1.algebra;

import java.util.Arrays;

public class SystemSolver {

    private final int MAX_ITERATIONS = 100;

    public boolean solveWithJacoby(Jacobi jacobi, double accuracy) {
        int iterations = 0;
        int n = jacobi.getSize();
        double[] actual = new double[n];
        double[] prev = new double[n];
        Arrays.fill(actual, 0);
        Arrays.fill(prev, 0);
        do {
            prev = actual.clone();
            // пробегаемся по рядам
            for (int i = 0; i < n; i++) {
                double sum = jacobi.getFreeMember(i);
                // пробегаемся по элементам
                for (int j = 0; j < n; j++) {
                    // если элемент не диагональный, то мы из суммы (недобудущего элемента), вычитаем предыдущий * коэфф
                    if (j != i)
                        sum -= jacobi.get(i, j) * prev[j];
                }
                // иначе мы присваиваем новый элемент в текущий вектор
                actual[i] = 1 / jacobi.get(i,i) * sum;
            }
            iterations++;
            if (iterations > MAX_ITERATIONS) {
                return false;
            }
            // проверка на достижение нужной погрешности
        } while (getMaxAbsValue(subtractFromVector(actual, prev)) > accuracy);

        jacobi.setIterations(iterations);
        jacobi.setRootsSolution(actual);
        jacobi.setErrorMargins(subtractFromVector(actual, prev));
        return true;
    }


    public double[] subtractFromVector(double[] v1, double[] v2) {
        double[] r = new double[v1.length];
        for (int i = 0; i < r.length; i++)
            r[i] = v1[i] - v2[i];
        return r;
    }

    public double getMaxAbsValue(double[] v1) {
        return Arrays.stream(v1).map(Math::abs).max().getAsDouble();
    }
}
