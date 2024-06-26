package com.example.lab5.methods;

import java.util.ArrayList;

public class BesselPolynomial implements Method{
    private ArrayList<ArrayList<Double>> calculateDiff(ArrayList<Double> arrayOfY){
        ArrayList<ArrayList<Double>> tempDefy = new ArrayList<>();
        tempDefy.add(new ArrayList<>(arrayOfY));
        for (int i = 1; i < arrayOfY.size(); i++) {
            ArrayList<Double> column = new ArrayList<>();
            ArrayList<Double> previousColumn = tempDefy.get(i - 1);
            for (int j = 0; j < arrayOfY.size() - i; j++) {
                column.add(previousColumn.get(j + 1) - previousColumn.get(j));
            }
            tempDefy.add(column);
        }
        return tempDefy;
    }    
    public double calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, double x, double[][] arrayFinite) {
        int n = arrayOfX.size();
        int size = arrayOfX.size();
        double tSub = (x - arrayOfX.get(n / 2)) / (arrayOfX.get(1) - arrayOfX.get(0));
        ArrayList<ArrayList<Double>> defy = calculateDiff(arrayOfY);
        if ((size % 2 != 0) || (Math.abs(tSub) > 0.75) || (Math.abs(tSub) < 0.25)) {
            return 0.0;
        }
        for (int i = 0; i < n; i++) {
            defy.get(i).set(0, arrayOfY.get(i));
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                defy.get(j).set(i, defy.get(j + 1).get(i - 1) - defy.get(j).get(i - 1));
            }
        }
        n = arrayOfX.size() - 1;
        int center = n / 2;
        double a = arrayOfX.get(center);
        double t = (x - a) / (arrayOfX.get(1) - arrayOfX.get(0));
        double result = (defy.get(center).get(0) + defy.get(center + 1).get(0)) * 0.5 + (t - 0.5) * defy.get(center).get(1) + t * (t - 1) * 0.5 * (defy.get(center - 1).get(2) + defy.get(center).get(2)) * 0.5;
        double term = t * (t - 1) / 2;
        for (int k = 3; k < n + 1; k++) {
            if (k % 2 == 0) {
                term /= (t - 0.5);
                term *= (t + (k * 0.5 - 1)) * (t - (k * 0.5)) / k;
                result += term * (defy.get((int) (center - 1 - (k * 0.5 - 1))).get(k) + defy.get((int) (center - (k * 0.5 - 1))).get(k)) / 2;
            } else {
                term *= (t - 0.5) / k;
                result += term * defy.get(center - k / 2).get(k);
            }
        }
        return result;
//        double t = (x - arrayOfX.get(arrayOfX.size() / 2))/(arrayOfX.get(1) - arrayOfX.get(0));
//        double result = (arrayOfY.get(0) + arrayOfY.get(1))/2 + ((t - 0.5) * arrayFinite[arrayOfX.size() / 2][1]);
//        double term = t * (t - 1);
//        int bottomTerm = 1;
//        if (isCalculable( arrayOfX, x)){
//            for(int n = 1; n < arrayOfX.size() / 2; n++){
//                if (n != 1){
//                    term *= (t + n - 1) * (t - n);
//                }
//                bottomTerm *= (2 * n);
//                result += term / bottomTerm *
//                        ((arrayFinite[arrayOfX.size() / 2 - n][2 * n] +
//                                arrayFinite[arrayOfX.size() / 2 - n + 1][2 * n]) / 2) ;
//
//                bottomTerm *= (2 * n + 1);
//                result += (t - 0.5) * term / bottomTerm *
//                        (arrayFinite[arrayOfX.size() / 2 - n][2 * n + 1]);
//            }
//            return result;
//        }
//        return 0.0;
    }
    public String nameOfMethod(){
        return "Полином Беса";
    }

    @Override
    public boolean isCalculable(ArrayList<Double> arrayOfX, double x){
        double t = (x - arrayOfX.get(arrayOfX.size() / 2))/(arrayOfX.get(1) - arrayOfX.get(0));
        return Math.abs(t) >= 0.25 && Math.abs(t) <= 0.75 && arrayOfX.size()%2 == 0;
    }
}
