package com.example.lab6.methods;

import com.example.lab6.Functions;

import java.util.ArrayList;
import java.util.Arrays;

public class UpgradeEulerMethod implements Method {
    //второй порядок точности
    @Override
    public ArrayList<Double> calculate(double x0, double x_last, double h, double y0, Functions f, double eps, boolean log) {
        ArrayList<Double> xValues = new ArrayList<>(Arrays.asList(x0));
        ArrayList<Double> yValues = new ArrayList<>(Arrays.asList(y0));
        var h1 = h;
        Double yPrev = null; int itFactor = 1;
        while(true){
            double x = x0, y = y0;
            while( x < x_last){
                double k1 = f.getFunction().apply(x, y);
                double k2 = f.getFunction().apply(x + h, y + h * k1);
                y += (h/2) * (k1 + k2);
                x += h;
                if (x - xValues.get(xValues.size() - 1) == h1) {
                    yValues.add(y);
                    xValues.add(x);
                }
            }
            if (itFactor >= 8192 || yPrev != null && Math.abs(y - yPrev) <= eps * (Math.pow(2, 4) - 1) || itFactor >= 1024) {
                return yValues;
            }else{
                xValues = new ArrayList<>(Arrays.asList(x0));
                yValues = new ArrayList<>(Arrays.asList(y0));
            }
            yPrev = y;
            h = h / 2;
            itFactor *= 2;
        }
    }

    public String nameOfMethod(){
        return "Модифицированный метод Эйлера";
    }
    @Override
    public boolean isCalculable(){
        return true;
    }
}
