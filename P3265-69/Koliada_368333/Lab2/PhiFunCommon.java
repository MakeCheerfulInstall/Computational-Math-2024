package Lab2;

/**
 * Приведенная функция для общего метода приведения x= x+ lambda*f(x)
 */
public class PhiFunCommon implements IFunction{
    /**
     * Оригинальная функция
     */
    private final IFunction _originFun;

    /**
     * Конструктор приведенной функции
     * @param origin оригинальная функция
     * @param a левая граница области решений
     * @param b правая граница области решений
     */
    public PhiFunCommon(IFunction origin, double a, double b) {
        _originFun = origin;
        calcLambda(a,b);
    }
    private double _lambda;

    /**
     * Метод вичисляет люмбду на отрезеке
     * @param a левая граница области решений
     * @param b правая граница области решений
     */
    private void calcLambda(double a, double b){
        double f1a = Math.abs(_originFun.f1Of(a));
        for (double x = a+0.01;x <=b; x++){
            f1a = Math.max(f1a,Math.abs(_originFun.f1Of(x)));
        }
        _lambda = -1/f1a;
    }
    @Override
    public double functionOf(double x) {
        return x + _lambda * _originFun.functionOf(x);
    }

    @Override
    public double f1Of(double x) {
        return 1 + _lambda* _originFun.f1Of(x);
    }
}
