package org.example;

//метод Симпсона
public class Method5 {


    private int maxIteration = 4;
    Functions functions = new Functions();
    public double solve(double a, double b, int functionChoice, double epsilon){
        double h = (b - a)/4;
        double x = a;
        double integral1 = 0;
        double integral2 = 1;
        while (Math.abs(integral2 - integral1) > epsilon) {
            x = a;
            double [] y = new double[maxIteration+1];
            integral2 = integral1;
            integral1 = 0;
            for (int i = 0; i <= maxIteration; i++) {
                y[i] = functions.getFunction(functionChoice, x);
                x+=h;
            }
            double sum1 = 0;
            double sum2 = 0;
            for (int i = 1; i < maxIteration; i=i+2){
                sum1+=y[i];
            }
            for (int i = 1; i <= maxIteration/2 -1 ; i++ ){
                sum2 += y[i*2];
            }
            integral1 = h/3*(y[0]+4*sum1+2*sum2+y[maxIteration]);
            maxIteration *= 2;
            h = (b-a)/maxIteration;

        }
        return integral2;
    }

    public int getMaxIteration(){
        return maxIteration/2;
    }

}
