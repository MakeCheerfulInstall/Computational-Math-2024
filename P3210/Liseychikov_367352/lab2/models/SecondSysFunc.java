package lab2.models;

import lab2.util.FuncX;

import java.util.ArrayList;

public class SecondSysFunc implements ISysFunc {
    @Override
    public ArrayList<FuncX> getDraw() {
        ArrayList<FuncX> ar = new ArrayList<>();
        ar.add(x -> Math.cos(x) + 2);
        ar.add(x -> -Math.cos(x / 8 - 0.5 * x * x) / 3 - 2 * x / 3 + 58.5 / 6);
        return ar;
    }

    @Override
    public String getMessage() {
        ArrayList<String> ar = new ArrayList<>();
        ar.add("y - cos(x) = 2");
        ar.add("6y + 2cos(x/8 - 0.5 * x^2) + 4x = 58.5");
        return ISysFunc.toString(ar);
    }

    @Override
    public double f1(double[] x) {
        return x[1] - Math.cos(x[0]) - 2;
    }

    @Override
    public double f2(double[] x) {
        return 6 * x[1] + 2 * Math.cos(x[0] / 8 - 0.5 * x[0] * x[0]) + 4 * x[0] - 58.5;
    }

    @Override
    public double[][] derivativeForNewton(double[] x) {
        return new double[][]{
                {Math.sin(x[0]), 1},
                {-2 * Math.sin(x[0] / 8 - 0.5 * x[0] * x[0]) * (1 / 8 - x[0]) + 4, 6}};
    }
}
