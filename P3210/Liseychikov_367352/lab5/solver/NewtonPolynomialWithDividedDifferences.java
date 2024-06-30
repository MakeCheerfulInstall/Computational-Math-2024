package lab5.solver;

import lab2.util.Point;

import java.util.ArrayList;
import java.util.List;

public class NewtonPolynomialWithDividedDifferences implements ISolver {

    @Override
    public double solver(List<Point> points, double x) {
        System.out.println("Многочлен Ньютона с разделенными разностями:");
        System.out.printf("%-10s", "x");
        points.stream().forEach(p -> System.out.printf("%-10s", Math.round(10000 * p.x()) / 10000.0));
        System.out.printf("\n%-10s", "y");
        points.stream().forEach(p -> System.out.printf("%-10s", Math.round(10000 * p.y()) / 10000.0));

        List<Double> firstColumn = new ArrayList<>();
        List<Double> actualRow = new ArrayList<>();
        points.stream().forEach(p -> actualRow.add(p.y()));
        for (int i = 0; i < points.size() - 1; i++) {
            System.out.printf("\n%-10s", i + 1 + "Δy");
            for (int j = 0; j < points.size() - i - 1; j++) {
                double num = actualRow.get(j + 1) - actualRow.get(j);
                actualRow.set(j, num);
                if (j == 0) {
                    firstColumn.add(num);
                }
                System.out.printf("%-10s", Math.round(10000 * num) / 10000.0);
            }
        }

        double answer = getAnswer(points, x, firstColumn);
        System.out.println("\n" + answer);
        return answer;
    }

    private static double getAnswer(List<Point> points, double x, List<Double> firstColumn) {
        double h = 1;
        for (Point point : points) {
            if (point.x() > x) {
                h = point.x() - points.get(0).x();
                break;
            }
        }
        double t = (x - points.get(0).x()) / h;
        double answer = points.get(0).y();
        for (int i = 0; i < firstColumn.size(); i++) {
            double num = firstColumn.get(i);
            for (int j = 0; j <= i; j++) {
                num *= (t - j) / (j + 1);
            }
            answer += num;
        }
        return answer;
    }
}
