package Lab5;

import Lab2.CalcErrorException;
import Lab2.IFunction;

/**
 * Многочлен Гаусса
 */
public class L5Method4 implements IL5Method, IFunction {
    protected double xs[];
    protected double ys[];
    /**
     * Шаг
     */
    private double h;
    /**
     * Величина t - (x - a)/h
     */
    protected   double t;
    /**
     * Параметр n
     */
    protected int n;
    /**
     * Индекс в массиве выбранной центральной точки
     */
    private int xi0;

    @Override
    public String toString() {
        return "Многочлен Гаусса";
    }

    /**
     *
     * @param x функция от
     * @return
     */


    @Override
    public double functionOf(double x) {
        return  switch(calcMiddle(x)){
            case 1 -> function1(x);
            default -> function2(x);
        };
    }

    /**
     * Расчет по первой формуле Гаусса
     * @param x
     * @return
     */
    private double function1(double x){
        double res = 0;
        int k = 0; // Порядок конечной разницы и онже номер элемента многочлена
        double tVal = 1;
        for(int i = 0; i >= -n; i--){
            tVal = calcT1(tVal,k);
            double dif = finalDiff(i,k++);
            res += tVal * dif;

            if(k > 2*n) break;
            tVal = calcT1(tVal,k);
            dif = finalDiff(i,k++);
            res += tVal * dif;
        }
        return res;
    }
    private  double function2(double x){
        double res = ys[iToIndex(0)];
        int k = 1; // Порядок конечной разницы и онже номер элемента многочлена
        double tVal = 1;
        for(int i = -1; i >= -n; i--){
            tVal = calcT2(tVal,k);
            double dif = finalDiff(i,k++);
            res += tVal * dif;

            if(k > 2*n) break;
            tVal = calcT2(tVal,k);
            dif = finalDiff(i,k++);
            res += tVal * dif;
        }
        return res;
    }


    /**
     * Вычисление множителя t по первой формуле Гаусса для k-го элемента
     * @param k номер элемента многочлена
     * @return
     */
    private double calcT1(double lastT,int k){
        /* toMinus
        при k = 1 получим 0
        при k = 2 получим -1
        при k = 3 получим 1
        при k = 4 получим -2
        при k = 5 получим 2
         */
        if(k == 0) return 1;
        int toMinus = k/2 * pow1(k-1);
        double tres = lastT * (t + toMinus)/k;
        return  tres;
    }
    /**
     * Вычисление множителя t по второй формуле Гаусса для k-го элемента
     * @param k номер элемента многочлена
     * @return
     */
    private double calcT2(double lastT,int k){
        /* toMinus -
        при k = 1 получим 0
        при k = 2 получим 1
        при k = 3 получим -1
        при k = 4 получим 2
        при k = 5 получим -2
         */
        if(k == 0) return 1;
        int toMinus = k/2 * pow1(k);
        double tres = lastT * (t + toMinus)/k;
        return  tres;
    }

    /**
     * Рассчитывает -1  в степени k
     * @param k - степень
     * @return -1 в степени k
     */
    private int pow1(int k){
        if(k == 0) return 1;
        return -1 * pow1(k-1);
    }

    @Override
    public double method(double[] xs, double[] ys, double x) {
        setXY(xs,ys);
        return functionOf(x);
    }

    @Override
    public void setXY(double[] xs, double[] ys) {
        this.xs = xs;
        this.ys = ys;
        if(xs.length != ys.length || xs.length < 2)
            throw new CalcErrorException("Недостаточное количество точек.");
        h =  Math.abs(xs[1] - xs[0]);
    }

    /**
     * Метод определяет нулевую точку в массиве
     * @param x
     */
    protected int calcMiddle(double x) {

        xi0 = xs.length%2 == 0? (xs.length)/2-1: xs.length/2;
        n = xi0;
        t =(x - xs[xi0])/h;

        return x > xs[xi0] ? 1 : 2;

    }

    /**
     * Метод вычисляет конучную разность к-го порядка для i-го элемента
     * @param i номер узла интерполяции
     * @param k порядок конченой разности
     * @return величина конечной разности
     */
    protected double finalDiff(int i,int k) {
        if(k == 0){
            return ys[iToIndex(i)];
        } else {
            return finalDiff(i+1, k-1) - finalDiff(i, k-1);
        }
    }

    protected int iToIndex(int i){
        int index = xi0+i;
        if(index < 0 || index >= xs.length) throw new CalcErrorException("Индекс при расчете вышел за пределы массива узлов интерполяции");
        return  xi0 + i;
    }
}
