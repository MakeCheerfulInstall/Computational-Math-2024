package lab2.solution;

import lab2.util.FuncX;
import lab2.util.Point;

import java.util.Optional;

public class Secant {
    private double secantMethod(FuncX func, double left, double right, double eps) {
        int n = 0;
        double x = left;
        double xLast = (left + right) / 2;
        double xGrandLast;

        while (Math.abs(x - xLast) > eps) {
            n++;
            xGrandLast = xLast;
            xLast = x;
            x -= func.solve(xLast) * (xLast - xGrandLast) / (func.solve(xLast) - func.solve(xGrandLast));
        }
        return x;
    }

    public Optional<Point> solve(FuncX func, double left, double right, double eps) {
        double point = secantMethod(func, left, right, eps);
        if (point >= left && point <= right) {
            System.out.println("Результат метода секущих: " + point);
            return Optional.of(new Point(point, 0));
        }
        System.out.println("Результат метода секущих: решение не удовлетворяет заданному интервалу");
        return Optional.empty();
    }
}
