package com.example.lab5.methods;

import java.util.ArrayList;

public class NewtonPolynomial implements Method {
    @Override
    public boolean getIsDrawing(){
        return true;
    }
    private double dividedDifference(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int start, int end) {
        if (start == end) {
            return arrayOfY.get(start);
        } else {
            return (dividedDifference(arrayOfX, arrayOfY, start+1, end)
                    - dividedDifference(arrayOfX, arrayOfY, start, end-1))
                    / (arrayOfX.get(end) - arrayOfX.get(start));
        }
    }

    public double calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite) {
        double result = arrayOfY.get(0);
        for (int i = 1; i < arrayOfX.size(); i++) {
            double term = dividedDifference(arrayOfX, arrayOfY, 0, i);
            for (int j = 0; j < i; j++) {
                term *= (x - arrayOfX.get(j));
            }
            result += term;
        }

        return result;
    }
    public String nameOfMethod(){
        return "Полином Ньютона";
    }
    @Override
    public boolean isCalculable(ArrayList<Double> arrayOfX, double x){
        return true;
    }
}
