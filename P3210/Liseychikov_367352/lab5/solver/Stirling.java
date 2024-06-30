package lab5.solver;

import lab2.util.Point;
import lab4.FileForLab;

import java.util.List;

public class Stirling implements ISolver {

    @Override
    public double solver(List<Point> points, double x) {
        double h, a, u;
        double y1 = 0, N1 = 1, d = 1,
                N2 = 1, d2 = 1, temp1 = 1,
                temp2 = 1, k = 1, l = 1, delta[][];

        delta = new double[points.size()][points.size()];
        int i, j, s;
        h = points.get(1).x() - points.get(0).x();
        s = points.size() / 2;
        a = points.get(s).x();
        u = (x - a) / h;

        for (i = 0; i < points.size() - 1; ++i) {
            delta[i][0] = points.get(i + 1).y() - points.get(i).y();
        }
        for (i = 1; i < points.size() - 1; ++i) {
            for (j = 0; j < points.size() - i - 1; ++j) {
                delta[j][i] = delta[j + 1][i - 1] - delta[j][i - 1];
            }
        }

        y1 = points.get(s).y();
        for (i = 1; i <= points.size() - 1; i++) {
            if (i % 2 != 0) {
                if (k != 2)
                    temp1 *= (Math.pow(u, k) - Math.pow((k - 1), 2));
                else
                    temp1 *= (Math.pow(u, 2) - Math.pow((k - 1), 2));
                k++;
                d *= i;
                s = (points.size() - i) / 2;
                y1 += (temp1 / (2 * d)) * (delta[s][i - 1] + delta[s - 1][i - 1]);
            } else {
                temp2 *= (Math.pow(u, 2) -
                        Math.pow((l - 1), 2));
                ++l;
                d *= i;
                s = (points.size() - i) / 2;
                y1 += (temp2 / (d)) * (delta[s][i - 1]);
            }
        }
        FileForLab.answ = y1;
        System.out.println("Вычисление с помощью схемы Стирлинга: " + y1);
        return y1;
    }
}
