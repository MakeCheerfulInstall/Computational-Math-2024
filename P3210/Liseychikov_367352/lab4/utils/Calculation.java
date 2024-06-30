package lab4.utils;

import lab2.util.FuncX;
import lab2.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Calculation {
    private static final List<Object[]> funcDeviations = new ArrayList<>();

    private Calculation() {}

    public static void calc(FuncX func, List<Point> points, String funcName) {
        double dev = deviationMeasure(func, points);
        double standartDev = standardDeviation(dev, points.size());
        coeffDetermination(func, points);
        funcDeviations.add(new Object[]{standartDev, funcName});
    }

    public static double deviationMeasure(FuncX func, List<Point> points) {
        double result = points.stream().map(p -> Math.pow(p.y() - func.solve(p.x()), 2)).reduce(0d, Double::sum);
        System.out.println("S = " + Math.round(result * 1000) / 1000.0);

        return result;
    }

    public static double standardDeviation(double deviation, int n) {
        double result = Math.sqrt(deviation / n);
        System.out.println("σ = " + Math.round(result * 1000) / 1000.0);

        return result;
    }

    public static double coeffDetermination(FuncX func, List<Point> points) {
        double mid = points.stream().map(p -> func.solve(p.x())).reduce(0d, Double::sum) / points.size();
        double result = 1 - points.stream().map(p -> Math.pow(p.y() - func.solve(p.x()), 2)).reduce(0d, Double::sum) /
                points.stream().map(p -> Math.pow(p.y() - mid, 2)).reduce(0d, Double::sum);
        System.out.println("R^2 = " + Math.round(result * 1000) / 1000.0);

        return result;
    }

    public static double pearsonCoeff(List<Point> points) {
        double midX = points.stream().map(Point::x).reduce(0d, Double::sum) / points.size();
        double midY = points.stream().map(Point::y).reduce(0d, Double::sum) / points.size();
        double del = Math.sqrt(points.stream().map(p -> Math.pow(p.x() - midX, 2)).reduce(0d, Double::sum) *
                points.stream().map(p -> Math.pow(p.y() - midY, 2)).reduce(0d, Double::sum));
        double result = points.stream().map(p -> (p.y() - midY) * (p.x() - midX)).reduce(0d, Double::sum) / del;
        System.out.println("Pearson coeff = " + Math.round(result * 1000) / 1000.0);

        return  result;
    }

    public static String bestApprox() {
        funcDeviations.sort((obj1, obj2) -> {
            double dev1 = (double) obj1[0];
            double dev2 = (double) obj2[0];
            if (dev1 > dev2) return 1;
            if (dev1 == dev2) return 0;
            return -1;
        });
        String result = funcDeviations.get(0)[1].toString();
        funcDeviations.clear();
        return result;
    }
}
