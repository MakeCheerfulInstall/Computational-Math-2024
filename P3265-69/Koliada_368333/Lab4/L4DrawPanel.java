package Lab4;

import Lab2.Graph;
import Lab2.IFunction;

import javax.swing.*;
import java.awt.*;

/**
 * Панель, на которой отображаются графики функций
 */
public class L4DrawPanel extends JPanel {
    public L4DrawPanel() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Массив функций для отображения, первый график отображается синим цветом, остальные красным
     */
    private IFunction[] _function = null;

    /**
     * Устанавливает 1 график, для отображения
     * @param fun одна функция, для отображения графика на панели
     */
    public void setFunction(IFunction fun) {
        _function = new IFunction[]{fun};
    }

    /**
     * Устанавливает массив функций, графики которых нужно отображать на панели
     * @param funs
     */
    public  void setFunctions(IFunction[] funs) {_function=funs;}

    /**
     * Минимальна доступная координта по x в пикселях
     */
    private int _minX = 0;
    /**
     * Максимально достпная координата по х в пикселях
     */
    private int _maxX = 0;
    /**
     * Минимальная координата по y в пикселях
     */
    private int _minY = 0;
    /**
     * Максимальная координата по y в пикселях
     */
    private int _maxY = 0;
    /**
     * Размер одного сантиметра в пикселях
     */
    private int _smw = 0;
    private int _smX = 0;
    private int _smY = 0;

    private double _xFactor = 1;
    private double _yFactor = 1;

    /**
     * X Координата точки 0 координатной системы графиков в пикселях
     */
    private int _xzero = 0;
    /**
     * Y Координата точки 0 координатной системы графиков в пикселях
     */
    private int _yzero = 0;

    /**
     * Метод прорисовывает оси координат
     * @param g
     */
    private void drawAxes(Graphics2D g) {

        if(reCalcFactors(g)) return; // Перестроили под график

        // 1 inch == 254 mm
        var size = getSize(); // Размер панели
        var insets = getInsets(); // рамки, на размер которых нужно уменьшить доступное пространство панели
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution(); // Текущая плотность экрана
        double dpsm = dpi / 2.54; // Количество пикселей на 1 см
        _smw = (int) dpsm;
        _smX = (int)(_smw * _xFactor + 0.5);
        _smY = (int)(_smw * _yFactor + 0.5);
        //Вычисляем  ширину и высоту учатска панели, доступного для рисования в пикселях
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        // Определяем точки центра координат и крайние ограничения координат
        _xzero = w / 2;
        // Оставим слева и справа по 50 пикселей свободного места
        int xmin = 50;
        int xmax = w - 50;
        _yzero = h / 2;
        //Оставим сверху 10 пикселей и снизу 20
        int ymin = 10;
        int ymax = h - 10;
        drawAxes(g, xmin,xmax,ymin, ymax,w,h);
    }

    private void drawAxes(Graphics2D g,int xmin, int xmax, int ymin, int ymax,int w,int h) {

        int half = _smw/4;

        int xZero = Math.max(_xzero, half);
        int yZero = Math.min(_yzero, h-half);

        //int xZero = _xzero;
        //int yZero = _yzero;

        //Рисуем Ось X
        g.drawLine(xZero, ymin, xZero, ymax);
        //Рисуем Ось Y
        g.drawLine(xmin, yZero, xmax, yZero);
        // Рисуем Названия осей
        g.drawString("Y", xZero + 5, ymin + 5);
        g.drawString("X", xmax, yZero - 10);


        // Сантиметровая насечка на оси X
        int x2; // Координаты отрицательных насечек
        int x1;          // Координаты положительных насечек
        int sm1 = 0, sm2 = 0; // числа на насечках
        for (x1 = _xzero + _smX; x1 < xmax; x1 += _smX) {
            g.drawLine(x1, yZero - 5, x1, yZero + 5);
            g.drawString(String.format("%d", ++sm1), x1, yZero - 5);
        }

        for (x2 = _xzero - _smX; x2 > xmin; x2 -= _smX) {
            g.drawLine(x2, yZero - 5, x2, yZero + 5);
            g.drawString(String.format("%d", --sm2), x2, yZero - 5);
        }

        _minX = x2;
        _maxX = x1 - _smX;
        // Сантиметровая насечка на оси Y
        int y2; // Координаты отрицательных насечек
        int y1;         // Координаты положительных насечек
        sm1 = 0;        //Числа на положительных насечках
        sm2 = 0;        //Числа на отрицательных насечках
        for (y1 = _yzero + _smY; y1 < ymax; y1 += _smY) {
            g.drawLine(xZero - 5, y1, xZero + 5, y1);
            g.drawString(String.format("%d", --sm1), xZero + 5, y1);
        }

        for (y2 = _yzero - _smY; y2 > ymin; y2 -= _smY) {
            g.drawLine(xZero - 5, y2, xZero + 5, y2);
            g.drawString(String.format("%d", ++sm2), xZero + 5, y2);
        }

        _minY = y2;
        _maxY = y1;

    }


