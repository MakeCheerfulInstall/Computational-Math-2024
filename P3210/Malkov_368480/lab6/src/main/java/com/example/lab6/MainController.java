package com.example.lab6;

import com.example.lab6.methods.*;
import com.example.lab6.util.CounterException;
import com.example.lab6.util.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.nio.channels.Pipe;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private TextField fileName;
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
    private TextField hSeparator;
    @FXML
    private TextField yZero;
    @FXML
    private TextField epsilon;
    @FXML
    private VBox graphContainer;

    private final Functions[] functions = {Functions.FIRST,Functions.SECOND};
    @Getter
    private final ArrayList<Method> methods = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(Functions func: functions){
            function.getItems().add(func.getDescription());
        }
        download.setOnAction(this::downloadData);
        calculate.setOnAction(this::solve);

        TrueValue trueValue = new TrueValue();
        UpgradeEulerMethod upgradeEulerMethod = new UpgradeEulerMethod();
        RungeKutteMethod rungeKutteMethod = new RungeKutteMethod();
        AdamsMethod adamsMethod = new AdamsMethod();

        methods.addAll(Arrays.asList(trueValue, upgradeEulerMethod, rungeKutteMethod, adamsMethod));
    }

    private void downloadData(ActionEvent event) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/main/resources/com/example/lab6/" + fileName.getText()));
            xBegin.setText(scanner.next());
            xEnd.setText(scanner.next());
            hSeparator.setText(scanner.next());
            yZero.setText(scanner.next());
            epsilon.setText(scanner.next());
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
            writer.close();
        } catch (FileNotFoundException e) {
            sendResult("Указано неверное имя файла");
        }
    }
    public void solve(ActionEvent event){
        try{
            String selectedFunction = function.getValue();
            double xSt = Validator.validateSingleField(xBegin.getText());
            double xF = Validator.validateSingleField(xEnd.getText());
            double h = Validator.validateSingleField(hSeparator.getText());
            double currentY = Validator.validateSingleField(yZero.getText());
            double eps = Validator.validateSingleField(epsilon.getText());
            StringBuilder sb = new StringBuilder();
            sb.append("Результаты методов\n");
            for(Method method: methods){
                if(method.isCalculable()){
                    sb.append(method.nameOfMethod()+ "\n");
                    ArrayList<Double> buff = method.calculate(xSt, xF, h, currentY,Functions.getByDesc(selectedFunction),  eps,false );
                    for (double value : buff) {
                        sb.append(String.format("%20.4f", value));
                    }
                    sb.append("\n");
                }
            }
            String result = sb.toString();
            sendResult(result);
            System.out.println(result);
            drawGraph(xSt, xF, h, currentY,Functions.getByDesc(selectedFunction),  eps, methods);
        } catch (NumberFormatException exception){
            sendResult("Проверьте корректность вводимых вами данных\nЭто должны быть дробные числа");
        } catch (NoSuchFieldException e) {
            sendResult("Проверьте корректность вводимых вами данных\nПоле не должно быть пустым");
        }

    }
    private void drawGraph(double x0,double x_last, double h, double y0, Functions f, double eps, ArrayList<Method> methods){
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        // Создание LineChart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);
        ArrayList<Double> result = null;
        ArrayList<Double> xValues = new ArrayList<>();
        for(double i = x0; i <= x_last; i+= h){
            xValues.add(i);
        }
        for (Method method: methods){
            if (method.isCalculable()){
                XYChart.Series series = new XYChart.Series();
                series.setName(method.nameOfMethod());
                result =  method.calculate(x0, x_last, h, y0 ,f,  eps,false );
                for (int i = 0; i < result.size(); i++) {
                    System.out.println( xValues.get(i)+ " " + result.get(i));
                    series.getData().add(new XYChart.Data(xValues.get(i), result.get(i)));
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


}