package com.example.lab6.methods;

import com.example.lab6.Functions;

import java.util.ArrayList;
import java.util.Arrays;

public class RungeKutteMethod implements Method{

    @Override
    public ArrayList<Double> calculate(double x0, double x_last, double h, double y0, Functions f, double eps, boolean log) {
        ArrayList<Double> xValues = new ArrayList<>(Arrays.asList(x0));
        ArrayList<Double> yValues = new ArrayList<>(Arrays.asList(y0));

        Double yPrev = null; int itFactor = 1;
        var h1 = h;
        while (true) {
            double x = x0, y = y0;
            while(x < x_last) {
                var k1 = h * f.getFunction().apply(x, y);
                var k2 = h * f.getFunction().apply(x + 0.5 * h, y + 0.5 * k1);
                var k3 = h * f.getFunction().apply(x + 0.5 * h, y + 0.5 * k2);
                var k4 = h * f.getFunction().apply(x + h, y + k3);
                y += (k1 + 2 * k2 + 2 * k3 + k4) / 6;
                x += h;
                if (x - xValues.get(xValues.size() - 1) == h1) {
                    yValues.add(y);
                    xValues.add(x);
                }
            }

            if (yPrev != null && Math.abs(y - yPrev) <= eps * (Math.pow(2, 4) - 1) || itFactor >= 1024) {
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

    @Override
    public String nameOfMethod() {
        return "Метод Рунге-Кутта 4- го порядка.";
    }

    @Override
    public boolean isCalculable() {
        return true;
    }
}
