package Computational.Math.GraphicPart.FunctionsComponents.ArrayDrawer;

import Computational.Math.GraphicPart.MainComponents.Axes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ArraysDrawer extends JComponent {
    private ArrayList<Double> list1; private ArrayList<Double> list2;private ArrayList<Double> list3;
    private ArrayList<Double> xList;
    private ArrayList<Double> correctList;

    public ArraysDrawer(ArrayList<Double> xList, ArrayList<Double> list1, ArrayList<Double> list2, ArrayList<Double> list3, ArrayList<Double> correctList) {
        this.list1 = list1;
        this.xList = xList;
        this.list2 = list2;
        this.list3 = list3;
        this.correctList = correctList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        final double xMin = -50d;
        final double xMax = 50d;
        final double xCenter = (double) getWidth() / 2;
        final double yCenter = (double) getHeight() / 2;
        final int xTickSpacing = getWidth() / Axes.getNumTicksX();
        final int yTickSpacing = getHeight() / Axes.getNumTicksX();
        int minLen = Math.min(Math.min(Math.min(xList.size(),list1.size()),list2.size()),list3.size());
        int dotW = 2;int dotY = 5;
        for (int i = 0; i < minLen; i++) {
            double xi = xList.get(i)*xTickSpacing+xCenter;
            double first_yi = yCenter - yTickSpacing * list1.get(i);
            gr.setColor(Color.green);
            gr.draw(new Rectangle2D.Double(xi,first_yi,dotW,dotW));


            double second_yi = yCenter - yTickSpacing * list2.get(i);
            gr.setColor(Color.MAGENTA);
            gr.draw(new Rectangle2D.Double(xi,second_yi,dotW,dotW));

            double third_yi = yCenter - yTickSpacing * list3.get(i);
            gr.setColor(Color.RED);
            gr.draw(new Rectangle2D.Double(xi,third_yi,dotW,dotW));

            double correctYi = yCenter - yTickSpacing*correctList.get(i);
            gr.setColor(Color.BLACK);
            gr.draw(new Rectangle2D.Double(xi,correctYi,dotW,dotW));
        }
    }
}

