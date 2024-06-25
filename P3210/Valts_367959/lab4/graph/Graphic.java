package lab4.graph;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import lab4.entity.Dot;
import lab4.entity.DotCollection;
import lab4.work.Approximation;

public class Graphic extends Application {

    public void run() {
        launch();
    }

    @FXML
    public static LineChart lineChart;

    @Override
    public void start(Stage stage) throws Exception {
        var X = new NumberAxis(-10, 10, 2);
        X.setLabel("x");
        var Y = new NumberAxis(-10, 10, 2);
        Y.setLabel("y");
        lineChart = new LineChart (
                Y,
                X
        );

        Scene scene = new Scene(lineChart, 900, 600);

        lineChart.setCreateSymbols(false);

        lineChart.getData().add(Approximation.s1);
        lineChart.getData().add(Approximation.s2);
        lineChart.getData().add(Approximation.s3);
        lineChart.getData().add(Approximation.s4);
        lineChart.getData().add(Approximation.s5);
        lineChart.getData().add(Approximation.s6);

        XYChart.Series<Double, Double> series;
        int n = 0;

        for (Dot d: DotCollection.getDots()) {
            series = new XYChart.Series<>();
            series.setName("Точка " + ++n);
            series.getData().add(new XYChart.Data<>(d.getX(), d.getY()));
            lineChart.getData().add(series);
        }

        stage.setScene(scene);
        stage.show();
    }

    public static XYChart.Series<Double, Double> addGraph1(double[] finalCoefficients) {
        //linear
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double a = finalCoefficients[0];
        double b = finalCoefficients[1];

        series.setName("P1(x) = (" + a + ")x + (" + b + ")");

        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, Approximation.getValueLinearApprox(a, b, point)));
        }
        return series;
    }
    public static XYChart.Series<Double, Double> addGraph2(double[] finalCoefficients) {
        //quadratic
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double a = finalCoefficients[0];
        double b = finalCoefficients[1];
        double c = finalCoefficients[2];

        series.setName("P2(x) = (" + a + ")x^2 + (" + b + ")x + (" + c + ")");

        for (double point = DotCollection.minX(); point <= DotCollection.maxX(); point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, Approximation.getValueQuadraticApprox(a, b, c, point)));
        }

        return series;
    }

    public static XYChart.Series<Double, Double> addGraph3(double[] finalCoefficients) {
        //cubic
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double a = finalCoefficients[0];
        double b = finalCoefficients[1];
        double c = finalCoefficients[2];
        double d = finalCoefficients[3];


        series.setName("P3(x) = (" + a + ")x^3 + (" + b + ")x^2 + (" + c + ")x + (" + d + ")");

        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, Approximation.getValueCubicApprox(a, b, c, d, point)));
        }

        return series;
    }

    public static XYChart.Series<Double, Double> addGraph4(double[] finalCoefficients) {
        //power
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double a = finalCoefficients[0];
        double b = finalCoefficients[1];


        series.setName("P4(x) = (" + a + ")*x^(" + b + ")");

        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, Approximation.getValuePowerApprox(a, b, point)));
        }

        return series;
    }

    public static XYChart.Series<Double, Double> addGraph5(double[] finalCoefficients) {
        //exp
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double a = finalCoefficients[0];
        double b = finalCoefficients[1];


        series.setName("P5(x) = (" + a + ")*e^(" + b + "x)");

        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, Approximation.getValueExponentialApprox(a, b, point)));
        }

        return series;
    }

    public static XYChart.Series<Double, Double> addGraph6(double[] finalCoefficients) {
        //log
        XYChart.Series<Double, Double> series = new XYChart.Series<>();

        double a = finalCoefficients[0];
        double b = finalCoefficients[1];


        series.setName("P6(x) = (" + a + ")*ln(x) + (" + b + ")");

        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, Approximation.getValueLogApprox(a, b, point)));
        }

        return series;
    }
}
