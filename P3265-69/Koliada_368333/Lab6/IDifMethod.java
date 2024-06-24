package Lab6;

import Lab2.CalcParams;
import Lab2.ILogger;
import Lab5.L5FunctionByTable;

/**
 * Интерфейс для методов решения диф уравнения
 */

public interface IDifMethod {

    /**
     * Метод запускает расчет
     * @param eq - диф уравнение для решения
     * @param params указанные пользователем параметры для расчета
     * @return Решение в виде табличной функции
     */
    L5FunctionByTable calculate(IDifEquation eq, CalcParams params);

    /**
     * Запоминает логгер для вывода результатов расчета
     * @param logger логгер
     */
    void setLogger(ILogger logger);
}
