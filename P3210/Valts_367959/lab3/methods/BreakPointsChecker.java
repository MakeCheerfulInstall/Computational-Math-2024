package methods;

import storage.FunctionStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BreakPointsChecker {

    private final int MAX_BREAKPOINTS = 10000;

    public List<Double> getBreakPoints(Double a, Double b, int n) {
        List<Double> breakpoints = new ArrayList<>();
        try {
            var result = FunctionStorage.getFunction(a);
            throwException(result);
        } catch (ArithmeticException e) {
            breakpoints.add(a);
        }

        try {
            var result = FunctionStorage.getFunction(b);
            throwException(result);
        } catch (ArithmeticException e) {
            breakpoints.add(b);
        }

        try {
            var result = FunctionStorage.getFunction((a + b) / 2);
            throwException(result);
        } catch (ArithmeticException e) {
            breakpoints.add((a + b) / 2);
        }

        double h = (b - a) / n;

        for (int i = 0; i < n; i++) {
            double x = a + i * h;

            try {
                var result = FunctionStorage.getFunction(x);
                throwException(result);
            } catch (ArithmeticException e) {
                breakpoints.add(x);
                if (breakpoints.size() >= MAX_BREAKPOINTS) {
                    return  getBreakPoints(a, b, n / 10);
                }
            }
        }

        return new ArrayList<>(new HashSet<>(breakpoints));
    }

    private void throwException(Double result) {
        if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
            throw new ArithmeticException();
        }
    }

    public Double tryToCompute(double x) {
        try {
            var result = FunctionStorage.getFunction(x);
            throwException(result);
            return result;
        } catch (ArithmeticException e) {
            return null;
        }
    }
}
