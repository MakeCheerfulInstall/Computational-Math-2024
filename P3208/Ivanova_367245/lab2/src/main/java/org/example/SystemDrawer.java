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

public class SystemDrawer extends JFrame {
    Functions functions = new Functions();

    public SystemDrawer(String title, int number, double a, double b, double[] solution) {
        super(title);

        // Define minX and maxX according to a and b
        double minX = a - 2;
        double maxX = b + 2;

        double minY = a - 10;
        double maxY = b + 2;

        // Create dataset for system1
        XYSeries series1 = new XYSeries("System 1");
        for (double x = minX; x <= maxX; x += 0.0005) {
            double y = functions.getSystem1(x, number);// Assuming system1 is represented by function number 1
            series1.add(x, y);
            series1.add(x, -y);
        }

        // Create dataset for system2
        XYSeries series2 = new XYSeries("System 2");
        for (double x = minX; x <= maxX; x += 0.0005) {
            double y = functions.getSystem2(x, number); // Assuming system2 is represented by function number 2
            series2.add(x, y);
        }


        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries pointSeries = new XYSeries("Point");
        pointSeries.add(solution[0], solution[1]);
        dataset.addSeries(pointSeries);



        dataset.addSeries(series1);
        dataset.addSeries(series2);

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


        // Set line color to black for system1
        renderer.setSeriesPaint(1, Color.BLACK);
        // Set line color to blue for system2
        renderer.setSeriesPaint(2, Color.BLUE);

        // Add markers for specified points of system1
        // Add markers for specified points of system2


// Убрать линии между точками для системы 2
        renderer.setSeriesShapesVisible(1, true); // Оставить только маркеры точек
        renderer.setSeriesLinesVisible(1, false); // Убрать линии между точками
        renderer.setSeriesShapesVisible(2, true); // Оставить только маркеры точек
        renderer.setSeriesLinesVisible(2, false); // Убрать линии между точками
        // Set renderer for the plot
        plot.setRenderer(renderer);

        // Customize the appearance of the point
        renderer.setSeriesShapesVisible(dataset.getSeriesIndex(pointSeries.getKey()), true);
        renderer.setSeriesShape(dataset.getSeriesIndex(pointSeries.getKey()), new java.awt.geom.Ellipse2D.Double(-7.5, -7.5, 15, 15)); // Larger size
        renderer.setSeriesPaint(dataset.getSeriesIndex(pointSeries.getKey()), Color.RED); // Set color of the point




        // Add markers for specified values of root1 and root2 on X axis
        Marker markerRoot1 = new ValueMarker(solution[0]);
        markerRoot1.setPaint(Color.RED);
        plot.addDomainMarker(markerRoot1);

        // Create Panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    public static void main(String[] args) {

    }
}

