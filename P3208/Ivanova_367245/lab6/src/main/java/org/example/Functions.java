package org.example;

public class Functions {
    public double f(int n, double x, double y) {
        if (n == 1) {
            return x + y;
        } else if (n == 2) {
            return x * x + y + 1;
        } else return Math.exp(x);
    }

    public double exactY(int n, double x, double x0, double y0) {
        if (n == 1) {
            return (y0 + x0 + 1) * Math.exp(x) - x - 1;
        } else if (n == 2) {
            return (x0+y0+3)*Math.exp(x) - x * x - 2 * x - 3;
        } else return Math.exp(x) - 1;
    }
}
