package lab6.math;

public class Euler {
    public static double[][] execute(double a, double b, double y0, double h) {
        int n = (int) ((b - a) / h + 1);
        double[][] result = new double[n][3];

        result[0][0] = a;
        result[0][1] = y0;
        result[0][2] = Functions.f(result[0][0], result[0][1]);

        for (int i = 1; i < n; i++) {
            result[i][0] = result[i - 1][0] + h;
            result[i][1] = result[i - 1][1] + h/2 * (Functions.f(result[i - 1][0], result[i - 1][1]) +
                    Functions.f(result[i][0], result[i - 1][1] + h * Functions.f(result[i - 1][0], result[i - 1][1])));
            result[i][2] = Functions.f(result[i][0], result[i][1]);
        }

        return result;
    }
}
