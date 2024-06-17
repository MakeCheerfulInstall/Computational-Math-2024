package Computational.math.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CalculatorTables {
    /**
     * Для вычисления конечных разностей
     * @param a левая граница интервала
     * @param b правая граница интервала x
     * @param f вычисляемая функция
     * @param amountOfPoints количество точек на интервале
     * @return таблицу с конечными разностями
     */
    public static FunctionalTable createTable(double a, double b, int amountOfPoints, Function<Double,Double> f){
        ArrayList<Double> xArr = new ArrayList<>();
        ArrayList<Double> yArr = new ArrayList<>();
        for (int i = 0; i < amountOfPoints; i++) {
            double x_i = a + (b - a) * i / amountOfPoints;
            xArr.add(x_i);
            yArr.add(f.apply(x_i));
        }
        return new FunctionalTable(xArr.toArray(new Double[0]), yArr.toArray(new Double[0]));
    }
    public static List<List<Double>> finiteDiff(List<List<Double>> data, List<Double> y) {
        if (y.size() <= 1) {
            return data;
        }
        List<Double> temp = new ArrayList<>();
        for (int i = 0; i < y.size() - 1; i++) {
            temp.add(y.get(i + 1) - y.get(i));
        }
        data.add(temp);
        return finiteDiff(data, temp);
    }
}
