package org.example;

import javax.swing.*;

public class LinearApproximation {

    private final int number = 1;

    private final String NAME = "ЛИНЕЙНАЯ";

    private double a = 0;
    private double b = 0;

    private double sko = 0;

    private double[] epsilon;

    public double[] solve(double[] x, double[] y, int amount) {
        double sx = 0;
        double sxx = 0;
        double sy = 0;
        double sxy = 0;
        for (int i = 0; i < amount; i++) {
            sx += x[i];
        }
        for (int i = 0; i < amount; i++) {
            sxx += x[i] * x[i];
        }
        for (int i = 0; i < amount; i++) {
            sy += y[i];
        }
        for (int i = 0; i < amount; i++) {
            sxy += x[i] * y[i];
        }
        a = (sxy * amount - sx * sy) / (sxx * amount - sx * sx);
        b = (sxx * sy - sx * sxy) / (sxx * amount - sx * sx);
        double[] result = new double[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = a * x[i] + b;
        }
        epsilon = new double[amount];
        for (int i = 0; i < amount; i++) {
            epsilon[i] = result[i] - y[i];
        }
        for (int i = 0; i < amount; i++) {
            sko += epsilon[i] * epsilon[i];
        }
        sko = Math.sqrt(sko / amount);

        return result;
    }

    public void draw(double[] x, double[] y, double[] result, int amount) {
        String title = "Linear Approximation";
        FunctionDrawer functionDrawer = new FunctionDrawer(title, amount, x, y, result);
        functionDrawer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        functionDrawer.pack();
        functionDrawer.setVisible(true);
    }

    public double getPearsonCoefficient(double[] x, double[] y, int amount) {
        double midX = 0;
        double midY = 0;
        double r = 0;
        for (int i = 0; i < amount; i++) {
            midX += x[i];
            midY += y[i];
        }
        midX = midX / amount;
        midY = midY / amount;
        double chisl = 0;
        double znam = 0;
        for (int i = 0; i < amount; i++) {
            chisl += (x[i] - midX) * (y[i] - midY);
            znam += (x[i] - midX) * (x[i] - midX) * (y[i] - midY) * (y[i] - midY);
        }
        r = chisl / znam;
        return r;
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

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double[] getEpsilon() {
        return epsilon;
    }

    public double getSko() {
        return sko;
    }

}
