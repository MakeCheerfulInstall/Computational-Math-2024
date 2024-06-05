package org.example;
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

    public FunctionDrawer(String title, int amount, double[] x, double[] y, double[] result) {
        super(title);

        XYSeries series = new XYSeries("Original Function");
        for (int i = 0; i < amount; i++) {
            series.add(x[i], y[i]);
        }

        XYSeries approxSeries = new XYSeries("Approximating Function");
        for (int i = 0; i < amount; i++) {
            approxSeries.add(x[i], result[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(approxSeries);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset
        );


        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, false);



        for (int i = 0; i < series.getItemCount(); i++) {
            double xValue = series.getX(i).doubleValue();
            double yValue = series.getY(i).doubleValue();
            renderer.setSeriesShapesVisible(0, true);
            renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
            renderer.setSeriesPaint(0, Color.BLUE);
        }

        for (int i = 0; i < approxSeries.getItemCount(); i++) {
            double xValue = approxSeries.getX(i).doubleValue();
            double yValue = approxSeries.getY(i).doubleValue();
            renderer.setSeriesShapesVisible(1, true);
            renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));
            renderer.setSeriesPaint(1, Color.RED);
        }


        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

}

