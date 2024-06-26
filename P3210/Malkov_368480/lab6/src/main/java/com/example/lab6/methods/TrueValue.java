package com.example.lab6.methods;

import com.example.lab6.Functions;

import java.util.ArrayList;
import java.util.Arrays;

public class TrueValue implements Method{

    @Override
    public ArrayList<Double> calculate(double x0, double x_last, double h, double y0, Functions f, double eps, boolean log) {
        ArrayList<Double> yValues = new ArrayList<>();
        for(double i = x0; i <= x_last; i+=h){
            yValues.add(f.getTrueValue(x0,y0,i));
        }
        return yValues;
    }

    @Override
    public String nameOfMethod() {
        return "Истинное значение";
    }

    @Override
    public boolean isCalculable() {
        return true;
    }
}
