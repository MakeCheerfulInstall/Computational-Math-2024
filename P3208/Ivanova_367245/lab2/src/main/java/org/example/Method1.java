package org.example;

public class Method1 {

    Functions functions = new Functions();
    private int iterations = 0;

    private double x = 0;
    private int equation = 0;

    public double solve(double a, double b, int functionChoice, double epsilon) {

        equation = functionChoice;

        if (functions.getFunction(a, functionChoice) * functions.getFunction(b, functionChoice) >= 0) {
            throw new IllegalArgumentException("Условие теоремы о промежуточном значении не выполнено.");

        } else {
            x = a;
            while ((b - a) >= epsilon) {
                x = (a + b) / 2;
                if (functions.getFunction(x, functionChoice) == 0.0) {
                    break;
                } else if (functions.getFunction(x, functionChoice) * functions.getFunction(a, functionChoice) < 0) {
                    b = x;
                } else {
                    a = x;
                }
                iterations++;
            }
            return x;
        }


    }
    public double getFunctionValue(){
        return functions.getFunction(x, equation);
    }

    public int getIterations(){
        return iterations;
    }
}
