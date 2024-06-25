package lab2.solution;

import lab2.util.FuncX;
import lab2.util.Point;

import java.util.Optional;

public class Chord {
    private double chordMethod(FuncX function, double left, double right, double eps) {
        while (Math.abs(right - left) > eps) {
            left = right - (right - left) * function.solve(right) / (function.solve(right) - function.solve(left));
            right = left - (left - right) * function.solve(left) / (function.solve(left) - function.solve(right));
        }
        return right;
    }

    public Optional<Point> solve(FuncX func, double left, double right, double eps) {
        double point = chordMethod(func, left, right, eps);
        if (point >= left && point <= right) {
            SimpleIteration.param = point - 0.01;
            System.out.println("Результат метода хорд: " + point);
            return Optional.of(new Point(point, 0));
        }
        System.out.println("Результат метода хорд: решение не удовлетворяет заданному интервалу");
        return Optional.empty();
    }
}
