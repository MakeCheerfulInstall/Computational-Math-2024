package org.example;

public class Method3 {
    private int iterations = 0;
    private double x;

    private int equation;
    Functions functions = new Functions();

    public double solve( int functionChoice, double epsilon, double a, double b) {
//        x = functions.getInitialApproximation(functionChoice);
        x = (a+b)/2;
        equation = functionChoice;
        while (Math.abs(functions.getFunction(x,functionChoice)) > epsilon) {
            x = x - functions.getFunction(x,functionChoice)/ functions.getDerivativeFunction(x, functionChoice);
            iterations++;
        }
        return x;
    }

    public int getIterations(){
        return iterations;
    }

    public double getFunctionValue(){
        return functions.getFunction(x, equation);
    }
}
