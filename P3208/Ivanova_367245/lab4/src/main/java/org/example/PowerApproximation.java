package org.example;

import javax.swing.*;

public class PowerApproximation {

    private final int number = 6;
    private final String NAME = "СТЕПЕННАЯ";
    private double[] epsilon;

    private double a;
    private double b;

    double sko = 0;

    public double[] solve(double[] x, double[] y, int amount) {
        epsilon = new double[amount];

        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;

        for (int i = 0; i < amount; i++) {
            sx += Math.log(x[i]);
            sxx += Math.log(x[i]) * Math.log(x[i]);
            sy += Math.log(y[i]);
            sxy += Math.log(x[i]) * Math.log(y[i]);
        }

        a = (sxy * amount - sx * sy) / (sxx * amount - sx * sx);
        b = (sxx * sy - sx * sxy) / (sxx * amount - sx * sx);

        a = Math.exp(a);

        double[] result = new double[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = a * Math.pow(x[i], b);
        }

        for (int i = 0; i < amount; i++) {
            epsilon[i] = result[i] - y[i];
            sko += epsilon[i] * epsilon[i];
        }
        sko = Math.sqrt(sko / amount);

        return result;
    }
    public void draw(double [] x, double [] y, double[] result, int amount){
        String title = "Power Approximation";
        FunctionDrawer functionDrawer = new FunctionDrawer(title, amount, x, y, result);
        functionDrawer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        functionDrawer.pack();
        functionDrawer.setVisible(true);
    }

    public double getDeterminationCoefficient(double[] result, double y[], int amount) {
        double midPhi = 0;
        double r2 = 0;
        for (int i = 0; i < amount; i++) {
            midPhi += result[i];
        }
        midPhi = midPhi / amount;
        double chisl = 0;
        double znam = 0;
        for (int i = 0; i < amount; i++) {
            chisl += (y[i] - result[i]) * (y[i] - result[i]);
            znam += (y[i] - midPhi) * (y[i] - midPhi);
        }
        r2 = 1 - chisl / znam;
        return r2;
    }
    public double[] getEpsilon() {
        return epsilon;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getSko() {
        return sko;
    }

    public int getNumber() {
        return number;
    }
}
