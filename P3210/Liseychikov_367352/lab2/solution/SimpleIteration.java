package lab2.solution;

import lab2.util.FuncX;
import lab2.util.Point;

import java.util.Optional;

public class SimpleIteration {
    private static final int LIMIT = 100;
    public static double param;

    public Optional<Point> solve(FuncX func, double left, double right, double eps) {
        simpleIterations(func, left, right, eps);
        return Optional.of(new Point(param, 0));
    }

    private double modifyFunc(FuncX func, double lambda, double x) {
        return x + lambda * func.solve(x);
    }

    private double maxdPhi(FuncX func, double lambda, double a, double b) {
        double step = (b-a) / 1000;
        double maxD = 0;

        for (double x = a + step; x <= b; x += step){
            maxD = Math.max(maxD, Math.abs(1 + lambda*func.derivative(x, 1e-9)));
        }

        return maxD;
    }

    public void simpleIterations(FuncX func, double left, double right, double eps){
        double lambda = Math.abs(func.derivative(left, 1e-9)) > Math.abs(func.derivative(right, 1e-9)) ? (-1 / func.derivative(left, 1e-9)) : (-1 / func.derivative(right, 1e-9));

        double q = maxdPhi(func, left, right, lambda);

        double prevX = left;

        double x = modifyFunc(func, lambda, prevX);
        int counter = 1;

        if (q < 1){
            while (Math.abs(x - prevX) > eps){
                prevX = x;
                x = modifyFunc(func, lambda, x);
                counter++;
            }
        } else {
            System.out.println("Гарантия сходимости метода не обеспечена");
            while (Math.abs(x - prevX) > eps && counter < LIMIT){
                prevX = x;
                x = modifyFunc(func, lambda, x);
                counter++;
            }
        }
        System.out.println("Результат метода простых итераций: " + param);
    }
}
