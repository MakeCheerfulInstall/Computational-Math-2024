package lab5;

import Abdullin_367039.lab5.Interpolation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class InterpolationPlot extends JFrame {

  public InterpolationPlot(String title, double[] x, double[] y) {
    super(title);

    double[] plotX = new double[100];
    double[] plotY1 = new double[100];
    double[] plotY2 = new double[100];

    for (int i = 0; i < 100; i++) {
      double val = x[0] + i * (x[x.length - 1] - x[0]) / 99.0;
      plotX[i] = val;
      plotY1[i] = Abdullin_367039.lab5.Interpolation.lagrange(x, y, val);
      plotY2[i] = Interpolation.newtonFiniteDifferences(x, y, val);
    }


    XYSeriesCollection dataset = new XYSeriesCollection();

    XYSeries series = new XYSeries("Заданные значения");
    for (int i = 0; i < x.length; i++) {
      series.add(x[i], y[i]);
    }
    dataset.addSeries(series);

    series = new XYSeries("Многочлен Лагранжа");
    for (int i = 0; i < plotX.length; i++) {
      series.add(plotX[i], plotY1[i]);
    }
    dataset.addSeries(series);

    series = new XYSeries("Многочлен Ньютона с конечными разностями");
    for (int i = 0; i < plotX.length; i++) {
      series.add(plotX[i], plotY2[i]);
    }
    dataset.addSeries(series);


    JFreeChart chart =
        ChartFactory.createXYLineChart(
            "Лабораторная работа 5",
            "X",
            "Y",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false);

    XYPlot plot = chart.getXYPlot();
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    plot.setRenderer(renderer);
    renderer.setSeriesLinesVisible(0, false);
    renderer.setSeriesShapesVisible(0, true);

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 600));
    setContentPane(chartPanel);
  }

}
