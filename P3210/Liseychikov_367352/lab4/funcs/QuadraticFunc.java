package lab4.funcs;

import lab2.util.FuncX;
import lab2.util.Point;
import lab4.utils.Calculation;
import lab4.utils.Opredelitel;

import java.util.List;

public class QuadraticFunc implements ApproxFunc {
    @Override
    public FuncX create(List<Point> points) {
        double[][] coeffs = new double[3][3];
        coeffs[0][0] = points.size();
        coeffs[0][1] = points.stream().map(Point::x).reduce(0d, Double::sum);
        coeffs[0][2] = points.stream().map(p -> Math.pow(p.x(), 2)).reduce(0d, Double::sum);
        coeffs[1][0] = coeffs[0][1];
        coeffs[1][1] = coeffs[0][2];
        coeffs[1][2] = points.stream().map(p -> Math.pow(p.x(), 3)).reduce(0d, Double::sum);
        coeffs[2][0] = coeffs[1][1];
        coeffs[2][1] = coeffs[1][2];
        coeffs[2][2] = points.stream().map(p -> Math.pow(p.x(), 4)).reduce(0d, Double::sum);
        double[] answers = new double[3];
        answers[0] = points.stream().map(Point::y).reduce(0d, Double::sum);
        answers[1] = points.stream().map(p -> p.x() * p.y()).reduce(0d, Double::sum);
        answers[2] = points.stream().map(p -> Math.pow(p.x(), 2) * p.y()).reduce(0d, Double::sum);
        double opred = Opredelitel.findOpred3(coeffs);

        double[][] coeffs1 = new double[3][3];
        for(int i = 0; i < 3; i++) {
            coeffs1[i][0] = answers[i];
            coeffs1[i][1] = coeffs[i][1];
            coeffs1[i][2] = coeffs[i][2];
        }
        double opred1 = Opredelitel.findOpred3(coeffs1);

        double[][] coeffs2 = new double[3][3];
        for(int i = 0; i < 3; i++) {
            coeffs2[i][0] = coeffs[i][0];
            coeffs2[i][1] = answers[i];
            coeffs2[i][2] = coeffs[i][2];
        }
        double opred2 = Opredelitel.findOpred3(coeffs2);

        double[][] coeffs3 = new double[3][3];
        for(int i = 0; i < 3; i++) {
            coeffs3[i][0] = coeffs[i][0];
            coeffs3[i][1] = coeffs[i][1];
            coeffs3[i][2] = answers[i];
        }
        double opred3 = Opredelitel.findOpred3(coeffs3);

        double a = opred1 / opred;
        double b = opred2 / opred;
        double c = opred3 / opred;

        System.out.println("Квадратичная аппроксимация: ");
        System.out.println("fi(x) = a + b * x + c * x ^ 2");
        System.out.println("fi(x) = " + Math.round(a * 100) / 100.0 + " + " + Math.round(a * 100) / 100.0 + " * x + " + Math.round(b * 100) / 100.0 + " * x ^ 2");
        FuncX func = x -> a + b * x + c * x * x;
        Calculation.calc(func, points, "квадратичная аппроксимация");

        return func;
    }


}
