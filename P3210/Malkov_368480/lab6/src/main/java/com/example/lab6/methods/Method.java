package com.example.lab6.methods;

import com.example.lab6.Functions;

import java.util.ArrayList;

public interface Method {
    ArrayList<Double> calculate(double x0,double x_last, double h, double y0, Functions f, double eps, boolean log);
    String nameOfMethod();
    boolean isCalculable();
}
