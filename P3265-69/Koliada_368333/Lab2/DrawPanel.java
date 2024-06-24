package Lab2;

import javax.swing.*;
import java.awt.*;

/**
 * Панель, на которой отображаются графики функций
 */
public class DrawPanel extends JPanel {
    public DrawPanel() {
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

        // 1 inch == 254 mm
        var size = getSize(); // Размер панели
        var insets = getInsets(); // рамки, на размер которых нужно уменьшить доступное пространство панели
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution(); // Текущая плотность экрана
        double dpsm = dpi / 2.54; // Количество пикселей на 1 см
        _smw = (int) dpsm;
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

        //Рисуем Ось X
        g.drawLine(_xzero, ymin, _xzero, ymax);
        //Рисуем Ось Y
        g.drawLine(xmin, _yzero, xmax, _yzero);
        // Рисуем Названия осей
        g.drawString("Y", _xzero + 5, ymin + 5);
        g.drawString("X", xmax, _yzero - 10);


        // Сантиметровая насечка на оси X
        int x2 = _xzero; // Координаты отрицательных насечек
        int x1;          // Координаты положительных насечек
        int sm1 = 0, sm2 = 0; // числа на насечках
        for (x1 = _xzero + _smw; x1 < xmax; x1 += _smw) {
            x2 -= _smw;
            g.drawLine(x1, _yzero - 5, x1, _yzero + 5);
            g.drawString(String.format("%d", ++sm1), x1, _yzero - 5);
            g.drawLine(x2, _yzero - 5, x2, _yzero + 5);
            g.drawString(String.format("%d", --sm2), x2, _yzero - 5);
        }
        _minX = x2;
        _maxX = x1 - _smw;
        // Сантиметровая насечка на оси Y
        int y2 = _yzero; // Координаты отрицательных насечек
        int y1;         // Координаты положительных насечек
        sm1 = 0;        //Числа на положительных насечках
        sm2 = 0;        //Числа на отрицательных насечках
        for (y1 = _yzero + _smw; y1 < ymax; y1 += _smw) {
            y2 -= _smw;
            g.drawLine(_xzero - 5, y1, _xzero + 5, y1);
            g.drawLine(_xzero - 5, y2, _xzero + 5, y2);
            g.drawString(String.format("%d", ++sm2), _xzero + 5, y2);
            g.drawString(String.format("%d", --sm1), _xzero + 5, y1);
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
        //Рисуем график
        drawGrafic(g2d);

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
            iCount++;
            //Выбор цвета для грацика функции
            if(iCount == 1)
                g.setColor( Color.blue);
            else
                g.setColor(Color.red);



            Graph gph; // Граф, который должна создать функция
            if(fun.isDrawByY()){ // Если граичк рисуется по координатам y
                int y0 = (_minY - _yzero) / _smw-1;
                int ystop = (_maxY - _yzero) / _smw+1;
                // То создаем граф по Y
                gph = fun.getGraficByY(y0, ystop, _xzero, _yzero, _smw);
                // Рисем граф на панели
                g.drawPolyline(gph.getXPoints(), gph.getYPoints(), gph.getN());
            }
            else {
                int x0 = (_minX - _xzero) / _smw;
                int xstop = (_maxX - _xzero) / _smw;
                //иначе по x

                var grfs = fun.getGrafics(x0, xstop, _xzero, _yzero, _smw);
                for(var gr:grfs){
                    if(gr == null) break;
                    // Рисем граф на панели
                    g.drawPolyline(gr.getXPoints(), gr.getYPoints(), gr.getN());
                }
            }
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
