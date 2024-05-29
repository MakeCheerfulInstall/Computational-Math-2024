package org.example;
//isFinit

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.*;
import org.apache.commons.math3.exception.TooManyEvaluationsException;

import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Math.round;
import static java.lang.Math.sin;

public class Convergence {

    Functions functions = new Functions();

    double point;

    public boolean check(int lowerBound, int upperBound, int number) {
        boolean flag = true;
        point = 0;
        for (int i = lowerBound; i <= upperBound; i++) {
            if (!Double.isFinite(functions.getFunction(number, i))) {
                flag = false;
                point = i;
                break;
            }
        }
        return flag;
    }

    public double getPoint() {
        return point;
    }

}
