package Lab2;

/**
 * Расчет нелинейного уравнения методом секущих
 */
public class MSecant extends MethodsBase {

    @Override
    public String toString() {
        return "Метод секущих";
    }

    /**
     * Расчет неоинейного уравнения методом секущих
     * @param params параметры расчета
     */
    @Override
    protected void startCalculation(CalcParams params) {
        // Xj-1 - это x0
        // Xj - это x1
        // Xj+1 - это x
        double x0, x1, x;
        double fx0, fx1, fx;
        // Выбираем начальное приближение
        x0 = _function.functionOf(params.xa) * _function.f2Of(params.xa) > 0 ? params.xa : params.xb;
        x1 = (params.xb + params.xa) / 2;
        fx0 = _function.functionOf(x0);
        fx1 = _function.functionOf(x1);
        do {

            x = x1 - (x1 - x0) / (fx1 - fx0) * fx1;
            fx = _function.functionOf(x);

            log(String.format("Xi-1 %f Xi %f Xi+1 %f fXi+1 %f |Xi+1-Xi| %f\n",
                    x0, x1, x, fx, Math.abs(x - x1)));
            if (Math.abs(x - x1) <= params.precision) break;
            if (Math.abs(fx) <= params.precision) break;

            x0 = x1;
            x1 = x;
            fx0 = fx1;
            fx1 = fx;
        } while (true);
        _foundX = x;
    }

    /**
     * Вывод сообщения в жэурнал
     * @param message сообщение
     */
    private void log(String message) {
        if (_logger != null) _logger.log(message);
    }

    /**
     * Переопределяем базовый метод на пустой, чтобы он ничего не делал
     */
    @Override
    protected void logSteps() {

    }
}
