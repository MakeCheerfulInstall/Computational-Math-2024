package lab4.funcs;

import lab2.util.FuncX;
import lab2.util.Point;
import lab4.utils.Calculation;

import java.util.List;

public class LogFunc implements ApproxFunc {
    @Override
    public FuncX create(List<Point> points) {
        double sx = points.stream().map(p -> Math.log(p.x())).reduce(0d, Double::sum);
        double sxx = points.stream().map(p -> Math.log(p.x()) * Math.log(p.x())).reduce(0d, Double::sum);
        double sy = points.stream().map(Point::y).reduce(0d, Double::sum);
        double sxy = points.stream().map(p -> Math.log(p.x()) * p.y()).reduce(0d, Double::sum);
        int n = points.size();

        double opred = sxx * n - sx * sx;
        double opred1 = sxy * n - sx * sy;
        double opred2 = sxx * sy - sx * sxy;
        double a = opred1 / opred;
        double b = opred2 / opred;

        System.out.println("Логарифмическая аппроксимация: ");
        System.out.println("fi(x) = a * lx + b");
        System.out.println("fi(x) = " + Math.round(a * 100) / 100.0 + " * x + " + Math.round(b * 100) / 100.0);
        FuncX func = x -> a * Math.log(x) + b;
        Calculation.calc(func, points, "логарифмическая аппроксимация");

        return func;
    }
}
