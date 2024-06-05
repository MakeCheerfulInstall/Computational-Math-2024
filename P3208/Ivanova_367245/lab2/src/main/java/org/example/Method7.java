package org.example;

public class Method7 {
    Functions functions = new Functions();

    int choice;
    private int iterations;

    private double [] pogreshnostx = new double[10];
    private double [] pogreshnosty = new double[10];


    private static final int MAX_ITERATIONS = 1000;
    public  double[] solve(double initialGuessX, double initialGuessY, int systemChoice, double epsilon) {
        double x = initialGuessX;
        double y = initialGuessY;
        double [] solution = new double[2];
        iterations = 0;

        while (iterations < MAX_ITERATIONS) {
            double newX = functions.getNextApproximationForX(x, y, systemChoice);
            double newY = functions.getNextApproximationForY(x, y, systemChoice);
            if (Math.max(Math.abs(newX - x),Math.abs(newY - y)) < epsilon) {
                pogreshnostx[iterations] = Math.abs(newX - x);
                pogreshnosty[iterations] =Math.abs(newY - y);
                break;
            }
            pogreshnostx[iterations] = Math.abs(newX - x);
            pogreshnosty[iterations] =Math.abs(newY - y);
            x = newX;
            y = newY;
            iterations++;

        }

        solution[0] = x;
        solution[1] = y;
        return solution;
    }

    public boolean checkShodimost(int number, double initialGuessX, double initialGuessY){
        if (Math.max(functions.getSystemFiDerivative1(initialGuessX, initialGuessY, number),(functions.getSystemFiDerivative2(initialGuessX, initialGuessY, number)))>1){
            System.out.println("Условие сходимости не выполняется на данном интервале.");
            return false;
        }else {
            System.out.println("Условие сходимости выполняется.");
            return true;
        }
    }

    public int getIterations(){
        return iterations;
    }

    public double [] getPogreshnostx(){
        return pogreshnostx;
    }

    public double [] getPogreshnosty(){
        return pogreshnosty;
    }



}
