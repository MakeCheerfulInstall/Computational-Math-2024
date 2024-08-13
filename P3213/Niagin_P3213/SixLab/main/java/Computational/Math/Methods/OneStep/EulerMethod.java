package Computational.Math.Methods.OneStep;

import Computational.Math.Methods.AbstractMethod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class EulerMethod extends AbstractMethod {
    public EulerMethod() {
        super("Метод Эйлера");
    }

    @Override
    public List<Double> apply(double xn, BinaryOperator<Double> function, double x_0, double y_0, double step )  {
        List<Double> yValues = new ArrayList<>();
        double nextY = y_0;
        int n = (int) ((xn-x_0)/step);

        for (int i = 0; i <= n; i++) {
            nextY = nextY(x_0,y_0,step,function);
            yValues.add(nextY);
            x_0 += step;
            y_0 = nextY;
        }
        return yValues;
    }
    private double nextY(double x_0, double y_0, double step, @NotNull BinaryOperator<Double> f){
        return y_0 + step * f.apply(x_0,y_0);
    }
}
