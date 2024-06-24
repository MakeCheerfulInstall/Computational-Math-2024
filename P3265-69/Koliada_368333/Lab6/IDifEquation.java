package Lab6;

import Lab2.CalcParams;
import Lab2.IFunction;

/**
 * Интерфейс диф уравнения
 */
public interface IDifEquation extends IFunction {
    /**
     * Решение уравнения - интегральная фуенкция для указанных начальных условий
     * @param x0 начальное условие по x
     * @param y0 - y0 = function(x0)
     * @return фукнкцию
     */
    IFunction getIntegralFunction(double x0,double y0);

    /**
     * Вычисляет значение функции Коши
     * @param x x
     * @param y y
     * @return значение
     */
    double valueOf(double x,double y);

    /**
     * Проверяет, что указанные параметры расчета возможны для решения уравнвения. В первую очередь,
     * что на указанном интервале есть решение
     * @param params введенные параметра
     */
    void checkParams(CalcParams params);
}
