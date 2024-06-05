package org.example;

import static java.lang.Math.abs;

public class ImprovedEulerMethod {
    public double[] x = new double[20];
    public double[] y = new double[20];
    public double[] f= new double[20];

    public void solve(double x0, double y0, double a, double b, double h, int functionChoice) {
        Functions functions = new Functions();
        x[0] = x0;
        y[0] = y0;
        f[0] = functions.f(functionChoice, x0, y0);
        int i = 0;
        double y_temp;
        while (a < b) {
            i++;
            a+=h;
            x[i] = a;
            y_temp = y[i - 1] + h * functions.f(functionChoice, x[i - 1], y[i - 1]);
            y[i] = y[i - 1] + h / 2 * (functions.f(functionChoice, x[i - 1], y[i - 1]) + functions.f(functionChoice, x[i], y_temp));
            f[i] = functions.f(functionChoice, x[i], y[i]);
        }
    }

    public double[] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }

    public double[] getF(){
        return f;
    }


    public double RungeRule(int n, double h) {
        return abs(Math.pow(y[n], h) - Math.pow(y[n], h / 2)) / (Math.pow(2, 2) - 1);
    }
}
