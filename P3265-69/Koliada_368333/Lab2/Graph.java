package Lab2;

/**
 * Класс представляющий точки на графике. Координаты в пикселях. При отрисовке
 * графика функции должны создать этот объект, после чего он должен быть передан DrawPanel
 */
public class Graph {
    /**
     * Количестов точек в графике
     */
    private int _nPoints;
    /**
     * X координаты точек
     */
    private int[] _xPoints;
    /**
     * Y координаты точек
     */
    private  int[] _yPoints;

    public  void setN(int n) {_nPoints = n;}
    public int getN() {return _nPoints;}
    public void setXPoints(int [] xs){_xPoints = xs;}
    public int[] getXPoints() { return _xPoints;}
    public void setYPoints(int[] ys){
        _yPoints = ys;
    }
    public  int[] getYPoints() {return _yPoints;}
}
