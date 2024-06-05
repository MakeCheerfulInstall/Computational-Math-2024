package org.example;

public class Functions {
    public double getFunction(double x, int functionNumber) {
        switch (functionNumber) {
            case (1) -> {
                return Math.sin(x);
            }
            default -> {
                return x*x;
            }
        }
    }
    public double []getPoints(double [] x, int amount, int functionNumber){
        double [] points = new double[amount];
        for (int i = 0; i < amount; i++){
            points[i] = getFunction(x[i], functionNumber);
        }
        return points;
    }
}
