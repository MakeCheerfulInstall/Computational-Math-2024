package Lab2;

/**
 * Вспомогательный класс, определяющий границы участка на оси координат. Используется для определения отрезков,
 * на которых меняется знак функции
 */
public class Margins {
    /**
     * Левая граница
     */
    public final double a;
    /**
     * Правая граница
     */
    public final double b;

    /**
     * Конструктор
     * @param x левая граница
     * @param x1 правая граница
     */
    public Margins(double x,double x1){
        a = x;
        b = x1;
    }
}
