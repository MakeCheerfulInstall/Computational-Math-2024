package lab2;

import Abdullin_367039.lab2.equations.Equation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class Graph extends JFrame {
    public Graph(Equation yourFunction, double[] line, String title) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = new XYSeries(title);
        for (double x = line[0] - 1; x < line[1] + 1; x += 0.01) {
            series.add(x, yourFunction.apply(BigDecimal.valueOf(x)));
        }
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "График функции",
                "X",
                "Y",
                dataset
        );

        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }
}
