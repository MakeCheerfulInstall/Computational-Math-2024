package Computational.Math.Methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class AdamsMethod extends AbstractMethod {
    public AdamsMethod() {
        super("Метод Адамса");
    }


    private List<Double> apply(int m, double x_n, BinaryOperator<Double> f, double x_0, double y_0, double step) {
        int n = (int) ((x_n - x_0) / step);
        Double[] yArr = new Double[n + 1];
        Double[] xArr = new Double[n + 1];
        for (int i = 0; i <= n; i++) {
            xArr[i] = x_0 + i * step;
        }
        yArr[0] = y_0;
        for (int i = 0; i < m; i++) {
            yArr[i + 1] = yArr[i] + step * f.apply(xArr[i], yArr[i]);
        }
        for (int i = m; i < n; i++) {
            yArr[i + 1] = yArr[i] + step * (
                    55 * f.apply(xArr[i], yArr[i]) -
                            59 * f.apply(xArr[i - 1], yArr[i - 1]) +
                            37 * f.apply(xArr[i - 2], yArr[i - 2]) -
                            9 * f.apply(xArr[i - 3], yArr[i - 3])
            ) / 24.0;
        }
        return new ArrayList<Double>(List.of(yArr));
    }

    @Override
    public List<Double> apply(double xn, BinaryOperator<Double> function, double x_0, double y_0, double step) {
        return apply(4,xn,function,x_0,y_0,step);
    }
}