package Lab3;

import Lab2.CalcParams;

/**
 * Реализация метода средних прямоугольников. Класс дополнительно
 * переопределяет метод расчета массива x
 */
public class MRectangleMiddle extends MethodBaseLb3{
    @Override
    public String toString() {
        return "Средних прямоугольников";
    }

    @Override
    double calcS(CalcParams params, double[] ys, int n) {
        double res = 0;
        for (int i = 0; i < n; i++) res += ys[i];
        double h = (params.xb - params.xa) / n;
        res *= h;
        return res;
    }

    @Override
    protected double[] getXs(double xa, double xb, int n) {
        double h = (xb - xa) / n;
        double[] xs = new double[n];
        // Формируем массив xs
        double x = xa + h/2;
        for(int i = 0; i<n-1;i++){
            xs[i] = x;
            x += h;
        }
        xs[n-1] = xb -h/2;
        return  xs;
    }
}
