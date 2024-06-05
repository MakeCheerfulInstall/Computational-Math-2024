package org.example;

public class Gauss {

    public double solve(double[] x, double[] y, int amount, double t) {
        int mid_index = amount / 2;
        double h = x[1] - x[0];
        double[][] centralDifferencesTable = computeDifferences(y);
        double f1 = firstInterpolationGaussForm(amount, x, y, mid_index, h, centralDifferencesTable, t);
        double f2 = secondInterpolationGaussForm(amount, x, y, mid_index, h, centralDifferencesTable, t);
        return (t > x[mid_index]) ? f1 : f2;
    }

    public double firstInterpolationGaussForm(int n, double[] x, double[] y, int mid_index, double h, double[][] centralDifferencesTable, double value) {
        double t = (value - x[mid_index]) / h;
        double result = y[mid_index];
        double term = 1.0;
        double tProduct = t;

        for (int i = 1; i < n; i++) {
            term *= tProduct / i;
            if (i % 2 == 0) {
                tProduct *= (t - i / 2);
            } else {
                tProduct *= (t + (i - 1) / 2);
            }
            int index = mid_index - (i / 2);
            if (index < 0 || index >= n) continue;
            result += term * centralDifferencesTable[index][i];
        }

        return result;
    }

    public double secondInterpolationGaussForm(int n, double[] x, double[] y, int mid_index, double h, double[][] centralDifferencesTable, double value) {
        double t = (value - x[mid_index]) / h;
        double result = y[mid_index];
        double term = 1.0;
        double tProduct = t;

        for (int i = 1; i < n; i++) {
            term *= tProduct / i;
            if (i % 2 != 0) {
                tProduct *= (t + i / 2);
            } else {
                tProduct *= (t - i / 2);
            }
            int index = mid_index + (i - 1) / 2;
            if (index < 0 || index >= n) continue;
            result += term * centralDifferencesTable[index][i];
        }

        return result;
    }

    public double[][] computeDifferences(double[] y) {
        int amount = y.length;
        double[][] differences = new double[amount][amount];

        for (int i = 0; i < amount; i++) {
            differences[i][0] = y[i];
        }

        for (int j = 1; j < amount; j++) {
            for (int i = 0; i < amount - j; i++) {
                differences[i][j] = differences[i + 1][j - 1] - differences[i][j - 1];
            }
        }

        return differences;
    }

    public static void printDifferencesTable(double[] x, double[][] differences) {
        int amount = x.length;

        System.out.println("Таблица конечных разностей:");
        System.out.printf("%-10s", "x");
        for (int j = 0; j < amount; j++) {
            System.out.printf("%-10s", "Δ^" + j + "y");
        }
        System.out.println();

        for (int i = 0; i < amount; i++) {
            System.out.printf("%-10.4f", x[i]);
            for (int j = 0; j < amount - i; j++) {
                System.out.printf("%-10.4f", differences[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Gauss gauss = new Gauss();
        int n = 5;
        double[] x = new double[n];
        double[] y = new double[n];
        double start = 1;
        double end = 2;
        double step = (end - start) / (n - 1);

        for (int i = 0; i < n; i++) {
            x[i] = start + i * step;
            y[i] = Math.log(x[i]);
        }

        printDifferencesTable(x, gauss.computeDifferences(y));
        double result = gauss.solve(x, y, n, 1.8);
        System.out.println("Приближенное значение функции в точке 1.8: " + result);
    }
}
