package org.example;

public class AdamsMethod {
    public double[] x = new double[20];
    public double[] y = new double[20];
    public double[] f = new double[20];

    public void solve(double x0, double y0, double a, double b, double h, int functionChoice) {
        Functions functions = new Functions();
        double df1 = 0;
        double df2 = 0;
        double df3 = 0;
        RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod();
        rungeKuttaMethod.solve(x0, y0, a, a + 3 * h, h, functionChoice);
        for (int i = 0; i < 4; i++) {
            f[i] = rungeKuttaMethod.getF()[i];
            x[i] = rungeKuttaMethod.getX()[i];
            y[i] = rungeKuttaMethod.getY()[i];
        }
        int i = 3;
        a = a + 3 * h;
        while (a < b) {
            a += h;
            df1 = f[i] - f[i - 1];
            df2 = f[i] - 2 * f[i - 1] + f[i - 2];
            df3 = f[i] - 3 * f[i - 1] + 3 * f[i - 2] + f[i - 3];
            y[i + 1] = y[i] + h * f[i] + Math.pow(h, 2) / 2 * df1 + 5 * Math.pow(h, 3) / 12 * df2 + 3 * Math.pow(h, 4) / 8 * df3;
            x[i+1] = a;
            f[i+1] = functions.f(functionChoice, x[i], y[i]);
            i++;
        }
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double[] getF() {
        return f;
    }

    public double getInaccuracy(int n, int functionChoice, double x0, double y0) {
        double eps = 0;
        Functions functions = new Functions();
        double[] y_exact = new double[n];
        for (int i = 0; i < n; i++) {
            y_exact[i] = functions.exactY(functionChoice, x[i], x0, y0);
            if (Math.abs(y_exact[i] - y[i]) > eps) {
                eps = Math.abs(y_exact[i] - y[i]);
            }
        }
        return eps;
    }

}
