package com.example.lab4.methods;
import lombok.Getter;
import java.util.ArrayList;
@Getter
public class LogarithmApproximation extends Method {
    private Double a;
    private Double b;
    public double f(double x) {
        return a * Math.log(x) + b;
    }

    @Override
    public void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n) {
        double sumLnX = 0;
        double sumY = 0;
        double sumLnX2 = 0;
        double sumYLnX = 0;

        for (int i = 0; i < n; i++) {
            double x = arrayOfX.get(i);
            double y = arrayOfY.get(i);
            double lnX = Math.log(x);

            sumLnX += lnX;
            sumY += y;
            sumLnX2 += lnX * lnX;
            sumYLnX += y * lnX;
        }

        double b = (sumY * sumLnX2 - sumLnX * sumYLnX) / (n * sumLnX2 - sumLnX * sumLnX);
        double a = (n * sumYLnX - sumLnX * sumY) / (n * sumLnX2 - sumLnX * sumLnX);

        this.a = a;
        this.b = b;
        double phiAvg = 0;

        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();
            row.add(arrayOfX.get(i));
            row.add(arrayOfY.get(i));
            phiAvg += f(arrayOfX.get(i));
            row.add(f(arrayOfX.get(i)));
            row.add(f(arrayOfX.get(i)) - arrayOfY.get(i));
            table.add(row);
            S += Math.pow(f(arrayOfX.get(i)) - arrayOfY.get(i), 2);
        }
        sko = Math.sqrt(S/n);

        phiAvg = phiAvg / n;
        double ssTot = 0;
        double ssRes = 0;

        for (int i = 0; i < n; i++) {
            double fi = f(arrayOfX.get(i));
            ssTot += Math.pow(arrayOfY.get(i) - phiAvg, 2);
            ssRes += Math.pow(arrayOfY.get(i) - fi, 2);
        }


        determ = 1 - (ssRes / ssTot);
    }

    @Override
    public String getNameMethod() {
        return "Логарифмическая аппроксимация";
    }
    @Override
    protected String getStringFun() {
        return "phi(x)="+a+" * log(x) + " + b ;
    }
}
