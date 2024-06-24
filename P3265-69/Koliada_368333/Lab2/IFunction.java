package Lab2;

/**
 * Интерфейс функции нелинейного уравнения
 */
public interface IFunction {
    /**
     * Сама функция
     * @param x функция от
     * @return значение функции
     */
    default double functionOf(double x) {return 0;};
    /**
     * Вычисляет производную функции
     * @param x - значение x
     * @return значение производной
     */
    default double f1Of(double x) {return 0;}

    /**
     * Вторая производная от функции
     * @param x параметр функции
     * @return значение второй производной
     */
    default double f2Of(double x){throw  new CalcErrorException("Расчет второй производной не реализован");}

    /**
     * Возвращает приведенную функцию, для которой на указанном участке выполняется условие сходимости
     * @param a левая граница интервала значений
     * @param b правая граница интервала значений
     * @return Приведенная функция или null, если такой функции не удалось найти
     */
    default IFunction getPhiFunction(IMethod forMethod, double a, double b){return null;}

    /**
     * Создает отображение графика функции в Graph
     * @param xStart минимальное значение координаты x в см
     * @param xStop максимальное значение координаты x в см
     * @param xZero координата x центра в пикселях
     * @param yZero координата y центра в пикселях
     * @param smp количество пикселей на 1 см
     * @return Graph
     */
    default Graph getGrafic(int xStart, int xStop,int xZero, int yZero,int smp){
        return getGrafic(xStart,xStop,xZero,yZero,smp,smp);
    }
    default Graph[] getGrafics(int xStart, int xStop,int xZero, int yZero,int smp){
        return getGrafics(xStart,xStop,xZero,yZero,smp,smp);
    }
    default Graph[] getGrafics(int xStart, int xStop,int xZero, int yZero,int smX,int smY){
        Graph[] grfs = new Graph[100];
        Graph g = new Graph();
        int gCount = 0;
        grfs[gCount++] = g;
        double step = 0.01;
        int n = 0;
        int size = (int) ((xStop - xStart) / step + 1);
        int[] xs = new int[size];
        int[] ys = new int[size];
        for (double x = xStart; x <= xStop; x += step) {
            int xInPoints = (int) (x * smX + xZero);
            double y = functionOf(x);
            if (Double.isNaN(y) || y < -10000 || y > 10000){
                g.setN(n);
                g.setYPoints(ys);
                g.setXPoints(xs);
                if(n > 0) {
                    n = 0;
                    xs = new int[size];
                    ys = new int[size];
                    g = new Graph();
                    grfs[gCount++] = g;
                }
                continue;
            }
            int yInPoints = (int) (yZero - y * smY);
            if(yInPoints < -1000) continue;
            if(yInPoints > 5000) continue;
            xs[n] = xInPoints;
            ys[n] = yInPoints;
            n++;
        }
        g.setN(n);
        g.setYPoints(ys);
        g.setXPoints(xs);
        return grfs;
    }

    default Graph getGrafic(int xStart, int xStop,int xZero, int yZero,int smX,int smY){
        Graph g = new Graph();
        double step = 0.01;
        int n = 0;
        int size = (int) ((xStop - xStart) / step + 1);
        int[] xs = new int[size];
        int[] ys = new int[size];
        for (double x = xStart; x <= xStop; x += step) {
            int xInPoints = (int) (x * smX + xZero);
            double y = functionOf(x);
            if (Double.isNaN(y) || y == Double.MAX_VALUE) {
                continue;
            }
            int yInPoints = (int) (yZero - y * smY);
            if(yInPoints < -1000) continue;
            if(yInPoints > 5000) continue;
            xs[n] = xInPoints;
            ys[n] = yInPoints;
            n++;
        }
        g.setN(n);
        g.setYPoints(ys);
        g.setXPoints(xs);
        return g;
    }

    /**
     * Указывает, что график функции нужно сосздавать в виде x = f(y)
     * @return true, если график создавать в виде x = f(y)
     */
    default boolean isDrawByY() {return false;}

    /**
     * Возвращает x = f(y)
     * @param y параметр функции x= f(y)
     * @return значение x
     */
    default double functionOfY(double y) {return 0;}

