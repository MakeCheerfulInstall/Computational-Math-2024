package org.example;

//метод средних прямоугольников
public class Method3 {

    private int maxIteration = 4;
    Functions functions = new Functions();
    public double solve(double a, double b, int functionChoice, double epsilon){
        double h = (b - a)/4;
        double x = h/2;
        double integral1 = 0;
        double integral2 = 1;
        while (Math.abs(integral2 - integral1) > epsilon) {
            x = h/2;
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
