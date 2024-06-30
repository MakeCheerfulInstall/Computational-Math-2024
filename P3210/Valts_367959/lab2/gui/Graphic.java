package lab2.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphic extends Application {
    private static int TYPE = 0, PROBLEM = 0;
    private static Object[][] res;

    public static void setData(int type, int problem, Object[][] result) {
        Graphic.TYPE = type;
        Graphic.PROBLEM = problem;
        res = result;
    }


    public void run() {
        launch();
    }

    @FXML
    public LineChart lineChart;

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

        if (Graphic.TYPE == 0) drawFirstType();
        if (Graphic.TYPE == 1) drawSecondType();

        stage.setScene(scene);
        stage.show();
    }

    private void drawFirstType() {
        for (int i = 0; i < Math.EQUATIONS[Graphic.PROBLEM].length; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(Math.GRAPHS[Graphic.PROBLEM][i]);

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);

            for (double point = -10; point <= 10; point += 0.01) {
                series.getData().add(new XYChart.Data<>(point, Math.EQUATIONS[Graphic.PROBLEM][i].apply(point)));
            }

        }
        for (int i = 0; i < 3; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(String.valueOf(i + 1));

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);
            try {
                series.getData().add(new XYChart.Data<>((Double) res[i][0], Math.EQUATIONS[Graphic.PROBLEM][0].apply((Double) res[i][0])));
            } catch (NullPointerException e) {
                System.out.printf("Метод под номером %d не дал ответ. Его точка не выведена на график", i + 1);
            }
        }
    }

    private void drawSecondType() {
        for (int i = 2; i < Math.SYSTEMS[Graphic.PROBLEM].length - 6; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(Math.GRAPH[Graphic.PROBLEM][i-2]);

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);

            for (double point = -10; point <= 10; point += 0.01) {
                var val = Math.SYSTEMS[Graphic.PROBLEM][i].apply(point);
                if (val < -10) continue;
                if (val > 10) continue;
                series.getData().add(new XYChart.Data<>(point, val));
            }
            series = new XYChart.Series<>();

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);
            series.setName("Решение");
            series.getData().add(new XYChart.Data<>((Double) res[0][0], (Double) res[1][0]));
            series.getData().add(new XYChart.Data<>((Double) res[0][0], (Double) res[1][0] + .1));
            series.getData().add(new XYChart.Data<>((Double) res[0][0]+ .1, (Double) res[1][0] + .1));
            series.getData().add(new XYChart.Data<>((Double) res[0][0] + .1, (Double) res[1][0]));
        }
    }
}
