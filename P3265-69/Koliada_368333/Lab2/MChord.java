package Lab2;

/**
 * Класс Расчетный метод хорд
 */
public class MChord extends MethodsBase {

    @Override
    public String toString() {
        return "Метод хорд";
    }

    /**
     * Расчет корня методом хорд
     * @param params параметры расчета
     */
    @Override
    protected void startCalculation(CalcParams params) {
        //Запоминаем границы отрезка
        double a = params.xa;
        double b = params.xb;
        //Вычисляем граничные значения функции
        var fa = _function.functionOf(a);
        var fb = _function.functionOf(b);
        //Определяем начальное значение x0
        double x0 = a - ((b - a)/(fb - fa))*fb;
        double x,fx;
        // Очищаем список итераций
        steps.clear();
        _iterations = 0;
        do {
            _iterations++;

            //Вычисляем функции на текущих границах отрезка
            fa = _function.functionOf(a);
            fb = _function.functionOf(b);
            // Вычисляем следующий x и fx
            x = (a * fb - b * fa)/(fb - fa);
            fx = _function.functionOf(x);
            //Запоминаем итерацию
            steps.add(new StepDescription(a,b,x,fa,fb,fx,x - x0));
            //Проверяем условие окончания расчетов
            if(Math.abs(x-x0) < params.precision){
                break;
            }
            if(Math.abs(fx) < params.precision){
                break;
            }
            //Меням x0 и текущие границы отрезка на следующие
            x0=x;
            if(fx * fa < 0){
                b = x;
            }
            else {
                a = x;
            }
        } while(_iterations < 101); // Позволим 100 итераций
        // Проверяем, есть ли решение
        if(_iterations == 101)
            throw new CalcErrorException(String.format("Не смогли найти решение за %d итераций",_iterations));
        //Записываем найденное решение
        _foundX = x;
    }

    /**
     * Метод выводит данные итераций в журнал
     */
    @Override
    protected void logSteps() {
        if(_logger == null) return;
        for(var step:steps){
            _logger.log(String.format("a %f b %f x %f fa %f fb %f fx %f |Xn+1-Xn|%f\n",
                    step.a,
                    step.b,
                    step.x,
                    step.fa,
                    step.fb,
                    step.fx,
                    step.dif));
        }
    }
}
