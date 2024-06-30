package lab5.util;

import lab5.methods.Lagrange;
import lab5.methods.Newton;
import lab5.methods.Polynomial;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Drawer {
    public static void drawLagrange() {
        Lagrange lagrange = new Lagrange();
        ArrayList<Double[]> values = Polynomial.getValues();

        XYSeries seriesLagrange = new XYSeries("Lagrange");

        XYSeriesCollection dataset = new XYSeriesCollection();

        JFreeChart chart = ChartFactory.createXYLineChart("Лагранж", "x",
                "y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        for (Double[] value : values) {
            XYShapeAnnotation shape = new XYShapeAnnotation(new Ellipse2D.Double(value[0], value[1], 0.01 / 4, 0.1), new BasicStroke(4.5f), Color.green);
            chart.getXYPlot().addAnnotation(shape);
        }

        for (double i = values.get(0)[0]; i <= values.get(values.size() - 1)[0]; i += 0.01) {
            Polynomial.setX(i);
            seriesLagrange.add(i, lagrange.execute());
        }

        dataset.addSeries(seriesLagrange);

        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }

    public static void drawNewton() {
        Newton newton = new Newton();
        ArrayList<Double[]> values = Polynomial.getValues();

        XYSeries seriesNewton = new XYSeries("Newton");

        XYSeriesCollection dataset = new XYSeriesCollection();

        for (double i = values.get(0)[0]; i <= values.get(values.size() - 1)[0]; i += 0.01) {
            Polynomial.setX(i);
            seriesNewton.add(i, newton.execute());
        }




        dataset.addSeries(seriesNewton);

        JFreeChart chart = ChartFactory.createXYLineChart("Ньютон", "x",
                "y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        for (Double[] value : values) {
            XYShapeAnnotation shape = new XYShapeAnnotation(new Ellipse2D.Double(value[0], value[1], 0.01 / 4, 0.1), new BasicStroke(4.5f), Color.green);
            chart.getXYPlot().addAnnotation(shape);
        }

        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(1000, 500);
        frame.setVisible(true);
    }
}