    /**
     * Метод запускает процесс рисования. Процесс рисования запускается
     * или пользователем при вызове метода repaint(), либо ОС, при перерисовке панели.
     * @param g - область рисования
     */
    private void doDrawing(Graphics g) {
        var g2d = (Graphics2D) g;
        //Рисуем оси
        drawAxes(g2d);
        // Рисуем точки на панели
        drawGraph(g2d);
        //Рисуем график
        drawGrafic(g2d);

    }

    /**
     * Точки для отображения на графике в натуральных координатах
     *
     */
    private Dots _dots = null;
    public void setDots(Dots gr) {
        _dots = gr;
        //Вичислим макс и мин
        double xMin = 0, xMax = 0, yMin = 0, yMax = 0;
        for(int i = 0; i < gr.getN();i++)
        {
            if(i == 0){
                xMax = xMin = gr.getDotX(i);
                yMax = yMin = gr.getDotY(i);
            }
            else {
                xMin = Math.min(xMin, gr.getDotX(i));
                xMax = Math.max(xMax, gr.getDotX(i));
                yMin = Math.min(yMin, gr.getDotY(i));
                yMax = Math.max(yMax, gr.getDotY(i));
            }
        }
        setMinMax(Math.floor(xMin-1), Math.floor(xMax+1),Math.floor(yMin-1), Math.floor(yMax+1));
    }
    private void drawGraph(Graphics2D g) {
        if(_dots  == null) return;
        int w = 4;
        g.setColor(Color.black);
        for(int i = 0; i < _dots.getN();i++){
            int x = (int)Math.floor(_dots.getXPoints()[i]*_smX) + _xzero-w;
            int y = _yzero - (int)Math.floor( _dots.getYPoints()[i]*_smY) -w;
            g.fillOval(x,y,2*w,2*w);
        }
    }
    private Color[] _colors = new Color[]{Color.blue,Color.red,Color.cyan, Color.green,Color.magenta,Color.yellow};

    private Color getColor(int index){
        if(index < _colors.length && index >= 0) return _colors[index];
        return Color.darkGray;
    }

    /**
     * Коэффициент заполняемости площади
     */
    private double _kz = 1.25;
    /**
     * Минимальные и максимальные натуральные величины (не в пикселях),
     * на которые нужно ориентироваться
     */
    private  double _xMinR;
    private double _xMaxR;
    private double _yMinR;
    private double _yMaxR;

    public void setMinMax(double xMin, double xMax, double yMin, double yMax){
        _xMinR = xMin;
        _xMaxR = xMax;
        _yMinR = yMin;
        _yMaxR = yMax;
    }

    private boolean reCalcFactors(Graphics2D g){
        if(_xMinR == _xMaxR || _yMinR == _yMaxR) return false;
        var size = getSize(); // Размер панели
        var insets = getInsets(); // рамки, на размер которых нужно уменьшить доступное пространство панели

        //Вычисляем  ширину и высоту учатска панели, доступного для рисования в пикселях
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;

        double xSize = _xMaxR - _xMinR;
        double ySize = _yMaxR - _yMinR;
        _xFactor =w/(xSize*_kz*_smw);
        _yFactor =h/(ySize*_kz*_smw);
        _smX = (int)(_smw * _xFactor + 0.5);
        _smY = (int)(_smw * _yFactor + 0.5);

        int xMinPix = (int)(Math.floor(xSize * _smX *(_kz-1)/2));
        _xzero = (int)Math.floor(xMinPix - _xMinR * _smX);
        _minX = 20;
        _maxX = w - 20;

        int yMinPix = h - (int)(Math.floor(ySize * _smY *(_kz-1)/2));
        _yzero = (int)(yMinPix + _yMinR * _smY);
        _minY = 10;
        _maxY = h - 10;

        drawAxes(g,_minX,_maxX,_minY,_maxY,w,h);
        return true;
    }

    /**
     * Рисует графики функций на поверхности панели
     * @param g класс для рисования графика
     */
    private void drawGrafic(Graphics2D g) {
        if (_function == null) return;
        int iCount = 0; // Нужен, чтобы написать сами формулу функции с вертикальным отступом друг от друга
        //Цикл по все функциям, график которых нужно нарисовать
        for(IFunction fun:_function) {
            g.setColor(getColor(iCount++));
            Graph gph; // Граф, который должна создать функция
            if(fun.isDrawByY()){ // Если график рисуется по координатам y
                int y0 = (_minY - _yzero) / _smY-1;
                int ystop = (_maxY - _yzero) / _smY+1;
                // То создаем граф по Y
                gph = fun.getGraficByY(y0, ystop, _xzero, _yzero, _smX,_smY);
            }
            else {
                int x0 = (_minX - _xzero) / _smX;
                int xstop = (_maxX - _xzero) / _smX;
                //иначе по x
                gph = fun.getGrafic(x0, xstop, _xzero, _yzero, _smX,_smY);
            }

            // Рисуем граф на панели
            g.drawPolyline(gph.getXPoints(), gph.getYPoints(), gph.getN());
            // Рисуем текстовое представление функции
            g.drawString(fun.toString(), 5, iCount * 15+10);
        }
    }

    /**
     * Метод вызывается java при необходимости отрисовать поверхность панели
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Рисуем график
        doDrawing(g);
    }
}
