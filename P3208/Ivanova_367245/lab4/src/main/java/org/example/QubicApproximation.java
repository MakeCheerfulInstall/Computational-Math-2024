package org.example;

import javax.swing.*;

public class QubicApproximation {
    private final int number = 3;
    private final String NAME = "КВАДРАТИЧНАЯ";
    private double[] epsilon;
    double a0 = 0;
    double a1 = 0;
    double a2 = 0;
    double a3 = 0;

    double sko = 0;

    public static double findDeterminant(double[][] matrix) {
        double determinant = 0;

        determinant = matrix[0][0] * (
                matrix[1][1] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2]) -
                        matrix[1][2] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1]) +
                        matrix[1][3] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1])
        ) - matrix[0][1] * (
                matrix[1][0] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2]) -
                        matrix[1][2] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0]) +
                        matrix[1][3] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0])
        ) + matrix[0][2] * (
                matrix[1][0] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1]) -
                        matrix[1][1] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0]) +
                        matrix[1][3] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0])
        ) - matrix[0][3] * (
                matrix[1][0] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1]) -
                        matrix[1][1] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0]) +
                        matrix[1][2] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0])
        );

        return determinant;
    }
    public double[] solve(double[] x, double[] y, int amount) {

        double sx = 0;
        double sxx = 0;
        double sxxx = 0;
        double sxxxx = 0;
        double sy = 0;
        double sxy = 0;
        double sxxy = 0;
        double sxxxxx = 0;
        double sxxxy = 0;
        double sxxxxxx = 0;

        for (int i = 0; i < amount; i++) {
            sx += x[i];
            sxx += x[i] * x[i];
            sxxx += x[i] * x[i] * x[i];
            sxxxx += x[i] * x[i] * x[i] * x[i];
            sxxxxx += x[i] * x[i] * x[i] * x[i] * x[i];
            sxxxxxx += x[i] * x[i] * x[i] * x[i] * x[i] * x[i];
            sy += y[i];
            sxy += x[i] * y[i];
            sxxy += x[i] * x[i] * y[i];
            sxxxy += x[i] * x[i] * x[i] * y[i];
        }



        double[][] matrix = {
                {amount, sx, sxx, sxxx},
                {sx, sxx, sxxx, sxxxx},
                {sxx, sxxx, sxxxx, sxxxxx},
                {sxxx, sxxxx, sxxxxx, sxxxxxx}
        };
        double[][] matrixA0 = {
                {sy, sx, sxx, sxxx},
                {sxy, sxx, sxxx, sxxxx},
                {sxxy, sxxx, sxxxx, sxxxxx},
                {sxxxy, sxxxx, sxxxxx, sxxxxxx}
        };
        double[][] matrixA1 = {
                {amount, sy, sxx, sxxx},
                {sx, sxy, sxxx, sxxxx},
                {sxx, sxxy, sxxxx, sxxxxx},
                {sxxx, sxxxy, sxxxxx, sxxxxxx}
        };
        double[][] matrixA2 = {
                {amount, sx, sy, sxxx},
                {sx, sxx, sxy, sxxxx},
                {sxx, sxxx, sxxy, sxxxxx},
                {sxxx, sxxxx, sxxxy, sxxxxxx}
        };
        double[][] matrixA3 = {
                {amount, sx, sxx, sy},
                {sx, sxx, sxxx, sxy},
                {sxx, sxxx, sxxxx, sxxy},
                {sxxx, sxxxx, sxxxxx, sxxxy}
        };
        double determinantMatrix = findDeterminant(matrix);

        if (determinantMatrix != 0) {

            a0 = findDeterminant(matrixA0) / determinantMatrix;
            a1 = findDeterminant(matrixA1) / determinantMatrix;
            a2 = findDeterminant(matrixA2) / determinantMatrix;
            a3 = findDeterminant(matrixA3) / determinantMatrix;

        } else {
            System.out.println("Определитель равен нулю, невозможно вычислить коэффициенты.");
            System.exit(0);
        }

        double[] result = new double[amount];
        for (int i = 0; i < amount; i++) {
            result[i] = a0 + a1 * x[i] + a2 * x[i] * x[i] + a3 * x[i] * x[i] * x[i];
        }

        epsilon = new double[amount];
        for (int i = 0; i < amount; i++) {
            epsilon[i] = result[i] - y[i];
        }

        for (int i = 0; i < amount; i++) {
            sko+= epsilon[i]*epsilon[i];
        }
        sko = Math.sqrt(sko/amount);

        return result;
    }
    public void draw(double [] x, double [] y, double [] result, int amount){
        String title = "Qubic Approximation";
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

    public double getA0() {
        return a0;
    }

    public double getA1() {
        return a1;
    }

    public double getA2() {
        return a2;
    }

    public double getA3() {
        return a3;
    }

    public double getSko(){
        return sko;
    }
}
