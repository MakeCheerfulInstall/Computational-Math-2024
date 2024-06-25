package lab4.funcs;

import lab2.util.FuncX;
import lab2.util.Point;
import lab4.utils.Calculation;

import java.util.List;

public class ExpFunc implements ApproxFunc {
    @Override
    public FuncX create(List<Point> points) {
        double sx = points.stream().map(Point::x).reduce(0d, Double::sum);
        double sxx = points.stream().map(p -> p.x() * p.x()).reduce(0d, Double::sum);
        double sy = points.stream().map(p -> Math.log(p.y())).reduce(0d, Double::sum);
        double sxy = points.stream().map(p -> p.x() * Math.log(p.y())).reduce(0d, Double::sum);
        int n = points.size();

        double opred = sxx * n - sx * sx;
        double opred1 = sxy * n - sx * sy;
        double opred2 = sxx * sy - sx * sxy;
        double a = opred1 / opred;
        double b = Math.exp(opred2 / opred);

        System.out.println("Экспоненциальная аппроксимация: ");
        System.out.println("fi(x) = b * e ^ ax");
        System.out.println("fi(x) = " + Math.round(b * 100) / 100.0 + " * e ^ " + Math.round(a * 100) / 100.0 + "x");
        FuncX func = x -> b * Math.exp(a * x);
        Calculation.calc(func, points, "экспоненциальная аппроксимация");

        return func;
    }
}
