package com.example.lab5.methods;

import java.util.ArrayList;

public abstract class GaussPrototype {
    protected double firstForm(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite){
        double midPoint = arrayOfX.get(arrayOfX.size() / 2);
        double t = (x - midPoint)/(arrayOfX.get(1) - arrayOfX.get(0));
        double result = arrayFinite[arrayOfX.size() / 2][0];

        double term = t;
        int bottomTerm = 1;
        if (arrayOfX.size() % 2 == 1){
            for(int n = 1; n <= arrayOfX.size() / 2; n++){
                if (n != 1){
                    term *= (t + n - 1);
                }
                bottomTerm *= (2*n - 1);
                result += term / bottomTerm * arrayFinite[Math.abs(arrayOfX.size() / 2) - n + 1][2 * n - 1];

                term *= (t - n);
                bottomTerm *= (2*n);
                result += term / bottomTerm * arrayFinite[Math.abs(arrayOfX.size() / 2) - n][2 * n];
            }
        }

        return result;
    }
    protected double secondForm(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite){
        double midPoint = arrayOfX.get(arrayOfX.size() / 2);
        double t = (x - midPoint)/(arrayOfX.get(1) - arrayOfX.get(0));
        double result = arrayFinite[arrayOfX.size() / 2][0];

        double term = 1.0;
        int bottomTerm = 1;
        if (arrayOfX.size() % 2 == 1){
            for(int n = 1; n <= arrayOfX.size() / 2; n++){
                if (n == 1){
                    term *= t;
                }else{
                    term *= (t - n + 1);
                }
                bottomTerm *= (2*n - 1);
                result += term / bottomTerm * arrayFinite[Math.abs(arrayOfX.size() / 2) - n][2 * n - 1];

                term *= (t + n);
                bottomTerm *= (2*n);
                result += term / bottomTerm * arrayFinite[Math.abs(arrayOfX.size() / 2) - n][2 * n];
            }
        }
        return result;
    }
}
