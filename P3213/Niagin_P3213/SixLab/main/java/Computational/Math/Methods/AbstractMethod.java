package Computational.Math.Methods;


import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.List;
import java.util.function.BinaryOperator;

public abstract class AbstractMethod {
    private final String methodName;
    public AbstractMethod(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @param xn правая граница исследуемого участка
     * @param function y' = y...
     * @param x_0 x_0 из начального условия, например y(-1) = 1, где x_0 = -1
     * @param y_0 y_0 из начального условия, например y(-1) = 1, где y_0 = 1
     * @param step Заданный шаг
     */

    public abstract List<Double> apply(double xn, BinaryOperator<Double> function, double x_0, double y_0, double step);
    public String getMethodName(){
        return methodName;
    }
    public String round(double Data){
        return String.format("%.3f",Data);
    }
}
