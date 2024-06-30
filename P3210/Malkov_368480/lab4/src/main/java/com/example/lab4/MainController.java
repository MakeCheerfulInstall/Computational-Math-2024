package com.example.lab4;

import com.example.lab4.methods.*;
import com.example.lab4.util.CounterException;
import com.example.lab4.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    public static Stage stage;
    @FXML
    public TextField xField;
    @FXML
    public TextField yField;
    @FXML
    public TextField fileName;
    @FXML
    public Button download;
    @FXML
    public Button start;
    @FXML
    public Label graphLabel;
    @FXML
    public Label result;
    @FXML
    public VBox graphContainer;
    @FXML
    public VBox resultLabel;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        start.setOnAction(this::submitData);
        download.setOnAction(this::getDataFromFile);
    }

    public void saveResult(ActionEvent event){
        try {
            PrintWriter writer = new PrintWriter("src/main/resources/com/example/lab4/Output");
            writer.println("asdasdad");
            writer.close();
        } catch (FileNotFoundException e) {
            sendResult("Указано неверное имя файла");
        }
    }
    public void getDataFromFile(ActionEvent event){
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/main/resources/com/example/lab4/" + fileName.getText()));
            this.xField.setText(scanner.nextLine());
            this.yField.setText(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            sendResult("Указано неверное имя файла");
        } catch (NoSuchElementException e){
            sendResult("В указанном файле отсутствует некоторые данные");
        }
    }
    public void submitData(ActionEvent event){
        ArrayList<Double> xValues;
        ArrayList<Double> yValues;
        try{
            xValues =  Validator.splitStringToDoubleArrayList(xField.getText());
            yValues =  Validator.splitStringToDoubleArrayList(yField.getText());
            if(xValues.size() != yValues.size()){
                throw new CounterException("Кол-во точек по x и y должно соответствовать");
            }
            int n = xValues.size();


            LineApproximation linApprox = new LineApproximation();
            linApprox.calculate(xValues,yValues,n);
            QuadApproximation quadApproximation = new QuadApproximation();
            quadApproximation.calculate(xValues,yValues,n);
            LogarithmApproximation logarithmApproximation = new LogarithmApproximation();
            logarithmApproximation.calculate(xValues,yValues,n);
            PowerApproximation powerApproximation = new PowerApproximation();
            powerApproximation.calculate(xValues,yValues,n);
            ExponentialApproximation exponentialApproximation = new ExponentialApproximation();
            exponentialApproximation.calculate(xValues,yValues,n);
            CubApproximation cubApproximation = new CubApproximation();
            cubApproximation.calculate(xValues,yValues,n);

            drawDots(xValues,yValues,linApprox,
                    quadApproximation,
                    logarithmApproximation,
                    powerApproximation,
                    exponentialApproximation,
                    cubApproximation);

            double[] determValues = new double[]{
                    linApprox.getDeterm(),
                    quadApproximation.getDeterm(),
                    logarithmApproximation.getDeterm(),
                    powerApproximation.getDeterm(),
                    exponentialApproximation.getDeterm(),
                    cubApproximation.getDeterm()
            };

            ArrayList<Method> buff1 = new ArrayList<>();
            buff1.add(linApprox);
            buff1.add(quadApproximation);
            buff1.add(logarithmApproximation);
            buff1.add(powerApproximation);
            buff1.add(exponentialApproximation);
            buff1.add(cubApproximation);

            double maxValue = determValues[0];

            for (int i = 1; i < determValues.length; i++) {
                if (determValues[i] > maxValue) {
                    maxValue = determValues[i];
                }
            }


            String result = linApprox.getAnswer() + "\n" +
                    quadApproximation.getAnswer() + "\n" +
                    logarithmApproximation.getAnswer() + "\n" +
                    powerApproximation.getAnswer() + "\n" +
                    exponentialApproximation.getAnswer() + "\n" +
                    cubApproximation.getAnswer();

            for(Method method: buff1){
                if(maxValue == method.getDeterm()){
                    result += "\n Лучшая аппроксимация это: " + method.getNameMethod();
                }
            }

            System.out.println(result);

            sendResult(result);
        } catch (NumberFormatException exception){
            sendResult("Проверьте корректность вводимых вами данных\nЭто должны быть дробные числа");
        } catch (CounterException e) {
            sendResult(e.getMessage());
        }
    }

    private void drawDots(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY,
                          LineApproximation lineApproximation, QuadApproximation quadApproximation,
                          LogarithmApproximation logarithmApproximation, PowerApproximation powerApproximation,
                          ExponentialApproximation exponentialApproximation, CubApproximation cubApproximation){

        ArrayList<Method> buff = new ArrayList<>();
        buff.add(lineApproximation);
        buff.add(quadApproximation);
        buff.add(logarithmApproximation);
        buff.add(powerApproximation);
        buff.add(exponentialApproximation);
        buff.add(cubApproximation);


        final double maxX = arrayOfX.stream().max(Double::compareTo).get();
        final double maxY = arrayOfY.stream().max(Double::compareTo).get();
        final double minX = arrayOfX.stream().min(Double::compareTo).get();
        final double minY = arrayOfY.stream().min(Double::compareTo).get();

        final NumberAxis xAxis = new NumberAxis(minX , maxX, (maxX - minX) / 10);
        final NumberAxis yAxis = new NumberAxis(minY, maxY, (maxY - minY) / 10);
        final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setStyle("");

        scatterChart.setTitle("Графики");

        for(Method function: buff){
            XYChart.Series<Number, Number> functionPoints = new XYChart.Series<>();
            functionPoints.setName(function.getNameMethod());
            for(double i = minX; i < maxX; i+=(maxX - minX)/1000){
                functionPoints.getData().add(new XYChart.Data<>(i, function.f(i)));
            }
            scatterChart.getData().addAll(functionPoints);
            functionPoints.getData().forEach(data -> {
                data.getNode().setStyle("-fx-background-radius: 10; -fx-background-insets:0; -fx-padding: 3 3;");
            });
        }

        XYChart.Series<Number, Number> dots = new XYChart.Series<>();
        dots.setName("Исходные данные");
        for(int i = 0; i < arrayOfX.size(); i++){
            dots.getData().add(new XYChart.Data<>(arrayOfX.get(i), arrayOfY.get(i)));
        }
        scatterChart.getData().addAll(dots);
        dots.getData().forEach(data -> {
            data.getNode().setStyle("-fx-background-radius: 10; -fx-background-insets:0; -fx-padding: 3 3; -fx-background-color: red; -fx-border-color: red;");
        });

        if(graphContainer.getChildren().size() < 2){
            graphContainer.getChildren().add(scatterChart);
        } else{
            graphContainer.getChildren().remove(graphContainer.getChildren().size()-1);
            graphContainer.getChildren().add(scatterChart);
        }
    }
    private void sendResult(String result){
        Label textLbl = new Label(result);
        ScrollPane scrollPane = new ScrollPane(textLbl);
        scrollPane.setPrefViewportHeight(280);
        scrollPane.setPrefViewportWidth(200);

        if(resultLabel.getChildren().size() < 1){
            resultLabel.getChildren().add(scrollPane);
        } else{
            resultLabel.getChildren().remove(0);
            resultLabel.getChildren().add(scrollPane);
        }
    }
}