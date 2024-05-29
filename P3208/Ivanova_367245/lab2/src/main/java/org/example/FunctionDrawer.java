package org.example;
import org.example.Functions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class FunctionDrawer extends JFrame {
    Functions functions = new Functions();

    public FunctionDrawer(String title, int number, double a, double b, double root, double functionValue) {
        super(title);

        double minX = a - 2;
        double maxX = b + 2;

        XYSeries series = new XYSeries("Function Plot");
        for (double xVal = minX; xVal <= maxX; xVal += 0.1) {
            double yVal = functions.getFunction(xVal, number);
            series.add(xVal, yVal);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset
        );


        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.BLACK);

        for (int i = 0; i < series.getItemCount(); i++) {
            double xValue = series.getX(i).doubleValue();
            double yValue = series.getY(i).doubleValue();
            if ((xValue == root && yValue == functionValue) ||
                    (xValue == a) ||
                    (xValue == b)) {
                renderer.setSeriesShapesVisible(i, true);
                renderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
                renderer.setSeriesPaint(i, Color.RED);
            }
        }


        XYSeries pointSeries = new XYSeries("Point");
        pointSeries.add(root, functionValue);
        dataset.addSeries(pointSeries);

        plot.setRenderer(renderer);


        Marker markerA = new ValueMarker(a);
        Marker markerB = new ValueMarker(b);
        markerA.setPaint(Color.RED);
        markerB.setPaint(Color.RED);
        plot.addDomainMarker(markerA);
        plot.addDomainMarker(markerB);

        renderer.setSeriesShapesVisible(dataset.getSeriesIndex(pointSeries.getKey()), true);
        renderer.setSeriesShape(dataset.getSeriesIndex(pointSeries.getKey()), new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
        renderer.setSeriesPaint(dataset.getSeriesIndex(pointSeries.getKey()), Color.RED);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
