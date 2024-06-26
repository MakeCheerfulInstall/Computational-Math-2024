package com.example.lab4.methods;
import lombok.Getter;
import java.util.ArrayList;
@Getter
public class LineApproximation extends Method {
    private Double a;
    private Double b;
    protected Double S = 0.0;
    protected Double sko;
    protected Double korrelPirs;
    protected Double determ;

     public double f(double x){
        return a*x + b;
    }

    @Override
    public void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n){
        double sumX = 0;
        double sumXX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumYY = 0;
        for (int i = 0; i < n; i++){
            sumX += arrayOfX.get(i);
            sumXX += Math.pow(arrayOfX.get(i), 2);
            sumY += arrayOfY.get(i);
            sumXY += arrayOfX.get(i) *arrayOfY.get(i);
            sumYY += arrayOfY.get(i) * arrayOfY.get(i);
        }
        a = (sumXY*n - sumX*sumY)/(sumXX*n - sumX*sumX);
        b = (sumXX*sumY - sumX*sumXY)/(sumXX*n - sumX*sumX);

        for (int i = 0; i < n; i++){
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(arrayOfX.get(i));
            tmp.add(arrayOfY.get(i));
            tmp.add(f(arrayOfX.get(i)));
            tmp.add(f(arrayOfX.get(i)) - arrayOfY.get(i));
            table.add(tmp);
            S += Math.pow(f(arrayOfX.get(i)) - arrayOfY.get(i), 2);
        }

        sko = Math.sqrt(S/n);

        korrelPirs = (sumXY * n - sumX * sumY) / Math.sqrt((sumXX * n - sumX * sumX) * (sumYY * n - sumY * sumY));
        determ = Math.pow(korrelPirs ,2);
    }

    @Override
    public String getNameMethod() {
        return "Линейная аппроксимация";
    }

    @Override
    protected String getStringFun() {
        return "phi(x)="+ a +" * x +" + b + "\nКоэффициент Пирсона " + korrelPirs;
    }
}
