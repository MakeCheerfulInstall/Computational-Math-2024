package org.example;

import static java.lang.Math.*;

public class Functions {
    public double getFunction(int number, double x) {
        if (number == 1) {
            return x * x;
        }
        if (number == 2) {
            return 3 * x * x * x - 2 * x * x - 7 * x - 8;
        } if (number == 3) {
            return 2 * x * x * x - 3 * x * x + 5 * x - 9;
        } if (number == 4){
            return 1 / x;
        } else{
            return tan(x);
        }
    }


}
