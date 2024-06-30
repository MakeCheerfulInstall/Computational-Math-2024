package com.example.lab2;

import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.lang.Double.isNaN;

public class SystemExpression {
    private static final String FIRST_F_STRING= "0.1 * x^2 + x + 0.2 * y^2 - 0.3 = 0";
    private static final String SECOND_F_STRING= "0.2 * x^2 + y + 0.1 * x * y - 0.7 = 0";
    private static final String FIRST_S_STRING= "3 * x - cos(y) - 0.9";
    private static final String SECOND_S_STRING= "sin(x-0.6) - y - 1.6";
    private static final List<String> listOfFunction = new ArrayList<>(Arrays.asList(FIRST_F_STRING, SECOND_F_STRING,FIRST_S_STRING, SECOND_S_STRING));
    public static List<String> getListOfFunction(){
        return listOfFunction;
    }
    private static final BiFunction<Double, Double, Double> fsfF = (x,y) -> 0.1 * Math.pow(x,2) + x + 0.2 * Math.pow(y,2) - 0.3;
    private static final BiFunction<Double, Double, Double> fsfPhi = (x, y) -> 0.3 - 0.1 * Math.pow(x,2) - 0.2 * Math.pow(y,2);
    private static final BiFunction<Double, Double, Double> fssF =  (x, y) -> 0.2 * Math.pow(x,2) + y + 0.1 * x * y - 0.7;
    private static final BiFunction<Double, Double, Double> fssPhi = (x, y) ->  0.7 - 0.2 * Math.pow(x,2) - 0.1 * x * y;

    private static final BiFunction<Double, Double, Double> fsfDFX = (x,y) -> -0.2 * x;
    private static final BiFunction<Double, Double, Double> fsfDFY = (x, y) -> -0.4 * y;
    private static final BiFunction<Double, Double, Double> fssDFX =  (x, y) -> -0.4 * x - 0.1 * y;
    private static final BiFunction<Double, Double, Double> fssDFY = (x, y) ->  -0.1 * x;

    private static final BiFunction<Double, Double, Double> ssfF = (x,y) ->3*x - Math.cos(y) - 0.9;
    private static final BiFunction<Double, Double, Double> ssfPhi = (x, y) -> (Math.cos(y) / 3) + 0.3;
    private static final BiFunction<Double, Double, Double> sssF =  (x, y) -> Math.sin(x-0.6) - y - 1.6;
    private static final BiFunction<Double, Double, Double> sssPhi = (x, y) ->  Math.sin(x-0.6) - 1.6;

    private static final BiFunction<Double, Double, Double> ssfDFX = (x,y) ->3*x - Math.cos(y) - 0.9;
    private static final BiFunction<Double, Double, Double> ssfDFY = (x, y) -> (Math.cos(y) / 3) + 0.3;
    private static final BiFunction<Double, Double, Double> sssDFX =  (x, y) -> Math.sin(x-0.6) - y - 1.6;
    private static final BiFunction<Double, Double, Double> sssDFY = (x, y) ->  Math.sin(x-0.6) - 1.6;

    private static final List<BiFunction<Double, Double, Double>> listOfF = new ArrayList<>(Arrays.asList(fsfF, fssF,ssfF,sssF));
    private static final List<BiFunction<Double, Double, Double>> listOfPhi = new ArrayList<>(Arrays.asList(fsfPhi,fssPhi,ssfPhi,sssPhi));

    private static final List<BiFunction<Double, Double, Double>> listOfPhiDFX = new ArrayList<>(Arrays.asList(fsfDFX,fssDFX,ssfDFX,sssDFX));

    private static final List<BiFunction<Double, Double, Double>> listOfPhiDFY = new ArrayList<>(Arrays.asList(fsfDFY,fssDFY,ssfDFY,sssDFY));
    public static List<BiFunction<Double, Double, Double>> getListOfF(){
        return listOfF;
    }
    public static List<BiFunction<Double, Double, Double>> getListOfPhi(){
        return listOfPhi;
    }
    public static List<BiFunction<Double, Double, Double>> getListOfPhiDFX(){
        return listOfPhiDFX;
    }
    public static List<BiFunction<Double, Double, Double>> getListOfPhiDFY(){
        return listOfPhiDFY;
    }

    public static boolean checkConvergence(BiFunction<Double, Double, Double> phi1DFX,BiFunction<Double, Double, Double> phi1DFY,
                                           BiFunction<Double, Double, Double>phi2DFX,BiFunction<Double, Double, Double> phi2DFY,
                                           double x0,double y0){
        double chpPhi1X = phi1DFX.apply(x0,y0);
        double chpPhi1Y = phi1DFY.apply(x0,y0);

        double sum1 = Math.abs(chpPhi1X) + Math.abs(chpPhi1Y);

        double chpPhi2X = phi2DFX.apply(x0,y0);
        double chpPhi2Y = phi2DFY.apply(x0,y0);

        double sum2 = Math.abs(chpPhi2X) + Math.abs(chpPhi2Y);

        System.out.println(sum1+" "+ sum2);

        return Math.max(sum1, sum2) < 1;
    }

    public static void simpleIteration(BiFunction<Double, Double, Double> f, BiFunction<Double, Double, Double> phi1,
                                       BiFunction<Double, Double, Double> g, BiFunction<Double, Double, Double> phi2,
                                       double x0, double y0, boolean convergence, double e, int maxIteration, Label resultLabel){
        double phi1Val = phi1.apply(x0, y0);
        String result= "";
        double phi2Val = phi2.apply(x0, y0);
        int count = 0;
        while (Math.max(Math.abs(phi1Val - x0), Math.abs(phi2Val - y0)) > e && count < maxIteration){
            System.out.println(x0 + " " + y0);
            System.out.println(phi1Val + " " +phi2Val);
            x0 = phi1Val;
            y0 = phi2Val;
            phi1Val = phi1.apply(x0, y0);
            phi2Val = phi2.apply(x0, y0);
            count++;
        }
        if (!convergence){
            result += "Гарантия сходимости метода не обеспечена\n";
        }
         if (Double.isInfinite(phi1Val) || Double.isInfinite(phi2Val)){
             result+= "Процесс расходится\n";
        } else {
             result  +="Результаты: X ="+ phi1Val +  " Y = "+phi2Val+"\n Значения функций: f1: "+
                     f.apply(phi1Val, phi2Val)+", f2: "+ g.apply(phi1Val, phi2Val)+" \n Количество итераций: "+count+"\n";
         }

        if (isNaN(Math.abs(phi1Val - x0)) || isNaN(Math.abs(phi2Val - y0))){
            result+= "Вектор погрешностей: ну тут всё грустно";
        } else {
            result += "Вектор погрешностей: "+Math.abs(phi1Val - x0)+" ; "+Math.abs(phi2Val - y0);
        }
        resultLabel.setText(resultLabel.getText().substring(0, resultLabel.getText().indexOf(":")).trim()+": " +result);
    }
}
