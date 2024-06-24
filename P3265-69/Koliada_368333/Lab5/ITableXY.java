package Lab5;

/**
 * Интерфейс к таблице с данными функции. Используется для передачи
 * панели с параметрами необходимых ей действий с таблицей
 */
public interface ITableXY {
    /**
     * Очищает данные в таблице
     */
    void clean();

    /**
     * Добавляет строку к таблице
     * @param x
     * @param y
     * @return
     */
    boolean addLine(double x, double y);

    /**
     * Возвращает данные из таблицы
     * @return
     */
    double[][] getXY();
}
