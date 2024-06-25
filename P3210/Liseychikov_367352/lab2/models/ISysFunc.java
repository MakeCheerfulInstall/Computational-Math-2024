package lab2.models;

import lab2.util.FuncX;

import java.util.ArrayList;

public interface ISysFunc {
    ArrayList<FuncX> getDraw();

    static String toString(ArrayList<String> arrayList) {
        StringBuilder result = new StringBuilder("|");
        for (String it : arrayList) {
            result.append(it).append("\n   |");
        }
        result.delete(result.length() - 5, result.length());
        return result.toString();
    }

    String getMessage();

    double[][] derivativeForNewton(double[] x);

    double f2(double[] x);

    double f1(double[] x);
}
