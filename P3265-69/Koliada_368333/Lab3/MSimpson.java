package Lab3;

import Lab2.CalcErrorException;
import Lab2.CalcParams;

/**
 * Реализация метода Симпсона
 */
public class MSimpson extends MethodBaseLb3{
    @Override
    public String toString() {
        return "Симпсона";
    }

    /**
     * Переопределяем метод проверки параметров, чтобы убедиться, что N - четное число
     * @param params
     */
    @Override
    public void checkParams(CalcParams params) {
        int n = (int)(params.startingDelta + 0.5);
        if(n == 0 || (n % 2) != 0) throw  new CalcErrorException("Для метода симпсона N должно быть четным числом");
        //Для остальных проверок вызываем реализацию от super класса
        super.checkParams(params);
    }

    @Override
    double calcS(CalcParams params, double[] ys, int n) {
        double h = (params.xb - params.xa)/n;
        return h/3*(ys[0] + 4 * ysum(1,n-1,ys) + 2 * ysum(2,n-2,ys) + ys[n]);

    }

    /**
     * Вспомогательный метод для расчета среза массива или по четным или по нечетным.
     * @param left левая гарница среза
     * @param right правая граница среза
     * @param ys массив y
     * @return сумма по срезу
     */
    private double ysum(int left,int right, double [] ys){
        double res = 0;
        for(int i = left ; i <= right;i+=2) res += ys[i];
        return res;
    }
}
