package Lab4;

import Lab2.CalcErrorException;
import Lab2.IFunction;
import Lab2.ILogger;

/**
 * Класс калькулятор всего
 */
public class L4Calculator {
    /**
     * Журнал для вывода результатов и сообщений
     */
    private ILogger _logger;
    /**
     * Заданные точки графика (таблица значений)
     */
    private Dots _dots = null;
    /**
     * Панель для отображения графиков
     */
    private L4DrawPanel drawPanel;

    /**
     * Конструктор калькулятора
     * @param dots заданные точки графика
     * @param logger журнал для вывода результатов
     */
    public L4Calculator(Dots dots,ILogger logger) {
        if(logger == null) throw new NullPointerException("logger is null!");
        _dots = dots;
        _logger = logger;
    }

    /**
     * Передает калькулятору панель отображения
     * @param drawPanel - панель
     */
    public void setDrawPanel(L4DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    /**
     * Оставляет в решении только те функции, при определении которых не произошли ошибки
     * @param fs массив всех функций
     * @param errors количество функций с ошибками
     * @return массив функций без ошибок
     */
    private static L4FunctionPoly[] leaveGoodFunctions(L4FunctionPoly[] fs, int errors) {
        if(errors == 0)return fs;
        L4FunctionPoly[] goodFunctions = new L4FunctionPoly[fs.length-errors];
        int i = 0;
        for(var fun :fs){
            if(!fun.getWasError()) goodFunctions[i++] = fun;
        }
        return goodFunctions;
    }

    /**
     * метод производит собсвтенно все необходимые расчеты
     */
    public void calculate() {
        //Очищаем журнал
        _logger.clear();

        // Создаем массив типов функций для исследования
        L4FunctionPoly[] functions = new L4FunctionPoly[6];
        for (int i = 1; i <= 3; i++) {
            L4FunctionPoly func = new L4FunctionPoly(i); // В конструкторе указываем степень полинома
            functions[i-1] = func;
        }
        functions[3] = new L4FunctionPow(); //Добавляем степенную функцию
        functions[4] = new L4FunctionExp(); //Добавляем експ функцию
        functions[5] = new L4FunctionLog(); //Добавляем логарифмическую функцию

        boolean first = true; //Признак первой функции - линейная
        int errors = 0; // Счетчик функций с ошибками
        for (L4FunctionPoly func: functions) {

            try {
                func.CalcA(_dots); // Расчет коэффициентов
            } catch (CalcErrorException e) {
                _logger.log(String.format("При расчете %s возникла ошибка %s",func.getFunctionName(),e.getMessage()));
                errors += 1;
                continue;
            }

            printResults(func);
            if(first){
                first = false;
                _logger.log(String.format("Коэффициент корелляции Пирсона равен %f",pirson()));
            }
            var r2 = calcR2(func);
            _logger.log(String.format("Коэффициент детерминации равен %f",r2));
            makeConclusion(r2);
        }

        // Оставляем только функции без ошибок
        functions = leaveGoodFunctions(functions,errors);


        _logger.log(String.format("Лучшая функция %s",getBestFunction(functions).toString()));


        if(drawPanel != null) {
            drawPanel.setFunctions(functions);
            drawPanel.repaint();
        }
    }

    private void makeConclusion(double r2){
        var r = Math.abs(1 - r2);
        if(r<= 0.1) _logger.log("Фукнция аппроксимирует процесс надежно.");
        else if(r <= 0.2) _logger.log("Фукнция аппроксимирует процесс достаточно надежно.");
        else if(r <= 0.3) _logger.log("Фукнция аппроксимирует процесс не надежно.");
        else _logger.log("Фукнция плохо аппроксимирует.");
    }

    /**
     * Определение коэффициента детерминации
     * @param func функция
     * @return коэффициент детерминации
     */
    private double calcR2(IFunction func) {
        double nominator = 0;
        double denominator1 = 0;
        double denominator2 = 0;
        for(int i = 0; i < _dots.getN();i++){
            nominator += Math.pow(_dots.getDotY(i) - func.functionOf(_dots.getDotX(i)),2);
            denominator1 += Math.pow(func.functionOf(_dots.getDotX(i)),2);
            denominator2 += func.functionOf(_dots.getDotX(i));
        }
        return 1 - nominator / (denominator1 - denominator2 * denominator2 /_dots.getN());
    }

    /**
     * Выбор лучшей функции по величине среднеквадратичного отколнения
     * @param functions массив функций
     * @return лучшая функция
     */
    private IFunction getBestFunction(IFunction[] functions) {
        int bestN = -1;
        double minAveS = 0;
        int i =0;
        for(IFunction func : functions) {
            var valS = getS(func);
            var aveS = Math.pow(valS/ _dots.getN(),1./2.);
            if(bestN == -1) {
                minAveS = aveS;
                bestN = 0;
            }
            else if(aveS < minAveS) {
                minAveS = aveS;
                bestN = i;
            }
            i++;
        }
        return functions[bestN];
    }

    /**
     * Вычисляем коэффициент Пирсона
     * @return
     */
    private double pirson() {
        double aveX = 0;
        double aveY = 0;
        for(int i = 0; i < _dots.getN();i++) {
            aveY += _dots.getDotY(i);
            aveX += _dots.getDotX(i);
        }
        aveX /= _dots.getN();
        aveY /= _dots.getN();

        //Числитель
        double numerator = 0;
        double denominatorX = 0, denominatorY = 0;
        for(int i = 0; i < _dots.getN();i++) {
            numerator += (_dots.getDotX(i) - aveX) * (_dots.getDotY(i) - aveY);
            denominatorX += (_dots.getDotX(i)-aveX) * (_dots.getDotX(i)-aveX);
            denominatorY += (_dots.getDotY(i) - aveY) * (_dots.getDotY(i)-aveY);
        }

        return numerator / Math.pow(denominatorX*denominatorY, 1./2.);
    }

    /**
     * Вывод результатов для одной функции
     * @param fun функция для отчетности
     */
    private void printResults(IFunction fun){
        if(_logger == null) return;


        _logger.log("Расчеты для функции " + fun.toString());

        String[] line = new String[_dots.getN() + 1];
        line[0] = String.format("%12.12s", "Ном пп");
        for (int i = 1; i <= _dots.getN(); i++) {
            line[i]= String.format("%12d",i);
        }
        _logger.log(String.join("|",line));

        line[0] = String.format("%12.12s", "X");
        for (int i = 1; i <= _dots.getN(); i++) {
            line[i]= String.format("%12f",_dots.getDotX(i-1));
        }
        _logger.log(String.join("|",line));

        line[0] = String.format("%12.12s", "Y");
        for (int i = 1; i <= _dots.getN(); i++) {
            line[i]= String.format("%12f",_dots.getDotY(i-1));
        }
        _logger.log(String.join("|",line));

        line[0] = String.format("%12.12s", "P(x)");
        for (int i = 1; i <= _dots.getN(); i++) {
            line[i]= String.format("%12f",fun.functionOf(_dots.getDotX(i-1)));
        }
        _logger.log(String.join("|",line));

        line[0] = String.format("%12.12s", "Ei");
        for (int i = 1; i <= _dots.getN(); i++) {
            line[i]= String.format("%12f",fun.functionOf(_dots.getDotX(i-1))
            -_dots.getDotY(i-1));
        }
        _logger.log(String.join("|",line));

        var valS = getS(fun);
        _logger.log(String.format("Мера отклонения для функции %s равна %f",
                fun.toString(),valS));

        _logger.log(String.format("Среднеквадратичное отклонение для функции %s равно %f",
                fun.toString(),Math.pow(valS/ _dots.getN(),1./2.)));

    }

    /**
     * Расчет меры отклонения многочлена function отзначений _dots.y в точках _dots.x
     * @param function - исследуемый многочлен
     * @return Мера отклонения
     */
    private double getS(IFunction function){
        double result = 0;
        for(int i = 0; i < _dots.getN();i++) {
            double val = function.functionOf(_dots.getDotX(i)) - _dots.getDotY(i);
            result += val*val;
        }
        return result;
    }
}
