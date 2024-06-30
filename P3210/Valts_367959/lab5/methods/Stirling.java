package lab5.methods;

import static java.lang.Math.pow;

public class Stirling extends Polynomial{
    @Override
    public double execute() {
        var values = Polynomial.getValues();
        var x1 = Polynomial.getX();
        var n = values.size();
        double h, a, u;
        double y1 = 0, N1 = 1, d = 1,
                N2 = 1, d2 = 1, temp1 = 1,
                temp2 = 1, k = 1, l = 1, delta[][];

        delta = new double[n][n];
        int i, j, s;
        h = values.get(1)[0] - values.get(0)[0];
        s = n / 2;
        a = values.get(s)[0];
        u = (x1 - a) / h;

        // Preparing the forward difference
        // table
        for (i = 0; i < n - 1; ++i) {
            delta[i][0] = values.get(i + 1)[1] - values.get(i)[1];
        }
        for (i = 1; i < n - 1; ++i) {
            for (j = 0; j < n - i - 1; ++j) {
                delta[j][i] = delta[j + 1][i - 1]
                        - delta[j][i - 1];
            }
        }

        // Calculating f(x) using the Stirling
        // formula
        y1 = values.get(s)[1];

        for (i = 1; i <= n - 1; ++i) {
            if (i % 2 != 0) {
                if (k != 2) {
                    temp1 *= (pow(u, k) -
                            pow((k - 1), 2));
                }
                else {
                    temp1 *= (pow(u, 2) -
                            pow((k - 1), 2));
                }
                ++k;
                d *= i;
                s = (n - i) / 2;
                y1 += (temp1 / (2 * d)) *
                        (delta[s][i - 1] +
                                delta[s - 1][i - 1]);
            }
            else {
                temp2 *= (pow(u, 2) -
                        pow((l - 1), 2));
                ++l;
                d *= i;
                s = (n - i) / 2;
                y1 += (temp2 / (d)) *
                        (delta[s][i - 1]);
            }
        }
        return y1;
    }
}
