package Lab3;

import Lab2.CalcErrorException;
import Lab2.CalcParams;
import Lab2.ILogger;

/**
 * Базовый абстрактный класс для методов расчета интеграла. Наследующий класс
 * должен реализовать как минимум метод расчета интеграла с указазанными границами интегрирования
 * и количеством разбиений этого интервала. Сам базовый класс реализует метод calculate,
 * котрый рассчитывает инграл для метода наследника с указанной точностью, уточняя
 * количество разбиений интервала интегрирования до достижения указанной точности
 * по методу Рунге
 */

public abstract class MethodBaseLb3 implements ISMethod{
    /**
     * результат расчета
     */
    protected double result = 0;
    /**
     * Количество разбиений интервала интегрирования для достижения
     * заданной точности
     */
    protected int lastIntervals = 0;

    /**
     * Проверка входных параметров
     * @param params
     */
    @Override
    public void checkParams(CalcParams params) {
        if (params.startingDelta <= 0) throw new CalcErrorException("Число интервалов должно быть больше 0");
        if (params.xa == params.xb) throw new CalcErrorException("Пределы интегрирования не могут быть равны");
    }

    /**
     * Метод рассчитывает интеграл с заданной точностью по методу Рунге
     * @param fun подинтегральная функция
     * @param params заданные параметры (интервал интегрирования и число разбиения интервала
     * @return
     */
    @Override
    public double calculate(ISFunction fun, CalcParams params) {

        //Проверяем параметры
        checkParams(params);

        var newIntervals = fun.splitInterval(params);

        double localResult = 0;
        for(var p: newIntervals) {
           localResult += calculateInterval(fun, p);
        }
        result = localResult;
        return result;
    }

    private double calculateInterval(ISFunction fun, CalcParams params) {

        //Количество разбиений интервала указано в double (наследство от laba2)
        // Таким образом мы получаем целое число из него
        int n = (int)(params.startingDelta + 0.5);
        //Результат на предыдущей итерации расчета
        double previousResult;
        //Устанавливаем начальное значение в несуществующее число
        result = Double.NaN;
        do {
            previousResult = result;
            //Получаем массив x для текущего количества разбиений
            //После этого получаем для этих x массив y - значений функцй
            var ys = fun.getValuesOf(getXs(params.xa, params.xb, n));
            //Дальше рассчитываем интеграл методом наследником
            result = calcS(params,ys,n);
            //Запоминаем поледний N - количество разбиений
            lastIntervals = n;
            //Удваиваем количество разбиений для следующего расчета
            n *= 2;
            //Продолжаем цикл пока нет  предыдущего результат (первая итерация) или пока
            // не достигнем нужной точности
        } while(Double.isNaN(previousResult) || Math.abs(previousResult - result) > params.precision);

        //Вернем результат расчета
        return result;

    }

    /**
     * Абстрактная функция для расчета интеграла для массива y-ов
     * @param params параметры расчета
     * @param ys массив значений функции
     * @param n количество разбиений
     * @return результат расчет интеграла
     */
    abstract double calcS(CalcParams params, double[]ys,int n);

    /**
     * Строит массив x для указанного интервала и количество разбиений
     * В такой реализации метод может быть использован для всех методов расчета интеграла,
     * кроме средних прямоугольков. Метод стредних прямоугольников должен переопределить
     * этот метод
     * @param xa нижняя граница интервала интегрирования
     * @param xb верхняя граница интервала интегрирования
     * @param n количество разбиений интервала
     * @return массив значений х
     */
    protected double[] getXs(double xa, double xb, int n){
        double h = (xb - xa) / n;
        double[] xs = new double[n + 1];
        // Формируем массив xs
        double x = xa;
        for(int i = 0; i<n;i++){
            xs[i] = x;
            x += h;
        }
        xs[n] = xb;
        return  xs;
    }

    /**
     * Журнал для вывода сообщений
     */
    protected ILogger logger;

    /**
     * Setter для журнала
     * @param log
     */
    @Override
    public void setLogger(ILogger log) {
        logger = log;
    }

    /**
     * Getter для результата расчета интеграла
     * @return результат расчета
     */
    @Override
    public double getCalculatedRoot() {
        return result;
    }

    /**
     * Getter для количества разбиений интервла интегрирования
     * @return использованное количество разбиений интервала интегрирования
     */
    @Override
    public int getUsedIntervals() {
        return lastIntervals;
    }

}
