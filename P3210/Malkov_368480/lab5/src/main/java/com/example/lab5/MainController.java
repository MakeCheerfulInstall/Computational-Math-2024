package com.example.lab5;

import com.example.lab5.methods.*;
import com.example.lab5.util.CounterException;
import com.example.lab5.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private TextField fileName;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private Button calculate;
    @FXML
    private Button download;
    @FXML
    private ChoiceBox<String> function;
    @FXML
    private TextField xBegin;
    @FXML
    private TextField xEnd;
    @FXML
    private TextField pointsCount;
    @FXML
    private Button calculateSecondForm;
    @FXML
    private VBox graphContainer;
    @FXML
    private TextField searchX0;
    @FXML
    private TextField searchX1;

    private final Functions[] functions = {Functions.SINX,Functions.COSX};
    @Getter
    private final ArrayList<Method> methods = new ArrayList<>();
    @Setter
    @Getter
    private double[][] currentFiniteDifference;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Functions func: functions){
            function.getItems().add(func.getDescription());
        }
        calculateSecondForm.setOnAction(this::calculateSecondForm);
        calculate.setOnAction(this::calculateFirstForm);
        download.setOnAction(this::downloadData);

        LagrangePolynomial lagrangePolynomial = new LagrangePolynomial();
        NewtonPolynomial newtonPolynomial = new NewtonPolynomial();
        GaussPolynomial gaussPolynomial = new GaussPolynomial();

        StirlingPolynomial stirlingPolynomial = new StirlingPolynomial();
        BesselPolynomial besselPolynomial = new BesselPolynomial();
        methods.addAll(Arrays.asList(lagrangePolynomial, newtonPolynomial, gaussPolynomial, besselPolynomial, stirlingPolynomial));
    }

    private void downloadData(ActionEvent event) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/main/resources/com/example/lab5/" + fileName.getText()));
            this.xField.setText(scanner.nextLine());
            this.yField.setText(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            sendResult("Указано неверное имя файла");
        } catch (NoSuchElementException e){
            sendResult("В указанном файле отсутствует некоторые данные");
        }
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

    private void calculateFirstForm(ActionEvent event) {
        ArrayList<Double> xValues;
        ArrayList<Double> yValues;
        try{
            xValues =  Validator.splitStringToDoubleArrayList(xField.getText());
            yValues =  Validator.splitStringToDoubleArrayList(yField.getText());
            if(xValues.size() != yValues.size()){
                throw new CounterException("Кол-во точек по x и y должно соответствовать");
            }
            double currentX = Double.parseDouble(searchX0.getText().replace(",","."));
            calculateData(xValues,yValues,currentX);
        } catch (NumberFormatException exception){
            sendResult("Проверьте корректность вводимых вами данных\nЭто должны быть дробные числа");
        } catch (CounterException e) {
            sendResult(e.getMessage());
        }
    }

    private void calculateSecondForm(ActionEvent event) {
        ArrayList<Double> xValues = new ArrayList<>();
        ArrayList<Double> yValues = new ArrayList<>();
        try{
            double xSt = Double.parseDouble(xBegin.getText().replace(",","."));
            double xF = Double.parseDouble(xEnd.getText().replace(",","."));
            int count = Integer.parseInt(pointsCount.getText().replace(",","."));
            double currentX = Double.parseDouble(searchX1.getText().replace(",","."));
            Functions currentFunc = Functions.getByDesc(function.getValue());
            assert currentFunc != null;
            for(double i = xSt; i< xF; i+=(xF - xSt)/count){
                xValues.add(i);
                yValues.add(currentFunc.getFunction().apply(i));
            }
            calculateData(xValues,yValues, currentX);
        } catch (NumberFormatException exception){
            sendResult("Проверьте корректность вводимых вами данных\nЭто должны быть дробные числа");
        }
    }
    private void calculateData(ArrayList<Double> xValues, ArrayList<Double> yValues, double currentX){
        currentFiniteDifference = calculateFiniteDifference(xValues, yValues);
        StringBuilder sb = new StringBuilder();
        sb.append("Таблица конечных разностей\n");
        for (double[] row : currentFiniteDifference) {
            for (double value : row) {
                sb.append(String.format("%10.4f", value));
            }
            sb.append("\n");
        }
        for (Method method: methods){
           sb.append(method.nameOfMethod());
           sb.append("\t");
           if(method.isCalculable(xValues,currentX)) sb.append(method.calculate(xValues, yValues, currentX, currentFiniteDifference));
           else sb.append("Метод неприменим");
           sb.append("\n");
        }
        String result = sb.toString();
        sendResult(result);
        System.out.println(result);
        drawGraph(xValues, yValues, methods);
    }

    private void drawGraph(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, ArrayList<Method> methods){
        final double maxX = arrayOfX.stream().max(Double::compareTo).get();
        final double minX = arrayOfX.stream().min(Double::compareTo).get();
        final double separate = (arrayOfX.get(1) - arrayOfX.get(0))/4;

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        // Создание LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        for (Method method: methods){
            if (method.getIsDrawing()){
                XYChart.Series series = new XYChart.Series();
                series.setName(method.nameOfMethod());
                for (double currentX = minX; currentX<maxX; currentX += separate) {
                    series.getData().add(new XYChart.Data(currentX, method.calculate(arrayOfX, arrayOfY, currentX, currentFiniteDifference)));
                }
                lineChart.getData().add(series);
            }
        }
        if(graphContainer.getChildren().size() <= 1){
            graphContainer.getChildren().add(1,lineChart);
        } else{
            graphContainer.getChildren().remove(1);
            graphContainer.getChildren().add(1,lineChart);
        }
    }

    private void sendResult(String result){
        Label textLbl = new Label(result);
        ScrollPane scrollPane = new ScrollPane(textLbl);
        scrollPane.setPrefViewportHeight(280);
        scrollPane.setPrefViewportWidth(200);

        if(graphContainer.getChildren().size() < 1){
            graphContainer.getChildren().add(0,scrollPane);
        } else{
            graphContainer.getChildren().remove(0);
            graphContainer.getChildren().add(0,scrollPane);
        }
    }
    private double[][] calculateFiniteDifference(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY){
        int n = arrayOfX.size();
        double[][] diffTable = new double[n][n];

        // Заполнение первой строки таблицы y-значениями
        for (int i = 0; i < n; i++) {
            diffTable[i][0] = arrayOfY.get(i);
        }

        // Построение таблицы конечных разностей
        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                diffTable[i][j] = diffTable[i + 1][j - 1] - diffTable[i][j - 1];
            }
        }
        return diffTable;
    }

}