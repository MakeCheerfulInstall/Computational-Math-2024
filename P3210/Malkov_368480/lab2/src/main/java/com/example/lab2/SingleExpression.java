package com.example.lab2;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleToIntFunction;
import java.util.function.Function;

public class SingleExpression {

    private static final String FIRST_F_STRING= "x^2-x+4";
    private static final String SECOND_F_STRING= "sin(x) - (x/2)";

    private static final Function<Double, Double> firstF = x -> Math.pow(x, 3) - x + 4;
    private static final Function<Double, Double> firstDF = x ->( 3 * Math.pow(x, 2)) - 1;
    private static final Function<Double, Double> firstSDF = x -> 6 * x;
    private static final Function<Double, Double> secondF = x -> Math.sin(x) - (x / 2);
    private static final Function<Double, Double> secondDF = x -> Math.cos(x) - (0.5);
    private static final Function<Double, Double> secondSDF = x -> -Math.sin(x);

    private static final List<Function<Double, Double>> listOfF = new ArrayList<>(Arrays.asList(firstF, secondF));
    private static final List<Function<Double, Double>> listOfDF = new ArrayList<>(Arrays.asList(firstDF,secondDF));
    private static final List<Function<Double, Double>> listOfSDF = new ArrayList<>(Arrays.asList(firstSDF,secondSDF));
    private static final List<String> listOfFunction = new ArrayList<>(Arrays.asList(FIRST_F_STRING, SECOND_F_STRING));
    public static List<String> getListOfFunction(){
        return listOfFunction;
    }
    public static List<Function<Double, Double>> getListOfF(){
        return listOfF;
    }
    public static List<Function<Double, Double>> getListOfDF(){
        return listOfDF;
    }
    public static List<Function<Double, Double>> getListOfSDF(){
        return listOfSDF;
    }

    public static boolean hasOneRoot(Function<Double, Double> f, double a,double b) {
        if (f.apply(a) * f.apply(b) > 0) {
            return false;
        }
        if ((a == b) && (f.apply(a) == 0)){
            System.out.println("Джекпот!!");
            return true;
        }

        double step = (b - a)/1000;
        double signChanges = 0;
        double previousSign = f.apply(a);

        for (double x = a + step; x <= b; x += step) {
            double currentSign = f.apply(x);
            if (currentSign * previousSign<0 && currentSign != 0) {
                signChanges++;
                if (signChanges > 1) {
                    return false;
                }
            }
            previousSign = currentSign;
        }

        return signChanges == 1;
    }
    public static void halfMeth(Function<Double, Double> f, Function<Double, Double> df, Function<Double, Double> sdf,
                                double a, double b, double e, Label resultLabel){
        int counter = 0;
        double x = (a + b)/2;
        while(Math.abs(a - b) > e & Math.abs(f.apply(x)) >= e){ // я бы считал минимум до 20 каунтера
            x = (a + b)/2;
            if(f.apply(x) * f.apply(a) > 0){
                a = x;
            }else{
                b = x;
            }
            counter++;

            if(counter >= 100){
                resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+":" +" Лапками вверх попробуйте другие входные данные");
                break;
            }
        }
        x = (a + b)/2;
        resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+": " + x + "\nЗначение функции в корне: "+ f.apply(x)+"\nЧисло итераций: "+ counter);
    }
    public static boolean newtonConverge(Function<Double, Double> df, Function<Double, Double> sdf,double a, double b){
        double step = (b - a) / 1000 ;
        double prevSignDf = df.apply(a);
        double prevSignSdf = sdf.apply(a);
        double newSign1;
        double newSign2;
        if (df.apply(a) != 0){
            for (double x = a + step; x <= b; x += step){
                if (df.apply(x) == 0){
                    return false;
                }
                newSign1 = df.apply(x);
                newSign2 = sdf.apply(x);

                if (newSign1 * prevSignDf < 0 || newSign2 * prevSignSdf<0){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    public static void newtonMethod(Function<Double, Double> f,Function<Double, Double> df, Function<Double, Double> sdf,
                             Double a,Double b,Double e, Label resultLabel){
        double prevX = f.apply(a) * sdf.apply(a) > 0 ? a : b;
        System.out.println(prevX);
        double x = prevX - f.apply(prevX) / df.apply(prevX);
        int count = 1;
        if (newtonConverge(df, sdf, a, b)){
            System.out.println("Все ок");
            while (Math.abs(x - prevX) > e && Math.abs(prevX) > e && Math.abs(f.apply(prevX) / df.apply(prevX)) > e){
                prevX = x;
                x = prevX - f.apply(prevX) / df.apply(prevX);
                count++;
            }
            resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+": "  + x + "\nЗначение функции в корне: "+ f.apply(x)+"\nЧисло итераций: "+ count);
        } else {
            resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+":" +"Условия сходимости метода Ньютона не выполнены, посчитать невозможно");
        }

    }
    public static double maxdPhi(Function<Double, Double> f, Function<Double, Double> df, double a,double b,double lambda){
        double step = (b-a) / 1000;
        System.out.println(step);
        System.out.println(lambda);
        double maxD = 0;

        double dPhiA = Math.abs(1 + lambda*df.apply(a));
        double dPhiB = Math.abs(1 + lambda*df.apply(b));

        for (double x = a + step; x <= b; x += step){
            maxD = Math.max(maxD, Math.abs(1 + lambda*df.apply(x)));
        }
        System.out.println(maxD);
        System.out.println(dPhiA+ " "  + dPhiB);
        return maxD;
    }

    public static double phi(Function<Double, Double> f,double x,double lambda){
        return x + lambda * f.apply(x);
    }

   public static void simpleIterations(Function<Double, Double> f,Function<Double, Double> df, Function<Double, Double> sdf,
                                  Double a,Double b,Double e, int maxIter, Label resultLabel){
        String result="";
        double lambda = Math.abs(df.apply(a)) > Math.abs(df.apply(b)) ? (-1 / df.apply(a)) : (-1 / df.apply(b));

        double q = maxdPhi(f, df, a, b, lambda);

        double prevX = a;

        double x = phi(f, prevX, lambda);
        int counter = 1;

        if (q < 1){
            while (Math.abs(x - prevX) > e){
                System.out.println("x: " +  prevX+ "\nnewX:"+ x+" \nphi: "+ phi(f, x, lambda)+"\nf:" + f.apply(x)+"\nabs:"+ Math.abs(x - prevX));
                prevX = x;
                x = phi(f, x, lambda);
                counter++;
                System.out.println("x: " +  prevX+ "\nnewX:"+ x+" \nphi: "+ phi(f, x, lambda)+"\nf:" + f.apply(x)+"\nabs:"+ Math.abs(x - prevX));
            }
        } else {
             result +="Гарантия сходимости метода не обеспечена\n";
             while (Math.abs(x - prevX) > e && counter < maxIter){
                prevX = x;
                x = phi(f, x, lambda);
                counter++;
            }
        }
        if(counter == maxIter){
            resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+": "+"Лапками вверх");
        }else{
            resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+": "+ result  + x + "\nЗначение функции в корне: "+ f.apply(x)+"\nЧисло итераций: "+ counter );
        }
   }
}
