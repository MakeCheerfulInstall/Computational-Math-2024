package org.example;

import static java.lang.Math.abs;

public class RungeKuttaMethod {
    public double[] x = new double[20];
    public double[] y= new double[20];
    public double[] f= new double[20];

    public double[] solve(double x0, double y0, double a, double b, double h, int functionChoice) {
        Functions functions = new Functions();
        x[0] = x0;
        y[0] = y0;
        f[0] = functions.f(functionChoice, x0, y0);
        int i = 1;
        double k1, k2, k3, k4;
        a+=h;
        while (a <= b) {
            x[i] = a;
            k1 = h * functions.f(functionChoice, x[i - 1], y[i - 1]);
            k2 = h * functions.f(functionChoice, x[i - 1] + h / 2, y[i - 1] + k1 / 2);
            k3 = h * functions.f(functionChoice, x[i - 1] + h / 2, y[i - 1] + k2 / 2);
            k4 = h * functions.f(functionChoice, x[i - 1] + h, y[i - 1] + k3);
            y[i] = y[i-1]+1.0/6*(k1+2*k2+2*k3+k4);
            f[i] = functions.f(functionChoice, x[i], y[i]);
            a+=h;
            i++;
        }
        return f;
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double RungeRule(int n, double h) {
        return abs(Math.pow(y[n], h) - Math.pow(y[n], h / 2)) / (Math.pow(2, 4) - 1);
    }

    public double[] getF() {
        return f;
    }
}
