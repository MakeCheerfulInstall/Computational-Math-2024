package Lab4;

import Lab2.CalcErrorException;

/**
 * Класс логарифмической функции fi(x) = a + b*ln(x)
 * Чтобы не путаться с a и b вот так сделаем, а не как в лекции
 * Таким образом для полинома первой степени подставляем y и ln(x),
 * получаем a[0] - a, a1[1] - b
 */

public class L4FunctionLog extends L4FunctionPoly {
    public L4FunctionLog() {super(1);}

    @Override
    public String getFunctionName() {
        return "Логарифмическая функция";
    }

    @Override
    protected double getX(Dots dots, int index) {
        double vl = dots.getDotX(index);
        if(vl <= 0) {
            _wasError = true;
            throw  new CalcErrorException("Для логарифмической функции значение X в массиве точек не может быть <= 0");
        }
        return Math.log(vl);
    }

    @Override
    public String toString() {
        return String.format("%f*log(x) + %f",_a[1],_a[0]);
    }

    @Override
    public double functionOf(double x) {
        return _a[0] + Math.log(x)*_a[1];
    }
}
