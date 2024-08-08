package Computational.math.GraphicPart.FunctionsComponents.CasualFunction;

import Computational.math.GraphicPart.MainComponents.Axes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.function.Function;

public class DrawFunction extends JComponent {
    private Function<Double, Double> function;
    private final double XMIN = -4;
    private final double XMAX = 4;

    public DrawFunction(Function<Double,Double> function){
        this.function = function;
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D gr = (Graphics2D) g;
        double xMin = -50d;
        double xMax = 50d;
        double xCenter = (double) getWidth() /2;
        double yCenter =(double) getHeight()/2;
        int xTickSpacing = getWidth() / Axes.NUM_TICKS_X;
        int yTickSpacing = getHeight() / Axes.NUM_TICKS_X;
        for (double xi = xMin; xi < xMax; xi = xi + 0.001) {
            double yi = yCenter - yTickSpacing * function.apply(xi);
            gr.draw(new Rectangle2D.Double(xi*xTickSpacing+xCenter,yi,0.00001,0.000001));
        }
    }
}

