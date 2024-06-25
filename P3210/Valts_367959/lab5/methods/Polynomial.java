package lab5.methods;

import java.util.ArrayList;

public abstract class Polynomial {
    private static double x;
    private static ArrayList<Double[]> values;

    public static double getX() {
        return x;
    }

    public static void setX(double x) {
        Polynomial.x = x;
    }

    public static ArrayList<Double[]> getValues() {
        return values;
    }

    public static void setValues(ArrayList<Double[]> values) {
        Polynomial.values = values;
    }

    abstract double execute();
}
