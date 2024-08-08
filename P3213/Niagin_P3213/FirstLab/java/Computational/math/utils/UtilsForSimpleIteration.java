package Computational.math.utils;

import Computational.math.SimpleIteration;

import java.text.DecimalFormat;
import java.util.Arrays;

public class UtilsForSimpleIteration {
    public static void printFinalTable(Double[] lastApproach, Double arrayRes){
        System.out.print("k = " + SimpleIteration.getIterationNumber() + " | ");
        Arrays.stream(lastApproach).map(approach -> approach + " | ").forEach(System.out::print);
        if(arrayRes == null){
            System.out.print("-" + "\n");
        }
        else {
            System.out.print(roundDouble(arrayRes) + "\n");
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }
    public static Double roundDouble(Double num){
        return Double.parseDouble(String.format("%.5f",num).replace(",","."));
    }
}
