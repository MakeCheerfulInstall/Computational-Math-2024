package Lab3;


import Lab2.CalcParams;

/**
 * Реализация метода правых прямоугольников
 */
public class MRectangleRight extends MethodBaseLb3 {
    @Override
    public String toString() {
        return "Правых прямоугольников";
    }

    @Override
    double calcS(CalcParams params, double[] ys, int n) {
        double res = 0;
        for (int i = 1; i <= n; i++) res += ys[i];
        double h = (params.xb - params.xa) / n;
        res *= h;
        return res;
    }
}
