package Lab2;

/**
 * Метод Ньютона решения нелинейного уравнения
 */
public class MNewtons extends MethodsBase {

    @Override
    public String toString() {
        return "Метод Ньютона";
    }

    /**
     * Переопределенный метод проверки параметров, так как нужно проверить на сходимость
     * @param params Параметры для расчета
     */
    @Override
    protected void checkParams(CalcParams params) {
        // Вызываем базовый метод проверки параметров
        super.checkParams(params);
        //Если все ок - не было throw, то проверяем сходимость
        checkConvergence(params);
    }

    /**
     * Метод проверяем сходимость на указанном отрезке
     * @param params параметры расчета
     */
    private void checkConvergence(CalcParams params) {
        if(_function.functionOf(params.xa)* _function.functionOf(params.xb) >= 0)
            throw  new CalcErrorException("Не соблюдается условие сходимости - разные знаки функции на начальных границах");
        double step = 0.05;
        for(double x = params.xa+step; x <= params.xb;x += step){
            if((_function.f1Of(x) > 0) != (_function.f1Of(x-step) > 0))
                throw  new CalcErrorException("Нет сходимости - первая производная меняет знак");
            if((_function.f2Of(x) > 0) != (_function.f2Of(x-step) > 0))
                throw  new CalcErrorException("Нет сходимости - вторая производная меняет знак");
            if(_function.f1Of(x) == 0) throw new CalcErrorException("Нет сходиомости - первая производная равна 0");
        }

    }

    /**
     * Расчет методом Ньютона
     * @param params параметры расчета
     */
    @Override
    protected void startCalculation(CalcParams params) {

        double x0;
        double x, f1x0, fx0;
        // Выбираем начальное приближение
        if(_function.functionOf(params.xa) * _function.f2Of(params.xa) > 0) x0 =params.xa;
        else if(_function.functionOf(params.xb) * _function.f2Of(params.xb) > 0) x0 = params.xb;
        else throw new CalcErrorException("Не определить начальное значение");
        do {
            fx0 = _function.functionOf(x0);
            f1x0 = _function.f1Of(x0);
            x = x0 - fx0 / f1x0;

            log(String.format("Xn %f fXn %f f'Xn %f Xn+1 %f |Xn+1 - Xn| %f\n",
                    x0, fx0, f1x0, x, Math.abs(x - x0)));

            if (Math.abs(x - x0) < params.precision) break;
            if (Math.abs(fx0 / f1x0) < params.precision) break;
            if (Math.abs(fx0) < params.precision) break;

            x0 = x;

        } while (true);
        _foundX = x;
    }

    /**
     * Метод выводит сообщение в журнал
     * @param message сообщение
     */
    private void log(String message) {
        if (_logger != null) {
            _logger.log(message);
        }
    }

    /**
     * Переопределяем, чтобы ничего не делала. Логирование выполняется в самом расчете
     */
    @Override
    protected void logSteps() {

    }
}
