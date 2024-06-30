package com.example.lab6.methods;

import com.example.lab6.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdamsMethod implements Method{
    // порядок точности 4
    @Override
    public ArrayList<Double> calculate(double x0, double x_last, double h, double y0, Functions f, double eps, boolean log) {
        ArrayList<Double> xValues = new ArrayList<>(List.of(x0));
        ArrayList<Double> yValues = new ArrayList<>(List.of(y0));
        ArrayList<Double> resultValues = new ArrayList<>(List.of(y0));
        var accuracy = -1.0;
        int itFactor = 1;
        var h1 = h;
        while (true){
            double x = x0, y = y0;
            double x1 = x0;
            for (int i = 1; i < 4; i++) {
                var k1 = h * f.getFunction().apply(x, y);
                var k2 = h * f.getFunction().apply(x + 0.5 * h, y + 0.5 * k1);
                var k3 = h * f.getFunction().apply(x + 0.5 * h, y + 0.5 * k2);
                var k4 = h * f.getFunction().apply(x + h, y + k3);
                y += (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                x += h;
                yValues.add(y);
                xValues.add(x);
                if (x - x1 == h1) {
                    resultValues.add(y);
                    var realY = f.getTrueValue(x0, y0, x);
                    accuracy = Math.max(accuracy, Math.abs(y - realY));
                    x1 = x;
                }
            }
            // остальные шаги методом Адамса-Башфорта
            while(xValues.get(xValues.size()-1) < x_last){
                double prX =  xValues.get(xValues.size()-1);
                double prXX = xValues.get(xValues.size() - 2);
                double prXXX = xValues.get(xValues.size() - 3);
                double prXXXX = xValues.get(xValues.size() - 4);
                double prY = yValues.get(yValues.size() - 1);
                double prYY = yValues.get(yValues.size() - 2);
                double prYYY = yValues.get(yValues.size() - 3);
                double prYYYY = yValues.get(yValues.size() - 4);

                double nextFVal = prY + h / 24 * (
                        55 * f.getFunction().apply(prX,prY) -
                                59 * f.getFunction().apply(prXX,prYY) +
                                37 * f.getFunction().apply(prXXX,prYYY) -
                                9 * f.getFunction().apply(prXXXX,prYYYY));
                y = prY + h / 24 * (
                        9 * f.getFunction().apply(x + h, nextFVal) +
                                19 * f.getFunction().apply(prX,prY) -
                                5 * f.getFunction().apply(prXX,prYY) +
                                f.getFunction().apply(prXXX,prYYY));
                x = x + h;
                xValues.add(x);
                yValues.add(y);
                if (x - x1 == h1) {
                    resultValues.add(y);
                    var realY = f.getTrueValue(x0, y0, x);
                    accuracy = Math.max(accuracy, Math.abs(y - realY));
                    x1 = x;
                }
            }
            if (accuracy <= eps || itFactor >= 8192){
                return resultValues;
            }else{
                xValues = new ArrayList<>(List.of(x0));
                yValues = new ArrayList<>(List.of(y0));
                resultValues = new ArrayList<>(List.of(y0));
            }
            h = h / 2;
            itFactor *= 2;
            accuracy = -1.0;
        }
    }

    @Override
    public String nameOfMethod() {
        return "Метод Адамса";
    }

    @Override
    public boolean isCalculable() {
        return true;
    }
}
