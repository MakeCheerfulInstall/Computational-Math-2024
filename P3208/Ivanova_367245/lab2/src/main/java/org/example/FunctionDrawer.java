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

        // Define minX and maxX according to a and b
        double minX = a - 2;
        double maxX = b + 2;

        // Create dataset
        XYSeries series = new XYSeries("Function Plot");
        for (double xVal = minX; xVal <= maxX; xVal += 0.1) {
            double yVal = functions.getFunction(xVal, number);
            series.add(xVal, yVal);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset
        );

        // Customize the appearance of the plot
        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        // Set line color to black
        renderer.setSeriesPaint(0, Color.BLACK);

        // Add markers for specified points
        for (int i = 0; i < series.getItemCount(); i++) {
            double xValue = series.getX(i).doubleValue();
            double yValue = series.getY(i).doubleValue();
            if ((xValue == root && yValue == functionValue) ||
                    (xValue == a) ||
                    (xValue == b)) {
                renderer.setSeriesShapesVisible(i, true); // Make markers visible
                renderer.setSeriesShape(i, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10)); // Set marker shape to circle with custom size
                renderer.setSeriesPaint(i, Color.RED); // Set color of specified points to red
            }
        }

        // Add marker for specified point (x, y)
        XYSeries pointSeries = new XYSeries("Point");
        pointSeries.add(root, functionValue);
        dataset.addSeries(pointSeries);

        // Set renderer for the plot
        plot.setRenderer(renderer);

        // Add markers for specified values a and b on X axis
        Marker markerA = new ValueMarker(a);
        Marker markerB = new ValueMarker(b);
        markerA.setPaint(Color.RED);
        markerB.setPaint(Color.RED);
        plot.addDomainMarker(markerA);
        plot.addDomainMarker(markerB);

        // Customize the appearance of the point
        renderer.setSeriesShapesVisible(dataset.getSeriesIndex(pointSeries.getKey()), true);
        renderer.setSeriesShape(dataset.getSeriesIndex(pointSeries.getKey()), new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10)); // Larger size
        renderer.setSeriesPaint(dataset.getSeriesIndex(pointSeries.getKey()), Color.RED); // Set color of the point

        // Create Panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
