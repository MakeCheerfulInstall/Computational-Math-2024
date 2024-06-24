package Lab3;

import Lab2.CalcParams;
/**
 * Реализация метода левых прямоугольников
 */
public class MRectangleLeft extends  MethodBaseLb3 {

    @Override
    public String toString() {
        return "Левых прямоугольников";
    }
    protected double calcS(CalcParams params, double[]ys,int n){
        double res = 0;
        for (int i = 1; i <= n; i++) res += ys[i - 1];
        double h = (params.xb - params.xa) / n;
        res *= h;
        return res;
    }

}
