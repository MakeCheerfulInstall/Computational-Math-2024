package Lab2;

/**
 * Метод половинного деления
 */
public class MHalfDivision extends MethodsBase{
    @Override
    public String toString() {
        return "Метод половинного деления";
    }

    /**
     * Вычисление корня методом половинного деления
     * @param params параметры расчета
     */
    @Override
    protected void startCalculation(CalcParams params) {
        //Запоминаем начальный отрезок
        double a = params.xa;
        double b = params.xb;
        double x;
        // Очищаем список итерацийи счетчик итераций
        steps.clear();
        _iterations = 0;
        do {
            _iterations++;
            // вычисляем текущий х
            x = (a + b) / 2;
            //Вычисляем функцию от x и границ текущего отрезка
            var f0 = _function.functionOf(x);
            var fa = _function.functionOf(a);
            var fb = _function.functionOf(b);

            //Запоминаем текущую итерацию
            steps.add(new StepDescription(a,b,x,fa,fb,f0));
            //Проверяем условие завершения
            if(Math.abs(b-a) < params.precision){
                break;
            }
            if(Math.abs(f0) < params.precision){
                break;
            }
            //Переходим на следующую итерацию меняя или левую или правую границу,
            // в зависимости от того, с какой стороны находится корень
            if(f0 * fa < 0) b = x;
            else a = x;

        } while(_iterations < 101);
        // Проверяем, есть ли решение
        if(_iterations == 101)
            throw new CalcErrorException(String.format("Не смогли найти решение за %d итераций",_iterations));
        //Записываем найденное решение
        _foundX = x;
    }
}
