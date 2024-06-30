package Computational.math.GraphicPart.FunctionsComponents.DotsFunction;

import Computational.math.GraphicPart.MainComponents.Axes;
import Computational.math.Utils.FunctionalTable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Технически, этот класс является классом костылём исключительно для 5 лабораторной работы
 */
public class DotsFunction extends JComponent {
    private FunctionalTable inputTable;
    private FunctionalTable newtonFunctionTable;

    public DotsFunction(FunctionalTable inputTable,FunctionalTable newtonFunctionTable){
        this.inputTable = inputTable;
        this.newtonFunctionTable = newtonFunctionTable;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Важно вызывать super.paintComponent для правильной отрисовки
        Graphics2D gr = (Graphics2D) g;
        final double xCenter = (double) getWidth() / 2;
        final double yCenter = (double) getHeight() / 2;
        final int xTickSpacing = getWidth() / Axes.getNumTicksX();
        final int yTickSpacing = getHeight() / Axes.getNumTicksX();

        // Получение всех значений из таблицы
        Double[] allX = inputTable.getxArr();
        Double[] startTableY = inputTable.getyArr();

        // Установка цвета для точек и линий
        gr.setColor(Color.RED);

        // Проход по всем точкам и отрисовка линий и точек
        for (int i = 0; i < inputTable.dimension() - 1; i++) {
            double xi = allX[i] * xTickSpacing + xCenter;
            double yi = yCenter - startTableY[i] * yTickSpacing;

            double xiFuture = allX[i + 1] * xTickSpacing + xCenter;
            double yiFuture = yCenter - startTableY[i + 1] * yTickSpacing;

            // Отрисовка линии
            gr.draw(new Line2D.Double(xi, yi, xiFuture, yiFuture));

            // Отрисовка точек
            gr.fill(new Ellipse2D.Double(xi - 2, yi - 2, 10, 10));
            gr.fill(new Ellipse2D.Double(xiFuture - 2, yiFuture - 2, 10, 10));

        }
        //ньютоновская проходка(можно в принципе выше закинуть, но ЫЫЫ)
        var newtonY = newtonFunctionTable.getyArr();
        for (int i = 0; i < newtonFunctionTable.dimension() - 1; i++) {
            double xi = allX[i] * xTickSpacing + xCenter;
            double yi = yCenter - newtonY[i] * yTickSpacing;

            double xiFuture = allX[i + 1] * xTickSpacing + xCenter;
            double yiFuture = yCenter - newtonY[i + 1] * yTickSpacing;

            // Отрисовка линии
            gr.setColor(Color.GREEN);
            gr.draw(new Line2D.Double(xi, yi, xiFuture, yiFuture));

            // Отрисовка точек
            gr.fill(new Ellipse2D.Double(xi - 2, yi - 2, 4, 4));
            gr.fill(new Ellipse2D.Double(xiFuture - 2, yiFuture - 2, 4, 4));

        }

        // Отрисовка последней точки
        double lastX = allX[inputTable.dimension() - 1] * xTickSpacing + xCenter;
        double lastY = yCenter - startTableY[inputTable.dimension() - 1] * yTickSpacing;
        gr.fill(new Ellipse2D.Double(lastX - 2, lastY - 2, 4, 4));
    }
}
