package Lab5;

/**
 * Интерфейс для обеспечения вызова расчетных действия из панели параметров. Передается
 * из глваного окна в панель параметров
 */
public interface IL5Calculator {
    /**
     * Производит расчеты указанными методами
     * @param methods - перечень методов для расчета
     */
    void startCalculation(IL5Method[] methods);
}
