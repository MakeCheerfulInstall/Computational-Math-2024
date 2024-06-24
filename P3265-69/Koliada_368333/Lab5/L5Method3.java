package Lab5;

import Lab2.CalcErrorException;
import Lab2.IFunction;

/**
 * Многочлен Ньютона для равноотстоящих узлов
 */
public class L5Method3 implements IL5Method, IFunction {

    private double [] xs;
    private double [] ys;
    /**
     * Шаг
     */
    private double h;

    /**
     * Строит таблицу конечных разностей размером n на n+ (x +  n колонок для delta)
     * @return
     */
    public double[][] getFinalDiffTable(){
        double[][] diffTable = new double[xs.length][xs.length+1];
        for(int i = 0; i < xs.length; i++){
            diffTable[i][0] = xs[i];
            for(int j = 0; j < xs.length-i; j++){
                diffTable[i][j+1] = finalDiff(i,j);
            }
        }
        return diffTable;
    }

    /**
     * Метод вычисляет конучную разность к-го порядка для i-го элемента
     * @param i номер узла интерполяции
     * @param k порядок конченой разности
     * @return величина конечной разности
     */
    private double finalDiff(int i,int k) {
        if(k == 0){
            return ys[i];
        } else {
            return finalDiff(i+1, k-1) - finalDiff( i, k-1);
        }
    }

    @Override
    public String toString() {
        return "Многочлен Ньютона с конечными разностями";
    }






























    /**
     * Собственно расчет значения для аргумента x
     * @param x функция от
     * @return
     */
    @Override
    public double functionOf(double x) {
        if(xs == null || ys == null) throw new CalcErrorException("Функция не задана");
        h = xs[1] - xs[0];

        return switch (chooseFormula(x)) { //Выбор типа формулы
            case 1 -> formula1(x);  // Первая функция Ньютона
            case 11 -> formula11(x, 1, 1); // Частный случай первой формулы Ньютона для x1 < x< x2
            default -> formula2(x); // Вторая формула Ньютона
        };
    }

    /**
     * Расчет значения для аргумента x по указанной таблице
     * @param xs значения узлов интерполяции
     * @param ys значения функции в узлах интерполяции
     * @param x аргумент для определения значения функции
     * @return
     */
    @Override
    public double method(double[] xs, double[] ys, double x) {
        setXY(xs,ys);
        return functionOf(x);
    }

    @Override
    public void setXY(double[] xs, double[] ys) {
        this.xs = xs;
        this.ys = ys;
    }

    /**
     * Выбор формулы для решения
     * @param x
     * @return true - если первая, false - вторая
     */

    private int chooseFormula(double x) {
        int i;
        for(i = 0; i<xs.length; i++){
            if(xs[i]> x) break;
        }
        if(i == xs.length) return 1;
        if(i == 0) return 2;
        if(i == 2) return 11;
        if(x-xs[i-1] <= xs[i]-x) return 1;
        return 2;
    }

    /**
     * Первая фармула Ньютона
     * @param x
     * @return
     */
    private double formula1(double x){
        return formula1(x,0);
    }

    /**
     * Первая формпула
     * @param x аргумент
     * @param xi индекс элемента, принимаемого за x0
     * @return значение функции для x
     */
    private double formula1(double x,int xi){
        double res = ys[xi];

        for(int i=1;i<xs.length;i++){
            res += t1ofi(x,i,xi) * finalDiff(xi,i);
        }
        return res;
    }

    /**
     * Частный случай первой формулы для случая x1 <= x <= x2
     * @param x аргумент
     * @param xi индекс первого элемента, т.е. всегда 1
     * @param iStart индекс первого элемнта для построения многочлена - всегда 1
     * @return
     */
    private double formula11(double x,int xi, int iStart){
        double res = ys[xi];

        int k = 1;
        for(int i=iStart + 1;i<xs.length;i++,k++){
            double t = t1ofi(x,i-iStart,xi);
            double yy=finalDiff(xi,k);
            res += t * yy;
        }
        return res;
    }

    /**
     * Вторая форпмула
     * @param x
     * @return
     */
    private double formula2(double x){
        double res = ys[ys.length-1];
        int k=1;
        for(int i=xs.length-2;i>=0;i--,k++){
            double t = t2Ofi(x,k);
            double yy=finalDiff(i,k);
            res +=  t*yy;
        }
        return res;
    }

    /**
     * Меод считает произведение t(t-1)(t-2)... для итого члена от x[xi]
     * @param x
     * @param i
     * @param  xi
     * @return
     */
    private double t1ofi(double x,int i,int xi){
        if(i == 1) return t1(x,xi);
        double res = 1;
        for(int j=0;j<i;j++){
            res *= t1(x,xi) - j;
        }
        return res/factorial(i);
    }

    /**
     *
     * @param x
     * @param i - номер члена в многочлене от 0
     * @return
     */
    private double t2Ofi(double x,int i){
        if(i == 1) return t2(x);
        double res = 1;
        for(int j=0;j<i;j++){
            res *= t2(x) + j;
        }
        return res/factorial(i);
    }

    /**
     * Расчет факториала
     * @param f
     * @return
     */
    private static int factorial(int f){
        if(f == 1) return 1;
        return f * factorial(f-1);
    }

    /**
     * t для первой формулы
     * @param x
     * @param xi
     * @return
     */
    private double t1(double x,int xi){
        return (x-xs[xi])/h;
    }

    /**
     * t для второй формулы
     * @param x
     * @return
     */
    private double t2(double x){
        return t1(x,xs.length-1);
    }


}
