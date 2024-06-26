package com.example.lab5.methods;

import lombok.Data;

import java.util.ArrayList;
@Data
public class LagrangePolynomial implements Method {
    @Override
    public boolean getIsDrawing(){
        return true;
    }
    public double calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite){
        double result = 0.0;

        for (int i = 0; i < arrayOfX.size(); i++) {
            double term = arrayOfY.get(i);
            for (int j = 0; j < arrayOfX.size(); j++) {
                if (i != j) {
                    term *= (x - arrayOfX.get(j)) / (arrayOfX.get(i) - arrayOfX.get(j));
                }
            }
            result += term;
        }

        return result;
    }
    public String nameOfMethod(){
        return "Полином Лагранжа";
    }
    @Override
    public boolean isCalculable(ArrayList<Double> arrayOfX, double x){
        return true;
    }
}
