package lab5.solver;

import lab2.util.Point;

import java.util.List;

public class LagrangePolynomial implements ISolver {

    @Override
    public double solver(List<Point> points, double x) {
        double answer = 0;
        for (int i = 0; i < points.size(); i++) {
            double num = 1;
            for (int j = 0; j < points.size(); j++) {
                if (i == j) continue;
                num *= (x - points.get(j).x()) / (points.get(i).x() - points.get(j).x());
            }
            answer += num * points.get(i).y();
        }
        System.out.println("Многочлен Лагранжа: " + answer);
        return answer;
    }
}
