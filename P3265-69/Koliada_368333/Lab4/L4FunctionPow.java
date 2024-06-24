package Lab4;

import Lab2.CalcErrorException;

/**
 * Класс степенной функции fi(x) = a* pow(x,b)
 * Линеаризуем получем ln(fi(x)) = ln(a) + b * ln(x)
 * Таким образом для полинома первой степени подставляем ln(y) и ln(x),
 * получаем a[0] - ln(a[0])
 * окончательно преобразуем a[0] = pow(e,a[0]) - это и будет a для нашей функциц
 * b = a[1]
 */
public class L4FunctionPow extends L4FunctionPoly {
    public L4FunctionPow() {super(1);}

    @Override
    public String getFunctionName() {
        return "Степенная функция";
    }

    @Override
    public void CalcA(Dots dots) {
        super.CalcA(dots);
        //Пересчитываем a[0]
        _a[0] = Math.exp(_a[0]);
        // Оставляем без изменений a[1]
    }

    @Override
    protected double getY(Dots dots, int index) {
        double vl = dots.getDotY(index);
        if(vl <= 0) {
            _wasError = true;
            throw  new CalcErrorException("Для степенной функции значение Y в массиве точек не может быть <= 0");
        }
        return Math.log(vl);
    }

    @Override
    protected double getX(Dots dots, int index) {
        double vl = dots.getDotX(index);
        if(vl <= 0) {
            _wasError = true;
            throw  new CalcErrorException("Для степенной функции значение X в массиве точек не может быть <= 0");
        }
        return Math.log(vl);
    }

    @Override
    public String toString() {
        return String.format("%f*pow(x,%f)",_a[0],_a[1]);
    }

    @Override
    public double functionOf(double x) {
        return _a[0]*Math.pow(x, _a[1]);
    }
}
