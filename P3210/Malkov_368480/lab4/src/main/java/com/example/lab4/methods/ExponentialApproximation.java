package com.example.lab4.methods;

import java.util.ArrayList;

public class ExponentialApproximation extends Method{
    private Double a;
    private Double b;

    public double f(double x) {
        return a * Math.exp(b * x);
    }

    @Override
    public void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n) {
        double sumX = 0;
        double sumYln = 0;
        double sumX2 = 0;
        double sumXYln = 0;

        for (int i = 0; i < n; i++) {
            double x = arrayOfX.get(i);
            double y = Math.log(arrayOfY.get(i));

            sumX += x;
            sumYln += y;
            sumX2 += x * x;
            sumXYln += x * y;
        }


        double b = (n * sumXYln - sumX * sumYln) / (n * sumX2 - sumX * sumX);
        double aLn = (sumYln - b * sumX) / n;
        double phiAvg = 0;
        this.a = Math.exp(aLn);
        this.b = b;

        for (int i = 0; i < n; i++) {
            double fi = f(arrayOfX.get(i));
            double eps = fi - arrayOfY.get(i);
            ArrayList<Double> row = new ArrayList<>();
            row.add(arrayOfX.get(i));
            row.add(arrayOfY.get(i));
            phiAvg += f(arrayOfX.get(i));
            row.add(fi);
            row.add(eps);
            table.add(row);
            S += Math.pow(eps, 2);
        }
        sko = Math.sqrt(S/n);

        phiAvg = phiAvg / n;
        double down = 0;
        double up = 0;

        for (int i = 0; i< n; i++) {
            double fi = f(arrayOfX.get(i));
            down += Math.pow(arrayOfY.get(i) - phiAvg, 2);
            up += Math.pow(arrayOfY.get(i) - fi, 2);
        }

        determ = 1 - (up / down);

    }

    @Override
    public String getNameMethod() {
        return "Экспоненциальная аппроксимация";
    }
    @Override
    protected String getStringFun() {
        return "Phi(x)=" + a + " * e ^ " + b + " x";
    }
}
