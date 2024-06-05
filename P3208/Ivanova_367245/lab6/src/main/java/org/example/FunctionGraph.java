package org.example;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class FunctionGraph extends JPanel {

    public FunctionGraph(double[] xValues, double[] yValues, double[] pointX, double[] pointY, int n, String title) {
        super(new BorderLayout());

        XYSeries series = new XYSeries("y' = f(x, y)");

        for (int i = 0; i < n; i++) {
            series.add(xValues[i], yValues[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        plot.setRenderer(renderer);

        XYSeries points = new XYSeries("Points");
        for (int i = 0; i < n; i++) {
            points.add(pointX[i], pointY[i]);
        }

        XYSeriesCollection pointDataset = new XYSeriesCollection();
        pointDataset.addSeries(points);

        XYLineAndShapeRenderer pointRenderer = new XYLineAndShapeRenderer(false, true);
        pointRenderer.setSeriesPaint(0, Color.RED);

        Shape pointShape = new Ellipse2D.Double(-5, -5, 10, 10);
        pointRenderer.setSeriesShape(0, pointShape);

        plot.setDataset(1, pointDataset);
        plot.setRenderer(1, pointRenderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        add(chartPanel, BorderLayout.CENTER);
    }
}
