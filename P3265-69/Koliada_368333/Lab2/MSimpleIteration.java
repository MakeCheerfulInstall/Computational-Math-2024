package Lab2;

/**
 * Метод простой итерации
 */
public class MSimpleIteration extends MethodsBase{
    @Override
    public boolean isFunSuitable(IFunction fun, double a, double b) {
        return !(Math.abs(fun.f1Of(a)) >= 1 || Math.abs(fun.f1Of(b)) >= 1);
    }

    @Override
    public String toString() {
        return "Метод простой итерации";
    }

    /**
     * Расчет корня уравнения методом простой итерации
     * @param params параметры расчета
     */
    @Override
    protected void startCalculation(CalcParams params) {
        //Запрашиваем подходящую эквивалентную функию - функция имеет возможность
        // передать эквивалентную функцию методу на проверку годности,
        // для этого методо передается первым параметром
        IFunction phi = _function.getPhiFunction(this,params.xa,params.xb);
        //Проверяем условие сходимости полученной эквивалентной функции
        if (Math.abs(phi.f1Of(params.xa)) >= 1 || Math.abs(phi.f1Of(params.xb)) >= 1)
            throw new CalcErrorException(String.format("Не соблюдается условие сходимости на отрезке %f %f",
                    params.xa, params.xb));

        //Устанавливаем начальное приближение
        double x0 = params.xa;
        double x,fix,fx;
        //Вычисляем функцию от начального приближения
        fix = phi.functionOf(x0);
        int counter = 0;
        do{
            //Далее вычисляем следующий x от эквивалентной функции
            x = fix;
            fix = phi.functionOf(x);
            fx = _function.functionOf(x); //Значение самой функции нам надо для проверки на то,
            // что решение удовлетворяет точности
            log(String.format("Xi %f Xi+1 %f Phi(Xi+1) %f f(Xi+1) %f |Xi+1 - Xi| %f)\n",
                    x0,x,fix,fx,Math.abs(x - x0)));
            if(Math.abs(fx) < params.precision) break;
            if(Math.abs(x - x0) < params.precision) break;
            x0 = x;
        }while(++counter < 100);

        if(counter == 100){
            throw new CalcErrorException("За 100 итераций результат не определен!");
        }
        _foundX = x;
    }

    /**
     * Выводит сообщение в журнал
     * @param message сообщение
     */
    private void log(String message){
        if(_logger != null) {
            _logger.log(message);
        }
    }

    /**
     * Переопределяем метод, чтобы не работала базовая реализация
     */
    @Override
    protected void logSteps() {

    }
}
