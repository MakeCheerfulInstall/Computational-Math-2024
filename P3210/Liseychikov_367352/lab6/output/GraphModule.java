package lab6.output;

import lab6.solver.FuncXY;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;

public class GraphModule {
    public static void draw(double[][] result, String name, FuncXY funcXY) throws IllegalArgumentException{

        double[] x = new double[result.length];
        double[] y = new double[result.length];

        for (int i = 0; i < result.length; i++)
            x[i] = result[i][0];

        for (int i = 0; i < result.length; i++)
            y[i] = result[i][1];

        XYSeries series_method = new XYSeries("Method");
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < x.length; i++) {
            series_method.add(x[i], y[i]);
        }
        dataset.addSeries(series_method);

        XYSeries series_solution = new XYSeries("Точное решение");
        for (double i = x[0]; i < x[x.length-1]; i = i + 0.01) {
            series_solution.add(i, funcXY.solve(i));
        }
        dataset.addSeries(series_solution);

        JFreeChart chart = ChartFactory.createXYLineChart(name, "x",
                "y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        JFrame frame = new JFrame("MinimalStaticChart");

        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}