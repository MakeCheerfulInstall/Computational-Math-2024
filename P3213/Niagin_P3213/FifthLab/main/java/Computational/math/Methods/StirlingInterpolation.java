package Computational.math.Methods;

import Computational.math.Utils.FunctionalTable;

import static Computational.math.Utils.MathUtils.factorial;

public class StirlingInterpolation extends AbstractMethod{
    public StirlingInterpolation() {
        super("Stirling Interpolation");
    }

    @Override
    public Double apply(FunctionalTable functionalTable, double xCur) {
        var x = functionalTable.getxArr();
        var y = functionalTable.getyArr();
        boolean isEquallySpaced = true;
        double h = Math.round((x[1] - x[0]) * 1000) / 1000.0;
        int n = x.length;
        for (int i = 1; i < n - 1; i++) {
            if (Math.round((x[i + 1] - x[i]) * 1000) / 1000.0 != h) {
                isEquallySpaced = false;
                break;
            }
        }
        if (!isEquallySpaced) {
            return Double.NaN; // Узлы не являются равноотстоящими
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
        int x0 = (n % 2 == 0) ? n / 2 - 1 : n / 2;
        double t = (xCur - x[x0]) / h;
        if(Math.abs(t) > 0.25)
            System.out.println(STR."t > 0.25 -> результат Стирлинга может быть не точным, t = \{String.format("%.3f",t)})");
        double result = a[x0][0];
        double compT1 = t;
        double compT2 = t * t;
        int prNumber = 0;
        for (int i = 1; i < n-1; i++) {
            if (i % 2 == 0) {
                result += (compT2 / factorial(i)) * a[x0 - (i / 2)][i];
                compT2 *= (t * t - prNumber * prNumber);
            } else {
                result += (compT1 / factorial(i)) * ((a[x0 - ((i + 1) / 2)][i] + a[x0 - (((i + 1) / 2) - 1)][i]) / 2);
                prNumber++;
                compT1 *= (t * t - prNumber * prNumber);
            }
        }
        return result;
    }
}
