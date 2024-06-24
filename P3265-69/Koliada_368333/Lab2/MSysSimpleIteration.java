package Lab2;

public class MSysSimpleIteration implements IMethodSys {
    @Override
    public void Calculate(ISysFunctions funcs, CalcParams params) {
        checkParams(params);
        checkConverge(funcs, params);
        double x0 = params.iaX;
        double y0 = params.iaY;
        ISysFunction f1 = (ISysFunction)funcs.getSysFunctions()[0];
        ISysFunction f2 = (ISysFunction)funcs.getSysFunctions()[1];

        int count = 0;
        double x,y;
        do {
            count++;
            x = f1.phi(x0,y0);
            y = f2.phi(x0,y0);

            log(String.format("Xi %f Xi+1 %f |Xi+1 - Xi| %f Yi %f Yi+1 %f |Yi+1 - Yi| %f\n",
                    x0,x,Math.abs(x-x0),y0,y,Math.abs(y-y0)));

            if(Math.abs(x -x0) <= params.precision && Math.abs(y - y0) <= params.precision) break;
            y0=y;
            x0=x;

        } while(count <=100);
        if(count > 100) throw  new CalcErrorException("Количество итераций превысило 100");
        log("Результат расчета методом простых итераций:\n");
        log(String.format("Количество итераций: %d\n",count));
        log(String.format("Вектор решений: (%f,%f)\n",x,y));
        log(String.format("Вектор погрешностей: (%f,%f)\n",Math.abs(x-x0),Math.abs(y-y0)));
        log("Проверочный расчет уравнений:\n");
        log(String.format("Уравнение %s при x=%f,y=%f результат %f\n",f1.toString(),x,y,f1.originalFunc(x,y)));
        log(String.format("Уравнение %s при x=%f,y=%f результат %f\n",f2.toString(),x,y,f2.originalFunc(x,y)));

    }

    /**
     * Проверяет введенные параметры
     * @param params
     */
    private static void checkParams(CalcParams params){
        if(params.precision <= 0) throw new CalcErrorException("Недопустимое значение требуемой точности");
        if(params.asMaxX <= params.asMinX) throw new CalcErrorException("Недопустимые значения области решения по х");
        if(params.asMaxY <= params.asMinY) throw new CalcErrorException("Недопустимое значение области решения по y");

    }

    /**
     * Проверяет условие сходимости
     * @param funcs
     * @param params
     */
    private static void checkConverge(ISysFunctions funcs,CalcParams params){
        double step = 0.1;
        ISysFunction f1 = (ISysFunction)funcs.getSysFunctions()[0];
        ISysFunction f2 = (ISysFunction)funcs.getSysFunctions()[1];
        for(double x = params.asMinX; x <= params.asMaxX; x += step){
            for(double y = params.asMinY; y <= params.asMaxY; y+= step){
                if((Math.abs(f1.phi1X(x,y)) + Math.abs(f1.phi1Y(x,y))) >= 1)
                    throw new CalcErrorException("На указанном отрезке решений не соблюдается условие сходимости.");
            }
        }
    }


    @Override
    public void setLogger(ILogger logger) {
        _logger = logger;
    }
    private ILogger _logger = null;
    private void log(String message){
        if(_logger != null) _logger.log(message);
    }

    @Override
    public String toString() {
        return "Метод простых итераций";
    }
}
