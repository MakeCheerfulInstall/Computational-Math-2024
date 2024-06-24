
package Lab4;

/**
 * Класс представляющий точки на графике полученные из файла
 */
public class Dots {
    /**
     * Количестов точек в графике
     */
    private int _nPoints;
    /**
     * X координаты точек
     */
    private double[] _xPoints;
    /**
     * Y координаты точек
     */
    private  double[] _yPoints;

    /**
     * Setter количества точек
     * @param n количество точек
     */
    public  void setN(int n) {_nPoints = n;}

    /**
     * getter количества точек
     * @return количество точек
     */
    public int getN() {return _nPoints;}

    /**
     * setter массива координат x
     * @param xs массив координат
     */
    public void setXPoints(double [] xs){_xPoints = xs;}

    /**
     * getter массива координат x
     * @return массив координат x
     */
    public double[] getXPoints() { return _xPoints;}

    /**
     * setter координат Y
     * @param ys массив координат y
     */
    public void setYPoints(double[] ys){
        _yPoints = ys;
    }

    /**
     * getter координат y
     * @return массив координат y
     */
    public  double[] getYPoints() {return _yPoints;}

    /**
     * возвращает x координатуц для i-той точки
     * @param index индекс точки
     * @return x координата
     */
    public double getDotX(int index) {
        if(index < 0 || index >= _nPoints) throw new IndexOutOfBoundsException();
        return _xPoints[index];
    }

    /**
     * возвращает y координату для i-ой точки
     * @param index индекс точки
     * @return y координата
     */
    public double getDotY(int index) {
        if(index < 0 || index >= _nPoints) throw new IndexOutOfBoundsException();
        return _yPoints[index];
    }
}
