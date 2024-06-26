package com.example.lab4.methods;

import lombok.Getter;

import java.util.ArrayList;
@Getter
public abstract class Method {
    ArrayList<ArrayList<Double>> table = new ArrayList<>();
    public abstract double f(double x);

    protected Double S = 0.0;

    protected Double sko;

    protected Double korrelPirs;

    protected Double determ;
    public abstract void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n);

    public String getAnswer(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s%-10s%-10s%s\n", "X", "Y", "PHI(X)", "Ei"));
        int length = table.size();
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%-10s%-10s%-10s%s\n",String.format("%.5f",table.get(i).get(0)),String.format("%.5f",table.get(i).get(1)),
                    String.format("%.5f",table.get(i).get(2)),String.format("%.5f",table.get(i).get(3))));
        }
        return getNameMethod() +
                "\nФункция вида: " + getStringFun()
                +"\nСКО: " + getSko() +
                "\nКоэффициент детерминации: " + getDeterm() +
                "\nМера отклонения: " + getS() +
                "\n" + sb;
    }
    public String getNameMethod() {
        return "Я ЕСМЬ ТИП ФУНКЦИИ";
    }

    protected String getStringFun() {
        return "Я ЕСМЬ ФУНКЦИЯ";
    }
}
