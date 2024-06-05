package org.example;

import static java.lang.Math.abs;

//метод левых прямоугольников
public class Method1 {
    Functions functions = new Functions();

    private int maxIteration = 4;
    public double solve(double a, double b, int functionChoice, double epsilon){
        double h = (b - a)/4;
        double x = a;
        double integral1 = 0;
        double integral2 = 1;
        maxIteration = 4;
        while (Math.abs(integral2 - integral1) > epsilon) {
            x = a;
            integral2 = integral1;
            integral1 = 0;
            for (int i = 0; i <= maxIteration; i++) {
                integral1 += h * functions.getFunction(functionChoice, x);
                x += h;
            }
            maxIteration *= 2;
            h = (b-a)/maxIteration;
        }
        return integral2;
    }

    public int getMaxIteration(){
        return maxIteration/2;
    }

}
