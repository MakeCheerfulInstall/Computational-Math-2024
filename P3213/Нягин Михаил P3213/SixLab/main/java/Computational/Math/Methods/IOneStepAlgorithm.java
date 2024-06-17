package Computational.Math.Methods;

import Computational.Math.Utils.FinalResultWithAccuracy;

import java.util.function.BinaryOperator;

public interface IOneStepAlgorithm {
    FinalResultWithAccuracy applyOneStep(AbstractMethod method, double xn, BinaryOperator<Double> function, double x_0, double y_0, double step, double accuracy);
}