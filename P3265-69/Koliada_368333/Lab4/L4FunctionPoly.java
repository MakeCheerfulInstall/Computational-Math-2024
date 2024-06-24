package Lab4;

import Lab1.Matrix;
import Lab2.IFunction;

/**
 * Класс функций полиномов любой степени
 */
public class L4FunctionPoly implements IFunction {

    /**
     * Метод возвращает название вида функции
     * @return название вида функции
     */
    public String getFunctionName() {
        return String.format("Полином степени %d",_m);
    }

    /**
     * Коэффициенты полинома a0, a1...
     */
    protected double[] _a = null;
    /**
     * Степень полинома
     */
    private int _m ;

    /**
     * Конструктор функции полинома
     * @param n - степень полинома
     */
    public L4FunctionPoly(int n) {
        if(n <= 0) throw new IllegalArgumentException();
        _a = new double[n+1];
        _m = n;
    }

    /**
     * Призна возникновения ошибки при расчете коэффициентов
     */
    protected boolean _wasError = false;

    /**
     * getter признака наличия ошибки
     * @return наличие ошибки
     */
    public boolean getWasError() { return _wasError; }

    /**
     * Метод вычисляет коэффициенты a0,a1,....,am
     * @param dots точки графика
     */
    public void CalcA(Dots dots){

        double[][] matrix = new double[_m+1][_m+1];
        double[] b = new double[_m+1];

        //Вычисляем массив B и матрицу
        for(int i = 0; i <= _m; i++){
            double sum = 0.0;
            for(int j = 0; j < dots.getN(); j++){
                //sum += dots.getDotY(j)*Math.pow(dots.getDotX(j),i);
                sum += getY(dots,j) * Math.pow(getX(dots,j),i);
            }
            b[i] = sum;
            //Вычисляем строку матрицы
            for(int col = 0; col <= _m; col++){
                sum = 0;
                for(int j = 0; j < dots.getN(); j++){
                    //sum += Math.pow(dots.getDotX(j),col+i); // Обратить внимание на степень
                    sum += Math.pow(getX(dots,j),col+i);// Обратить внимание на степень
                }
                matrix[i][col] = sum;
            }
        }
        Matrix matr = new Matrix(matrix,b);
        _a = matr.calcMatrix();
    }

    /**
     * Метод возвращает то значение Y, которое должно быть помещено в СЛАУ для расчета по методу
     * наименьших квадратов
     * @param dots точки графика
     * @param index индекс точки
     * @return искомое значение
     */
    protected double getY(Dots dots,int index){
        return dots.getDotY(index);
    }

    /**
     * Метод возвращает то значение X, которое должно быть помещено в СЛАУ для расчета по методу
     * наименьших квадратов
     * @param dots точки графика
     * @param index индекс точки
     * @return искомое значение
     */
    protected double getX(Dots dots,int index){
        return dots.getDotX(index);
    }

    @Override
    public String toString() {
        if(_a == null) return "Полином не определен";
        if(_m == 1)
            return String.format("%f + %f * x", _a[0], _a[1]);
        if(_m == 2)
            return String.format("%f + %f * x + %f *x*x", _a[0], _a[1], _a[2]);
        if(_m == 3)
            return String.format("%f + %f * x + %f *x*x + %f*x*x*x", _a[0], _a[1], _a[2], _a[3]);
        return String.format("Полином степени %d",_m);
    }

    @Override
    public double functionOf(double x) {
        double sum = 0.0;
        for(int i = 0; i <=_m; i++) {
            sum += _a[i] * Math.pow(x,i);
        }
        return sum;
    }

    @Override
    public double f1Of(double x) {
        return 0;
    }
}
