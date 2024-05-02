package org.example;

public class Method5 {
    Functions functions = new Functions();

    private int equation;
    private int iterations;

    private double x;

    public double solve(int functionChoice, double epsilon, double a, double b) {
        equation = functionChoice;
//        x = functions.getInitialApproximation(functionChoice);
        double prev = (a+b)/2;
        x = functions.getNextApproximation(prev, functionChoice);
        iterations = 0;

        while (Math.abs(prev - x) > epsilon) {
            prev = x;
            x = functions.getNextApproximation(x, functionChoice);
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

    public boolean checkShodimost(double a, double b, int number){
        if (functions.getFiDerivative(Math.max(Math.abs(a),Math.abs(b)), number)>1){
            System.out.println("Условие сходимости не выполняется на данном интервале.");
            return false;
        }else {
            System.out.println("Условие сходимости выполняется.");
            return true;
        }
    }

}
