package Lab6;

/**
 * Интерфейс для обеспечения вызова расчетных действия из панели параметров. Передается
 * из глваного окна в панель параметров
 */
public interface IL6Calculator {
    /**
     * Запускает расчет указанного диф уравнения указанными методами
     * @param methods - перечень методов для расчета
     */
    void startCalculation(IDifEquation eq, IDifMethod[] methods);
}
