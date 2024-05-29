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

        double minX = a - 2;
        double maxX = b + 2;

        double minY = a - 10;
        double maxY = b + 2;

        XYSeries series1 = new XYSeries("System 1");
        for (double x = minX; x <= maxX; x += 0.0005) {
            double y = functions.getSystem1(x, number);
            series1.add(x, y);
            series1.add(x, -y);
        }

        XYSeries series2 = new XYSeries("System 2");
        for (double x = minX; x <= maxX; x += 0.0005) {
            double y = functions.getSystem2(x, number);
            series2.add(x, y);
        }


        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries pointSeries = new XYSeries("Point");
        pointSeries.add(solution[0], solution[1]);
        dataset.addSeries(pointSeries);



        dataset.addSeries(series1);
        dataset.addSeries(series2);


        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();



        renderer.setSeriesPaint(1, Color.BLACK);

        renderer.setSeriesPaint(2, Color.BLUE);



        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(2, true);
        renderer.setSeriesLinesVisible(2, false);

        plot.setRenderer(renderer);


        renderer.setSeriesShapesVisible(dataset.getSeriesIndex(pointSeries.getKey()), true);
        renderer.setSeriesShape(dataset.getSeriesIndex(pointSeries.getKey()), new java.awt.geom.Ellipse2D.Double(-7.5, -7.5, 15, 15));



        Marker markerRoot1 = new ValueMarker(solution[0]);
        markerRoot1.setPaint(Color.RED);
        plot.addDomainMarker(markerRoot1);


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
    
}

