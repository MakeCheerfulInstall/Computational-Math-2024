package Computational.Math.Methods;

import Computational.Math.Utils.FinalResultWithAccuracy;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public interface IManyStepsAlgorithm {
    FinalResultWithAccuracy applyManySteps(Function<Double,Double> correctFunction, int m, double b, BinaryOperator<Double> f, double x_0, double y_0, double step, double accuracy);
}
