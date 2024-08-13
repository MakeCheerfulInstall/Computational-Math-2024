package Computational.math.Methods;

import Computational.math.Utils.FunctionalTable;

import static Computational.math.Utils.MathUtils.factorial;

public class BesselInterpolation extends AbstractMethod{
    public BesselInterpolation() {
        super("Многочлен Бесселя");
    }

    @Override
    public Double apply(FunctionalTable functionalTable, double xCur) {
        var x = functionalTable.getxArr();
        var y =functionalTable.getyArr();
        boolean isEquallySpaced = true;
        double h = Math.round((x[1] - x[0]) * 1000) / 1000.0;
        int n = x.length;
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            a[i][0] = y[i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                a[j][i] = a[j + 1][i - 1] - a[j][i - 1];
            }
        }
        for (int i = 1; i < n - 1; i++) {
            if (Math.round((x[i + 1] - x[i]) * 1000) / 1000.0 != h) {
                isEquallySpaced = false;
                break;
            }
        }
        if (!isEquallySpaced) {
            return Double.NaN; // Узлы не являются равноотстоящими
        }
        int x0 = (n % 2 == 0) ? n / 2 - 1 : n / 2;
        double t = (xCur - x[x0]) / h;
        double result = (a[x0][0] + a[x0 + 1][0]) / 2;
        result += (t - 0.5) * a[x0][1];
        double compT = t;
        int lastNumber = 0;
        if(Math.abs(t) < 0.25 || Math.abs(t) > 0.75){
            System.out.println(STR."t < 0.25 или t > 0.75 => Результат может быть не точным, значение t = \{String.format("%.3f",t)}");
        }
        for (int i = 2; i < n; i++) {
            if (i % 2 == 0) {
                lastNumber++;
                compT *= (t - lastNumber);
                result += (compT / factorial(i)) * ((a[x0 - i / 2][i] + a[x0 - ((i / 2) - 1)][i]) / 2);
            } else {
                result += (compT * (t - 0.5) / factorial(i)) * a[x0 - ((i - 1) / 2)][i];
                compT *= (t + lastNumber);
            }
        }
        return result;
    }
}
