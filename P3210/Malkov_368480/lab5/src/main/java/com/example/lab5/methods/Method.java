package com.example.lab5.methods;

import lombok.Getter;

import java.util.ArrayList;

public interface Method {
    double calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double xValue, double[][] arrayFinite);
    String nameOfMethod();
    boolean isCalculable(ArrayList<Double> arrayOfX, double x);
    default boolean getIsDrawing(){
        return false;
    }
}
