package Lab5;

import Lab2.IFunction;

/**
 * Многочлен Лагранжа
 */
public class L5Method1 implements IL5Method, IFunction {
    @Override
    public String toString() {
        return "Многочлен Лагранжа";
    }
    @Override
    public double method(double[] xs, double[] ys, double x) {
        setXY(xs,ys);
        return functionOf(x);
    }

    private double[] xs;
    private double[] ys;

    @Override
    public void setXY(double[] xs, double[] ys) {
        this.xs = xs;
        this.ys = ys;
    }

    @Override
    public double functionOf(double x) {
        double result = 0;
        for(int i = 0; i < xs.length; i++){
            double mlt = 1;
            for(int j = 0; j < ys.length; j++){
                if(j==i) continue;
                mlt *= (x - xs[j])/(xs[i] - xs[j]);
            }
            result += mlt*ys[i];
        }
        return result;
    }
}
