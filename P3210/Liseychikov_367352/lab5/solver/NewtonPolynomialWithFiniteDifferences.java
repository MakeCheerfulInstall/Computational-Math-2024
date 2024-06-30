package lab5.solver;

import lab2.util.Point;

import java.util.ArrayList;
import java.util.List;

public class NewtonPolynomialWithFiniteDifferences implements ISolver {
    @Override
    public double solver(List<Point> points, double x) {
        System.out.println("Многочлен Ньютона с конечными разностями:");
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
                double num = (actualRow.get(j + 1) - actualRow.get(j)) / (points.get(i + 1 + j).x() - points.get(j).x());
                actualRow.set(j, num);
                if (j == 0) {
                    firstColumn.add(num);
                }
                System.out.printf("%-10s", Math.round(10000 * num) / 10000.0);
            }
        }

        double answer = points.get(0).y();
        for (int i = 0; i < points.size() - 1; i++) {
            double multiplier = firstColumn.get(i);
            for (int j = 0; j < i + 1; j++) {
                multiplier *= (x - points.get(j).x());
            }
            answer += multiplier;
        }
        System.out.println("\n" + answer);
        return answer;
    }
}
