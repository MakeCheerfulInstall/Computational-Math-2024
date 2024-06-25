package Lab3;

import Lab2.CalcParams;
import Lab2.ILogger;

/**
 * Интерфейс методов расчета интеграла
 */
public interface ISMethod {
    /**
     * Метод рассчитывает значение интеграла до заданной точности по правилу Рунге
     * @param fun подинтегральная функция
     * @param params заданные параметры (интервал интегрирования и число разбиения интервала
     * @return
     */
     double calculate(ISFunction fun, CalcParams params);
    /**
     * Метод устанавливает журнал, который может использовать метод для вывода хода расчетов
     * @param logger
     */
    void setLogger(ILogger logger);

    void checkParams(CalcParams params);


    /**
     * Возвращает исчисленный интеграл
     * @return значение исчисленного интеграла
     */
    double getCalculatedRoot();

    /**
     * Возвращает количество разбиений интервала интегрирования
     * @return
     */
    int getUsedIntervals();
}
