package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class FunctionDrawer extends JFrame {

    public FunctionDrawer(String title, int amount, double[] x1, double[] y1, String mainGraphLabel, double specialPointX, double specialPointY) {
        super(title);

        XYSeries series1 = new XYSeries(mainGraphLabel);
        for (int i = 0; i < amount; i++) {
            series1.add(x1[i], y1[i]);
        }

        XYSeries specialSeries = new XYSeries("Значение функции для заданного документа");
        specialSeries.add(specialPointX, specialPointY);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(specialSeries);


        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "X",
                "Y",
                dataset
        );


        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();


        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6));

        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10));

        renderer.setSeriesItemLabelsVisible(1, true);
        renderer.setSeriesItemLabelPaint(1, Color.RED);

        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);


        chart.getLegend().setVisible(true);
    }


}
