package org.example;

import static java.lang.Math.*;

public class Functions {
    public double getFunction(double x, int number) {
        if (number == 1) {
            return x * x * x - x + 4;
        } else if (number == 2) {
            return 3*x*x*x+1.7*x*x-15.42*x+6.89;
        } else {
            return x*x - 3*x +1;
        }

    }

    public double getDerivativeFunction(double x, int number) {
        if (number == 1) {
            return 3 * x * x - 1;
        } else if (number == 2) {
            return 9*x*x+3.4*x-15.42;
        } else {
            return 2*x-3;
        }
    }

    public double getNextApproximation(double x, int number) {
        if (number == 1) {
            return 1.0909 * x - 0.0909 * x * x * x - 0.3636;
        } else if (number == 2) {
            return cbrt((-1.7*x*x+15.42*x-6.89)/3);
        } else {
            return (x*x+1)/3;
        }
    }

    public double getFiDerivative(double x, int number){
        if (number == 1){
            return 1/3*(cbrt(1/((x-4)*(x-4))));
        }else if(number == 2){
            return (1/3)*cbrt(1/3)*cbrt(1/(pow(-1.7*x*x+15.42*x -6.89,2)))*(-3.4*x+15.42);
        }else {
            return 2*x/3;
        }
    }


    public double getNextApproximationForX(double x, double y, int number) {
        if (number == 1) {
            return 0.3 - 0.1 * x * x - 0.2 * y * y;
        } else {
            return (2-Math.cos(y))/2;
        }
    }

    public double getSystem1(double x, int number) {
        if (number == 1) {
            double res = Math.sqrt((0.3 - 0.1 * x * x - x) / 0.2);
            return res;
        } else {
            return Math.acos(2-2*x);

        }
    }

    public double getSystem2(double x, int number) {
        if (number == 1) {
            return (0.7-0.2*x*x)/(1+0.1*x);
        } else {
            return 1.2-sin(x+1);
        }
    }


    public double getNextApproximationForY(double x, double y, int number) {
        if (number == 1) {
            return 0.7 - 0.2 * x * x - 0.1 * x * y;
        } else {
            return 1.2-sin(x+1);
        }
    }

    public double getSystemFiDerivative1(double x, double y, int number){
        if (number == 1){
            return Math.abs(-0.2*x) + Math.abs(-0.4*y);
        }else {
            return Math.abs(sin(y)/2);
        }
    }

    public double getSystemFiDerivative2(double x, double y, int number){
        if (number == 1){
            return Math.abs(-0.4*x-0.1*y)+Math.abs(-0.1*x);
        }else {
            return Math.abs(cos(x+1));
        }
    }




}
