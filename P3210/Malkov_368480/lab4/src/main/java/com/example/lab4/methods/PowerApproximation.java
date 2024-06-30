package com.example.lab4.methods;

import java.util.ArrayList;

public class PowerApproximation extends Method{
    private Double a;
    private Double b;

    public double f(double x) {
        return a * Math.pow(x, b);
    }

    @Override
    public void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n) {
        double sumLnX = 0;
        double sumLnY = 0;
        double sumLnX2 = 0;
        double sumLnXLnY = 0;

        for (int i = 0; i < n; i++) {
            double lnX = Math.log(arrayOfX.get(i));
            double lnY = Math.log(arrayOfY.get(i));

            sumLnX += lnX;
            sumLnY += lnY;
            sumLnX2 += lnX * lnX;
            sumLnXLnY += lnX * lnY;
        }

        b = (n * sumLnXLnY - sumLnX * sumLnY) / (n * sumLnX2 - sumLnX * sumLnX);
        double lnA = (sumLnY - b * sumLnX) / n;
        a = Math.exp(lnA);
        double phiAvg = 0;

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
        double ssTot = 0;
        double ssRes = 0;

        for (int i = 0; i < n; i++) {
            double fi = f(arrayOfX.get(i));
            ssTot += Math.pow(arrayOfY.get(i) - phiAvg, 2);
            ssRes += Math.pow(arrayOfY.get(i) - fi, 2);
        }
        determ = 1 - (ssRes / ssTot);
    }

    @Override
    public String getNameMethod() {
        return "Степенная аппроксимация";
    }

    @Override
    protected String getStringFun() {
        return "Phi(x)= " + a  + " * x" + "^ b";
    }
}
