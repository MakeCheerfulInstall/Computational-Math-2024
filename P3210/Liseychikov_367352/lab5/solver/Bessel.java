package lab5.solver;

import lab2.util.Point;
import lab4.FileForLab;

import java.util.List;

public class Bessel implements ISolver {

    @Override
    public double solver(List<Point> points, double x) {
        float arrX[] = new float[10];
        float arrY[][] = new float[10][10];
        double sum, u;
        int k, n = points.size() - 1;

        for (int i = 1; i < n; i++)
            for (int j = 0; j < n - i; j++)
                arrY[j][i] = arrY[j + 1][i - 1] - arrY[j][i - 1];

        sum = (arrY[2][0] + arrY[3][0]) / 2;
        if (n % 2 != 0)
            k = n / 2;
        else
            k = n / 2 - 1;

        u = (x - arrX[k]) / (arrX[1] - arrX[0]);

        for (int i = 1; i < n; i++) {
            if (i % 2 != 0)
                sum = (float) (sum + ((u - 0.5) * cal_x(u, i - 1) * arrY[k][i]) / factorial(i));
            else
                sum = sum + (cal_x(u, i) * (arrY[k][i] + arrY[--k][i]) / (factorial(i) * 2));
        }
        System.out.println("Вычисление с помощью схемы Бесселя: " + FileForLab.getAnswer(sum));
        return sum;
    }

    int factorial(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++)
            fact = fact * i;
        return fact;
    }

    double cal_x(double u, int n) {
        double tmp;
        if (n == 0)
            return 1;
        tmp = u;
        for (int i = 1; i <= n / 2; i++)
            tmp = tmp * (u - i);
        for (int i = 1; i < n / 2; i++)
            tmp = tmp * (u + i);
        return tmp;
    }

}
