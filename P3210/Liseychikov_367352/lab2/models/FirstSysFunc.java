package lab2.models;

import lab2.util.FuncX;

import java.util.ArrayList;

public class FirstSysFunc implements ISysFunc {
    @Override
    public ArrayList<FuncX> getDraw() {
        ArrayList<FuncX> ar = new ArrayList<>();
        ar.add(x -> -Math.sin(x) / 2 + 1);
        ar.add(x -> x - 2 * Math.cos(x - 1) + 0.7);
        return ar;
    }

    @Override
    public String getMessage() {
        ArrayList<String> ar = new ArrayList<>();
        ar.add("sin(x) + 2y = 2");
        ar.add("y - x + 2 * cos(x - 1) = 0.7");
        return ISysFunc.toString(ar);
    }

    @Override
    public double f1(double[] x) {
        return Math.sin(x[0]) + 2 * x[1] - 2;
    }

    @Override
    public double f2(double[] x) {
        return x[1] - x[0] + 2 * Math.cos(x[0] - 1) - 0.7;
    }

    @Override
    public double[][] derivativeForNewton(double[] x) {
        return new double[][]{
                {Math.cos(x[0]), 2},
                {-1 - x[0] * Math.sin(x[0] - 1), 1}};
    }
}