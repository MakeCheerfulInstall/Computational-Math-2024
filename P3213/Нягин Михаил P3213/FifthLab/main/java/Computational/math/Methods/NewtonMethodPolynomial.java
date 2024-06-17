package Computational.math.Methods;

import Computational.math.Utils.FunctionalTable;

import java.util.Arrays;

public class NewtonMethodPolynomial extends AbstractMethod{
    public NewtonMethodPolynomial() {
        super("Метод Ньютона с разделенными разностями");
    }

    @Override
    public Double apply(FunctionalTable functionalTable, double x_current) {
        var a = newtonCoefficient(functionalTable.getxArr(),functionalTable.getyArr());
        var x = functionalTable.getxArr();
        var n = functionalTable.getxArr().length - 1;
        var p = a[n];
        for (int k = 1; k < n+1; k++) {
            p = a[n - k] + (x_current - x[n - k])*p;

        }
        return p;
    }
    public Double[] newtonCoefficient(Double[] x, Double[] y) {
        int m = x.length;
        Double[] yCopy = Arrays.copyOf(y, m);
        for (int k = 1; k < m; k++) {
            for (int j = m - 1; j >= k; j--) {
                yCopy[j] = (yCopy[j] - yCopy[j - 1]) / (x[j] - x[j - k]);
            }
        }
        return yCopy;
    }
}
