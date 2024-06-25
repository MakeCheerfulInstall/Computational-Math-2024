package lab4.funcs;

import lab2.util.FuncX;
import lab2.util.Point;
import lab4.utils.Calculation;
import lab4.utils.Opredelitel;

import java.util.List;

public class CubicFunc implements ApproxFunc {
    @Override
    public FuncX create(List<Point> points) {
        double[][] coeffs = new double[4][4];
        coeffs[0][0] = points.size();
        coeffs[0][1] = points.stream().map(Point::x).reduce(0d, Double::sum);
        coeffs[0][2] = points.stream().map(p -> Math.pow(p.x(), 2)).reduce(0d, Double::sum);
        coeffs[0][3] = points.stream().map(p -> Math.pow(p.x(), 3)).reduce(0d, Double::sum);
        coeffs[1][0] = coeffs[0][1];
        coeffs[1][1] = coeffs[0][2];
        coeffs[1][2] = coeffs[0][3];
        coeffs[1][3] = points.stream().map(p -> Math.pow(p.x(), 4)).reduce(0d, Double::sum);
        coeffs[2][0] = coeffs[1][1];
        coeffs[2][1] = coeffs[1][2];
        coeffs[2][2] = coeffs[1][3];
        coeffs[2][3] = points.stream().map(p -> Math.pow(p.x(), 5)).reduce(0d, Double::sum);
        coeffs[3][0] = coeffs[2][1];
        coeffs[3][1] = coeffs[2][2];
        coeffs[3][2] = coeffs[2][3];
        coeffs[3][3] = points.stream().map(p -> Math.pow(p.x(), 6)).reduce(0d, Double::sum);
        double[] answers = new double[4];
        answers[0] = points.stream().map(Point::y).reduce(0d, Double::sum);
        answers[1] = points.stream().map(p -> p.x() * p.y()).reduce(0d, Double::sum);
        answers[2] = points.stream().map(p -> Math.pow(p.x(), 2) * p.y()).reduce(0d, Double::sum);
        answers[3] = points.stream().map(p -> Math.pow(p.x(), 3) * p.y()).reduce(0d, Double::sum);
        double opred = Opredelitel.findOpred4(coeffs);

        double[][] coeffs1 = new double[4][4];
        for(int i = 0; i < 4; i++) {
            coeffs1[i][0] = answers[i];
            coeffs1[i][1] = coeffs[i][1];
            coeffs1[i][2] = coeffs[i][2];
            coeffs1[i][3] = coeffs[i][3];
        }
        double opred1 = Opredelitel.findOpred4(coeffs1);

        for(int i = 0; i < 4; i++) {
            coeffs1[i][0] = coeffs[i][0];
            coeffs1[i][1] = answers[i];
            coeffs1[i][2] = coeffs[i][2];
            coeffs1[i][3] = coeffs[i][3];
        }
        double opred2 = Opredelitel.findOpred4(coeffs1);

        for(int i = 0; i < 4; i++) {
            coeffs1[i][0] = coeffs[i][0];
            coeffs1[i][1] = coeffs[i][1];
            coeffs1[i][2] = answers[i];
            coeffs1[i][3] = coeffs[i][3];
        }
        double opred3 = Opredelitel.findOpred4(coeffs1);

        for(int i = 0; i < 4; i++) {
            coeffs1[i][0] = coeffs[i][0];
            coeffs1[i][1] = coeffs[i][1];
            coeffs1[i][2] = coeffs[i][2];
            coeffs1[i][3] = answers[i];
        }
        double opred4 = Opredelitel.findOpred4(coeffs1);

        double a = opred1 / opred;
        double b = opred2 / opred;
        double c = opred3 / opred;
        double d = opred4 / opred;

        System.out.println("Кубическая аппроксимация: ");
        System.out.println("fi(x) = a + b * x + c * x ^ 2 + d * x ^ 3");
        System.out.println("fi(x) = " + Math.round(a * 100) / 100.0 + " + " + Math.round(b * 100) / 100.0 + " * x + " + Math.round(c * 100) / 100.0 + " * x ^ 2 + " + Math.round(d * 100) / 100.0 + " * x ^ 3");
        FuncX func = x -> a + b * x + c * x * x + d * x * x * x;
        Calculation.calc(func, points, "кубическая аппроксимация");

        return func;
    }
}
