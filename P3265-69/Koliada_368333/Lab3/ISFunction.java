package Lab3;

import Lab2.CalcParams;
import Lab2.IFunction;

/**
 * Интерфейс для подинтегральной функции. Расширяет базовый интерфейс IFunction, который использовалася во второй лабе
 * Он соедржит, в том числе, методы для отображения графика функции
 */
public interface ISFunction extends IFunction {
    /**
     * Метод возвращает массив ys, исчисленный функцией для входного массива xs
     * @param xs - массив значений x, для которых должны быть исчислены y
     * @return - массив исчисленных y
     */
    default double[] getValuesOf(double[] xs) {
        double[] ys = new double [xs.length];
        int index = 0;
        for(var x : xs){
            ys[index++] = functionOf(x); // метод наследуемого интерфейса IFunction
        }
        return  ys;
    }

    /**
     * Метод преобразует запрашенный пользователем интервал интегрирования
     * в массив возможных интервалов интегрирования.
     * В реализации по умолчания интервал не изменяется
     * @param parms
     * @return
     */
    default CalcParams[] splitInterval(CalcParams parms){
        var params = new CalcParams[1];
        params[0] = parms.clone();
        return params;
    }
    // Определяем здесь метод наследуемого интерфейса IFunction в виде метода по умолчанию,
    // чтобы не нужно было в самих классах определять его. Так как пока в данной задаче
    // про интегралы он не нужен

    //Метод определяения первой производной
    @Override
    default double f1Of(double x) {
        return 0;
    }

}
