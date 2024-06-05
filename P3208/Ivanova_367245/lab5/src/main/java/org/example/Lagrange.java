package org.example;

public class Lagrange {

    public double solve(double [] x, double [] y, int amount, double t ){
        double result = 0;
        for (int i = 0; i < amount; i++) {
            double temp = y[i];
            for (int j = 0; j < amount; j++) {
                if (j != i) {
                    temp *= (t - x[j]) / (x[i] - x[j]);
                }
            }
            result += temp;
        }
        return result;
    }
}
