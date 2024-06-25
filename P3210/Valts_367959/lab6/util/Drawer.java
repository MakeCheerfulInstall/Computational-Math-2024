package lab6.util;

import lab6.math.Functions;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class Drawer {
    public static void draw(double[][] result, String name) throws IllegalArgumentException{

        double[] x = new double[result.length];
        double[] y = new double[result.length];

        for (int i = 0; i < result.length; i++)
            x[i] = result[i][0];

        for (int i = 0; i < result.length; i++) {
            y[i] = result[i][1];
        }

//        for (int i = 0; i < x.length; i++) {
//            System.out.println(x[i] + " ; " + y[i] + " ; " + Functions.solution(x[i]));
//        }

        XYSeries series_method = new XYSeries("Method");

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < x.length; i++) {
//            System.out.println(x[i] + " ; " + y[i]);
            series_method.add(x[i], y[i]);
        }

        dataset.addSeries(series_method);

        XYSeries series_solution = new XYSeries("Точное решение");

        for (double i = x[0]; i < x[x.length-1]; i = i + 0.01) {
//            System.out.println(x[i] + " ; " + Functions.solution(x[i]));
            series_solution.add(i, Functions.solution(i));
        }

        dataset.addSeries(series_solution);

        JFreeChart chart = ChartFactory.createXYLineChart(name, "x",
                "y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        JFrame frame =
                new JFrame("MinimalStaticChart");

        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}
