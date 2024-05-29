package org.example;

public class Newton {

    public double solve(double[] x, double[] y, int amount, double t) {
        double[][] dividedDifferences = new double[amount][amount];

        for (int i = 0; i < amount; i++) {
            dividedDifferences[i][0] = y[i];
        }

        for (int j = 1; j < amount; j++) {
            for (int i = 0; i < amount - j; i++) {
                dividedDifferences[i][j] = (dividedDifferences[i + 1][j - 1] - dividedDifferences[i][j - 1]) / (x[i + j] - x[i]);
            }
        }

//        System.out.println("Таблица разделенных разностей:");
//        for (int i = 0; i < amount; i++) {
//            for (int j = 0; j < amount - i; j++) {
//                System.out.printf("%-15.4f", dividedDifferences[i][j]);
//            }
//            System.out.println();
//        }

        double result = dividedDifferences[0][0];
        double term = 1.0;

        for (int i = 1; i < amount; i++) {
            term *= (t - x[i - 1]);
            result += dividedDifferences[0][i] * term;
        }

        return result;
    }
}
