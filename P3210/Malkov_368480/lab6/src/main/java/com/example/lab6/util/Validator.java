package com.example.lab6.util;

import java.util.ArrayList;
import java.util.function.Function;

public class Validator {
    public static ArrayList<Double> splitStringToDoubleArrayList(String input) throws NumberFormatException{
        String[] stringArray = input.replace(",",".").replaceAll("[ ]+"," ").split(" ");
        ArrayList<Double> result = new ArrayList<>();
        for (String str : stringArray) {
            result.add(Double.parseDouble(str));
        }
        return result;
    }
    public static double validateSingleField(String input) throws NumberFormatException, NoSuchFieldException{
       if (input.equals("")){
           throw new NoSuchFieldException("");
       }
        return Double.parseDouble(input.replace(",","."));
    }
}
