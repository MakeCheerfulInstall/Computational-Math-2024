package Lab3;

import  Lab2.CalcParams;

/**
 * Интерфейс для передачи кнопке "Рассчитать интеграл" метода, который должен быть вызван по
 * нажатию кнопки
 */
public interface L3CalculationProcess {
     /**
      * Метод для старта процесса расчета
      * @param func подинтегральная функция для расчета
      * @param method используемый метод расчета
      * @param params входные параметры
      */
     void startCalculation(ISFunction func, ISMethod method, CalcParams params);
}
