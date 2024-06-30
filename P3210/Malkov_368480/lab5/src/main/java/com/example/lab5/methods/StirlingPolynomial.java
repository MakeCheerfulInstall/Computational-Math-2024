package com.example.lab5.methods;

import java.util.ArrayList;

public class StirlingPolynomial extends GaussPrototype implements Method {
    public double calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite) {
        double t = (x -  arrayOfX.get(arrayOfX.size() / 2))/(arrayOfX.get(1) - arrayOfX.get(0));
        if (isCalculable(arrayOfX, x)){
            return (firstForm(arrayOfX, arrayOfY, x, arrayFinite) + secondForm(arrayOfX, arrayOfY, x, arrayFinite)) / 2;
        }
        return 0.0;
    }

    public String nameOfMethod(){
        return "Полином Стирлинга";
    }
    @Override
    public boolean isCalculable(ArrayList<Double> arrayOfX, double x){
        double t = (x - arrayOfX.get(arrayOfX.size() / 2))/(arrayOfX.get(1) - arrayOfX.get(0));
        return arrayOfX.size()%2 == 1 && Math.abs(t) <= 0.25;
    }
}
