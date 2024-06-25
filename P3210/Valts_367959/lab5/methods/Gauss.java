package lab5.methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Gauss extends Polynomial {

    private final Map<Integer, List<Double>> deltas = new HashMap<>();
    private final Map<Integer, Double> xes = new HashMap<>();

    @Override
    public double execute() {
        double x = getX();
        ArrayList<Double[]> values = getValues();
        double result;
        double a = values.get((values.size() - 1) / 2)[0];
        int n = values.size();

        for (int j = 0, i = -(n / 2); i <= n / 2; i++, j++) {
            xes.put(i, values.get(j)[0]);

            var temp = new ArrayList<Double>();
            temp.add(values.get(j)[1]);
            deltas.put(i, temp);
        }
        for (int k = 0, i = 0; i < n; i++, k++) {
            for (int j = -(n / 2); j < n / 2 - k; j++) {
                Double fd = deltas.get(j).get(i);
                Double sd = deltas.get(j + 1).get(i);
                deltas.get(j).add(sd - fd);
            }
        }

        if (x > a) {
            result = firstFormula();
        } else {
            result = secondFormula();
        }

        return result;
    }

    private double firstFormula() {
        double x = getX();
        ArrayList<Double[]> values = getValues();
        int n = values.size();
        double h = values.get(1)[0] - values.get(0)[0];
        double t = (x - values.get((n + 1) / 2 - 1)[0]) / h;
        double result = 0;
        double tkoeff = 1;
        for (int j = 1, i = 0; j < n - 1; i--, j += 2) {
            double delta1 = deltas.get(i).get(j - 1);
            double delta2 = deltas.get(i).get(j);
            tkoeff = 1;
            for (int k = 0; k < j - 1; k++) {
                tkoeff *= t + (k % 2 == 0 ? k : -k);
            }
            result += delta1 * tkoeff / factorial(j - 1);
            tkoeff *= t + j / 2;
            result += delta2 * tkoeff / factorial(j);
        }
        tkoeff *= t - n / 2;
        result += deltas.get(-(n/2)).get(n-1) * tkoeff / factorial(n - 1);

        return result;
    }

    private double secondFormula() {
        double x = getX();
        ArrayList<Double[]> values = getValues();
        int n = values.size();
        double h = values.get(1)[0] - values.get(0)[0];
        double t = (x - values.get((n + 1) / 2 - 1)[0]) / h;
        double result = deltas.get(0).get(0);
        double temp = 1;
        for (int j = 2, i = -1, f = 0, s = 1; j <= n - 1; i--, j += 2, f--, s++) {
            double delta1 = deltas.get(i).get(j - 1);
            double delta2 = deltas.get(i).get(j);

            temp *= t + f;
            result += delta1 * temp / factorial(j - 1);
            temp *= t + s;
            result += delta2 * temp / factorial(j);
        }

        return result;
    }

    private long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }
}
