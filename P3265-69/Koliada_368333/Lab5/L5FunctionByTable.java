package Lab5;

import Lab2.Graph;
import Lab2.IFunction;

/**
 * класс табличной функции. Для отображения графика по данным из указанной таблицы
 */
public class L5FunctionByTable implements IFunction {
    /**
     * Обязаны реализовать, но использовать не будем
     * @param x функция от
     * @return
     */
    @Override
    public double functionOf(double x) {
        return 0;
    }

    /**
     * Переопределяем метод получения данных для отображения графика на панели рисвоания
     * @param xStart первый x для отображения
     * @param xStop последний х для отображения
     * @param xZero положение точки 0
     * @param yZero положение точки 0
     * @param smX масштаб по х
     * @param smY масштаб по y
     * @return граф для отображения на панели
     */
    @Override
    public Graph getGrafic(int xStart, int xStop, int xZero, int yZero, int smX,int smY) {

        // Просто пересчитываем значения из таблицы в координаты панели отображения
        Graph grh = new Graph();
        int[] xps = new int[xs.length];
        int[] yps = new int[xs.length];
        for(int i = 0; i < xs.length; i++){
            xps[i] = (int) (xs[i] * smX + xZero);
            yps[i] = (int) (yZero - ys[i] * smY);
        }

        grh.setN(xps.length);
        grh.setXPoints(xps);
        grh.setYPoints(yps);
        return grh;
    }

    /**
     * Имя функции
     */
    private String _name = null;

    @Override
    public String toString() {
        return _name == null ? "Табличная функция":_name;
    }

    /**
     * Значения функции
     */
    private double[] xs;
    private  double[] ys;

    /**
     * Содержит манимальные и максимальные значения x и у
     */
    private double[] _xyMinMax;

    public void setXY(double[] xs, double[] ys){
        this.xs = xs;
        this.ys = ys;
    }

    public void setName(String name){
        _name = name;
    }

    public void setMinMax(double [] val){
        _xyMinMax = val;
    }

    public double[] getMinMax() {
        return _xyMinMax;
    }
}
