package Lab3;

import Lab2.CalcParams;

/**
 * Реализация метода трапеций
 */
public class MTrapeziod extends MethodBaseLb3{
    @Override
    public String toString() {
        return "Трапеций";
    }

    @Override
    double calcS(CalcParams params, double[] ys, int n) {
        double h = (params.xb - params.xa)/n;
        double res = ((ys[0] + ys[ys.length-1]))/2;
        for(int i = 1; i < n-1;i++) res += ys[i];
        res *= h;
        return res;
    }
}
