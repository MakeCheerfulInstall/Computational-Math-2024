package Lab4;

import Lab2.CalcErrorException;

/**
 * Класс экспоненциальной функции fi(x) = a* exp(bx)
 * Линеаризуем получем ln(fi(x)) = ln(a) + b * x
 * Таким образом для полинома первой степени подставляем ln(y) и x,
 * получаем a[0] - ln(a[0])
 * окончательно преобразуем a[0] = pow(e,a[0]) - это и будет a для нашей функциц
 * b = a[1]
 */
public class L4FunctionExp extends L4FunctionPoly {
    public L4FunctionExp(){super(1);}

    @Override
    public String getFunctionName() {
        return "Экспоненциальная функция";
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
            throw  new CalcErrorException("Для экспоненциальной функции значение Y в массиве точек не может быть <= 0");
        }
        return Math.log(vl);
    }

    @Override
    public String toString() {
        return String.format("%f*exp(%f *x)",_a[0],_a[1]);
    }

    @Override
    public double functionOf(double x) {
        return _a[0]*Math.exp(x *_a[1]);
    }
}
