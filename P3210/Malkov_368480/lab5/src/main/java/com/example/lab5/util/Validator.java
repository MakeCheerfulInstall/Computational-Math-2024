package com.example.lab5.util;

import java.util.ArrayList;

public class Validator {
    public static ArrayList<Double> splitStringToDoubleArrayList(String input) throws NumberFormatException{
        String[] stringArray = input.replace(",",".").replace("[]","").split(" ");
        ArrayList<Double> result = new ArrayList<>();
        for (String str : stringArray) {
            result.add(Double.parseDouble(str));
        }
        return result;
    }
}
