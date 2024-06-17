package Computational.math.Methods;

import Computational.math.Utils.FunctionalTable;

import static Computational.math.Utils.MathUtils.factorial;

public class NewtonMethodInterpolation extends AbstractMethod {
    public NewtonMethodInterpolation() {
        super("Метод Ньютона с конечными разностями");
    }
    @Override
    public Double apply(FunctionalTable functionalTable, double xCur) {
        Double[] x = functionalTable.getxArr();
        Double[] y = functionalTable.getyArr();
        int n = x.length;
        boolean isEquallySpaced = true;
        double h = x[1] - x[0];
        for (int i = 1; i < n - 1; i++) {
            if (round(x[i + 1] - x[i]) != h) {
                isEquallySpaced = false;
                break;
            }
        }
        if (!isEquallySpaced) {
            return Double.NaN;
        }

        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            a[i][0] = y[i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                a[j][i] = a[j + 1][i - 1] - a[j][i - 1];
            }
        }

        double result;
        if (xCur <= x[n / 2]) {
            int x0 = n - 1;
            for (int i = 0; i < n; i++) {
                if (xCur <= x[i]) {
                    x0 = i - 1;
                    break;
                }
            }
            if (x0 < 0) {
                x0 = 0;
            }
            double t = (xCur - x[x0]) / h;
            result = a[x0][0];
            for (int i = 1; i < n; i++) {
                result += (tCalc(t, i, true) * a[x0][i]) / factorial(i);
            }
        } else {
            double t = (xCur - x[n - 1]) / h;
            result = a[n - 1][0];
            for (int i = 1; i < n; i++) {
                result += (tCalc(t, i, false) * a[n - i - 1][i]) / factorial(i);
            }
        }
        return result;
    }

    private static double tCalc(double t, int i, boolean isForward) {
        double result = t;
        for (int j = 1; j < i; j++) {
            if (isForward) {
                result *= (t - j);
            } else {
                result *= (t + j);
            }
        }
        return result;
    }
    private double round(double num){
        return Double.parseDouble(String.format("%.3f",num).replace(",","."));
    }
}
