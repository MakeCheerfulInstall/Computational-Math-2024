package Lab4;

import Lab2.CalcParams;
import Lab3.ISFunction;
import Lab3.ISMethod;

/**
 * Интерфейс для передачи кнопке "Рассчитать интеграл" метода, который должен быть вызван по
 * нажатию кнопки
 */
public interface L4CalculationProcess {
     /**
      * Метод для старта процесса расчета
      * @param func подинтегральная функция для расчета
      * @param method используемый метод расчета
      * @param params входные параметры
      */
     void startCalculation(ISFunction func, ISMethod method, CalcParams params);
}