    /**
     * Cоздает отображение графика функции x = f(y) в граф
     * @param yStart минимальное значение координаты y в см
     * @param yStop максимальное значение координаты y в см
     * @param xZero координата x центра в пикселях
     * @param yZero координата y центра в пикселях
     * @param smp количество пикселей на 1 см
     * @return Graph
     */
    default Graph getGraficByY(int yStart, int yStop,int xZero, int yZero,int smp){
        return getGraficByY(yStart, yStop, xZero, yZero, smp,smp);
    }
    default Graph getGraficByY(int yStart, int yStop,int xZero, int yZero,int smX,int smY){
        Graph g = new Graph();
        double step = 0.01;
        int n = 0;
        int size = (int) ((yStop - yStart) / step + 1);
        int[] xs = new int[size];
        int[] ys = new int[size];
        for (double y = yStart; y <= yStop; y += step) {
            int yInPoints = (int) (yZero - y * smY);
            double x = functionOfY(y);
            if (Double.isNaN(x) || x == Double.MAX_VALUE) continue;
            int xInPoints = (int) (xZero + x * smX);
            if(xInPoints < -1000) continue;
            if(xInPoints > 5000) continue;
            xs[n] = xInPoints;
            ys[n] = yInPoints;
            n++;
        }
        g.setN(n);
        g.setYPoints(ys);
        g.setXPoints(xs);
        return g;
    }
}

/**
 * Интерфейс для функции в системе уравнений
 */
interface  ISysFunction extends  IFunction{
    /**
     * Приведенная функция (правая часть) для определения Y
     * @param x
     * @param y
     * @return Значение Y при указанных x и y
     */
    double phi(double x, double y);


    /**
     * Перваяя производная приведенной функции по x
     * @param x
     * @param y
     * @return
     */
    double phi1X(double  x,double y);

    /**
     * Первая производная приведенной функции по Y
     * @param x
     * @param y
     * @return
     */
    double phi1Y(double x,double y);

    /**
     * Начальная функция для проверки результатов расчета
     * @param x
     * @param y
     * @return
     */
    double originalFunc(double x, double y);

    double f1x(double x, double y);
    double f1y(double x, double y);
    default boolean isCircle() {return false;}

    @Override
    default Graph getGrafic(int xStart, int xStop, int xZero, int yZero, int smp) {
        if(!isCircle())
            return IFunction.super.getGrafic(xStart, xStop, xZero, yZero, smp);
        return drawCircle(xStart, xStop, xZero, yZero, smp);
    }

    @Override
    default Graph[] getGrafics(int xStart, int xStop, int xZero, int yZero, int smp) {
        if(!isCircle())
            return IFunction.super.getGrafics(xStart, xStop, xZero, yZero, smp);
        Graph[] g = new Graph[1];
        g[0] = drawCircle(xStart, xStop, xZero, yZero, smp);
        return g;
    }

    default double getStep(double x) {
        return 0.01;
    }
    default Graph drawCircle(int xStart, int xStop, int xZero, int yZero, int smp){
        Graph g = new Graph();
        double step = 0.01;
        int n = 0;
        int size = (int) ((xStop - xStart) / step + 1);
        int[] xs = new int[size * 2 + 1];
        int[] ys = new int[size * 2 + 1];
        for (double x = xStart; x <= xStop; x += step) {
            step = getStep(x);
            int xInPoints = (int) (x * smp + xZero);
            double y = functionOf(x);
            if (Double.isNaN(y) || y == Double.MAX_VALUE) continue;
            int yInPoints = (int) Math.round(yZero - y * smp);
            xs[n] = xInPoints;
            ys[n] = yInPoints;
            n++;
        }
        for (double x = xStop; x >= xStart; x -= step) {
            step = getStep(x);

            int xInPoints = (int) (x * smp + xZero);
            double y = functionOf(x);
            if (Double.isNaN(y) ||y == Double.MAX_VALUE) continue;
            int yInPoints = (int) Math.round(yZero + y * smp);
            xs[n] = xInPoints;
            ys[n] = yInPoints;
            n++;
        }
        g.setN(n);
        g.setYPoints(ys);
        g.setXPoints(xs);
        return g;
    }
}

