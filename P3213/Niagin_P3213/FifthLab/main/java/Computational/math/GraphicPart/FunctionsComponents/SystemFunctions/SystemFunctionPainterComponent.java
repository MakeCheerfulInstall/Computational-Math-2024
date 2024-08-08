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
    private final double xCenter = (double) getWidth() / 2;
    private final double yCenter = (double) getHeight() / 2;

    public SystemFunctionPainterComponent(ArrayList<Function<Double,Double>> functionList){
        this.functionList = functionList;
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        final double xMin = -50d;
        final double xMax = 50d;

        //iterating system from the list
        for (int i = 0; i < functionList.size(); i++) {
            int xTickSpacing = getWidth() / Axes.getNumTicksX();
            int yTickSpacing = getHeight() / Axes.getNumTicksX();
            for (double xi = xMin; xi < xMax; xi = xi + 0.001) {
                double yi = yCenter - yTickSpacing * functionList.get(i).apply(xi);
                gr.draw(new Rectangle2D.Double(xi * xTickSpacing + xCenter, yi, 0.00001, 0.000001));
            }
        }
    }
}
