package Computational.math.GraphicPart.FunctionsComponents.SystemFunctions;

import Computational.math.GraphicPart.MainComponents.Axes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SystemFunctionPainterComponent extends JComponent {
    private List<Function<Double,Double>> functionList = new ArrayList<>();
    public SystemFunctionPainterComponent(ArrayList<Function<Double,Double>> functionList){
        this.functionList = functionList;
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        //iterating system from the list
        for (int i = 0; i < functionList.size(); i++) {
            double xMin = -4d;
            double xMax = 4d;
            double xCenter = (double) getWidth() / 2;
            double yCenter = (double) getHeight() / 2;
            int xTickSpacing = getWidth() / Axes.NUM_TICKS_X;
            int yTickSpacing = getHeight() / Axes.NUM_TICKS_X;
            for (double xi = xMin; xi < xMax; xi = xi + 0.001) {
                double yi = yCenter - yTickSpacing * functionList.get(i).apply(xi);
                gr.draw(new Rectangle2D.Double(xi * xTickSpacing + xCenter, yi, 0.00001, 0.000001));
            }
        }
    }
}
