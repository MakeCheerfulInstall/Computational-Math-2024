package Computational.Math.Utils;

import Computational.Math.Methods.AbstractMethod;
import lombok.Data;

import java.util.List;
import java.util.function.BinaryOperator;

@Data
public class FinalResultWithAccuracy {
    private AbstractMethod method;
    private List<Double> resultOfIteration;
    private double accuracy;
    private double step;
    private double xn;private double y_0;private BinaryOperator<Double> function;private double x_0;

    public FinalResultWithAccuracy(AbstractMethod method, double xn, BinaryOperator<Double> function, double x_0, double y_0,double step, List<Double> resultOfIteration, double accuracy) {
        this.method = method;
        this.x_0 = x_0;
        this.resultOfIteration = resultOfIteration;
        this.accuracy = accuracy;
        this.step = step;
        this.xn = xn;
        this.function = function;
        this.y_0 = y_0;
    }



    public List<Double> getAnswer(double maxStep){
        return method.apply(xn, function, x_0, y_0, maxStep);
    }
}
