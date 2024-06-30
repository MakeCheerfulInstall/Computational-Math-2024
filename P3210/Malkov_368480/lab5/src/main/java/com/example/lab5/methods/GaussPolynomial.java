package com.example.lab5.methods;

import lombok.Getter;

import java.util.ArrayList;

public class GaussPolynomial extends GaussPrototype implements Method {
    @Override
    public boolean getIsDrawing(){
        return true;
    }

    public double calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite) {
        double midPoint = arrayOfX.get(arrayOfX.size() / 2);
        double result;
        if(x > midPoint){
            result = firstForm(arrayOfX, arrayOfY, x, arrayFinite);
        }
        else {
            result = secondForm(arrayOfX, arrayOfY, x, arrayFinite);
        };
        return result;
    }
    public String nameOfMethod(){
        return "Полином Гаусса";
    }
    @Override
    public boolean isCalculable(ArrayList<Double> arrayOfX, double x){
        return true;
    }
}
