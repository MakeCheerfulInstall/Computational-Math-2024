package Lab5;

import Lab2.IFunction;

/**
 * Многочлен Ньютона с разделенными разностями
 */
public class L5Method2 implements IL5Method, IFunction {
    @Override
    public double method(double[] xs, double[] ys, double x) {
        setXY(xs,ys);
        return functionOf(x);
    }

    private  double[] xs;
    private  double[] ys;
    @Override
    public void setXY(double[] xs, double[] ys) {
        this.xs = xs;
        this.ys = ys;
    }

    /**
     * Расчет разделенных разностей
     * @param iStart начальный элемент
     * @param iLast конечный элемент
     * @return конечная разность
     */
    private double dividedDiff(int iStart, int iLast) {
        if((iLast - iStart) == 1){
            return (ys[iLast] - ys[iStart])/(xs[iLast] - xs[iStart]);
        }
        else {
            return (dividedDiff(iStart + 1, iLast)
                    - dividedDiff(iStart,iLast-1)) / (xs[iLast] - xs[iStart]);
        }
    }

    @Override
    public String toString() {
        return "Многочлен Ньютона с разделенными разностями";
    }

    @Override
    public double functionOf(double x) {
        double result = ys[0];
        for (int k = 1; k < xs.length; k++) {
            double divdiff = dividedDiff(0,k);
            double mlt = 1;
            for(int j = 0; j < k; j++){
                mlt *= (x - xs[j]);
            }
            result += divdiff*mlt;
        }
        return result;
    }
}
