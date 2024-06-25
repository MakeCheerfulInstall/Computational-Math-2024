package Lab2;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для методов вычсиддения нелинейных уравнений
 */
public abstract class MethodsBase implements IMethod{
    @Override
    public boolean isFunSuitable(IFunction fun,double a, double b) {
        return true;
    }
    @Override
    public double getCalculatedRoot() {
        return _foundX;
    }
    @Override
    public void calculate(IFunction func, CalcParams params) {
        _function = func;
        // Если в параметрах не были указаны граничные значения,
        // то они будут установлены в методе checkParams
        checkParams(params);

        startCalculation(params);

        logSteps();
    }

    /**
     * Класс, содержащий расчетные данные, возникающие на одном шаге итерации для
     * методов половинного деления и метода хорд
     */
    protected class StepDescription {
        public double a;
        public double b;
        public double x;
        public double fa;
        public double fb;
        public double fx;
        public double dif;
        public StepDescription(double a,double b,double x, double fa, double fb, double fx){
            this.fa = fa;
            this.fb  = fb;
            this.fx = fx;
            this.a = a;
            this.b = b;
            this.x = x;
            dif = Math.abs(a-b);
        }
        public StepDescription(double a,double b,double x, double fa, double fb, double fx, double dif){
            this.fa = fa;
            this.fb  = fb;
            this.fx = fx;
            this.a = a;
            this.b = b;
            this.x = x;
            this.dif = Math.abs(dif);
        }
    }

    /**
     * Абстрактный метод производит вычислдение корня
     * @param params параметры расчета
     */
    protected abstract void startCalculation(CalcParams params);

    /**
     * Метод вычисляет отрезки, на которых есть корни уравнения.
     * Корни ищутся на отрезках, которые последовательно 10 раз расширяются.
     * Первый отрезок -10 10, второй -20 20, третий -30 30, и.т.д.
     * @return Список грниц Margins, на которых обнаружено наличие корней
     */
    private List<Margins> calcAB(){

        double xa = -10, xb = 10;
        for(int expandCounter = 0; expandCounter < 10; expandCounter++){
            var roots = CheckRootExistence(xa,xb); // Возвращает список корней на указанном отрезке
            if(!roots.isEmpty()){
                return roots; // Если отрезки есть, то возвращает их
            }
            // Расширим границы
            xa -= xa;
            xb += xb;
        }
        throw  new CalcErrorException("Не смогли определить граничные значения!");
    }

    /**
     * Метод проверяет параметры на корректность.
     * @param params Параметры для расчета
     */
    protected void checkParams(CalcParams params) {
        // Если границы отрезка не указаны
        if(params.xa == 0 && params.xb == 0){
            // Запросить возможные границы
            var margins = calcAB();
            // Если есть хоть один отрезок с корнями,
            // то записать границы первого отрезка (самого левого) в параметры
            if(!margins.isEmpty()) {
                params.xa = margins.getFirst().a;
                params.xb = margins.getFirst().b;
            }
        }
        else { // Если границы отрезка указаны в параметрах
            var margins = CheckRootExistence(params.xa, params.xb); // Проверяем наличие корня на отрезке
            if (margins.isEmpty()) throw new CalcErrorException(
                    String.format("На отрезке от %f до %f не ни одного корня!", params.xa, params.xb));
            // Проверим, что на отрезке один корень
            if (margins.size() > 1) throw new CalcErrorException(
                    String.format("На отрезке от %f до %f обнаружено больше одного корня!", params.xa, params.xb));
            // Проверим, что указана корректная точность
            if (params.precision <= 0) throw new CalcErrorException("Точность не может быть меньше или равна 0");
        }
    }

    /**
     * Метод выводит данные итераций в журнал
     */
    protected  void logSteps() {
        if(_logger == null) return; // Если логгер не подключен, то ничего не делаем
        for(var step:steps){
            _logger.log(String.format("a %f b %f x %f fa %f fb %f fx %f |a-b|%f\n",
                    step.a,
                    step.b,
                    step.x,
                    step.fa,
                    step.fb,
                    step.fx,
                    Math.abs(step.a - step.b)));
        }
    }

    /**
     * Метод проверяет таблицным методом наличие корней на указанном отрезке
     * @param xa левая граница отрезка
     * @param xb правая граница отрезка
     * @return Список отрезков с шагом step, на которых найдены корни уравнения
     */
    private List<Margins> CheckRootExistence(double xa, double xb) {
        /*
        Если непрерывная функция f(x) на отрезке [a; b]
        принимает на концах отрезка значения разных знаков, а
        производная f'(x) сохраняет знак внутри отрезка (т.е. f(x) монотонна),
        то внутри отрезка существует единственный корень уравнения f(x) = 0.
        */
        // Подсчитаем количество решений на отрезке xa xb табличным методом

        //Вычисляем начальное значение на левом конце отрезка
        double lastY =  _function.functionOf(xa);
        //Создаем спсисок для хранения отрезков с корнями
        ArrayList<Margins> rootsMargins = new ArrayList<>();

        for(double x = xa+step; x <= xb; x += step){
            var y = _function.functionOf(x);
            if(lastY * y < 0){
                rootsMargins.add(new Margins(x-step,x));
            }
            lastY = y;
        }
        return  rootsMargins;
    }

    /**
     * Список шагов вычислительного процесса
     */
    protected ArrayList<MHalfDivision.StepDescription> steps = new ArrayList<>();

    /**
     * Метод записывает журнал, куда можно выводить сообщения
     * @param logger
     */
    @Override
    public void setLogger(ILogger logger) {
        _logger = logger;
    }

    /**
     * Журнал для вывода сообщений
     */
    protected ILogger _logger = null;
    /**
     * Количество итераций, за которое удалось определить корень с указанной точностью
     */
    protected int _iterations;
    /**
     * Найденный корень уравнений
     */
    protected double _foundX;
    /**
     * Функция, корень которой вычислет метод
     */
    protected IFunction _function = null;
    /**
     * Шаг для определения корней на указанном отрезке
     */
    protected double step = 0.1;

}
