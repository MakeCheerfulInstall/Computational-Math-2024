package com.example.lab2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainController implements Initializable {
    @FXML
    public ChoiceBox<Integer> expressionID;
    @FXML
    public TextField maxIteration;
    @FXML
    public Button downloadBtn;
    @FXML
    public Button submitExpressionBtn;
    @FXML
    public TextField epsilon;
    @FXML
    public TextField leftPos;
    @FXML
    public TextField rightPos;
    @FXML
    public TextField firstY;
    @FXML
    public TextField firstX;
    @FXML
    public TextField systemEpsilon;
    @FXML
    public Button systemSubmit;
    @FXML
    public TextField systemMaxIteration;
    @FXML
    public Button systemDownload;
    @FXML
    public ChoiceBox<Integer> systemNumber;
    @FXML
    public Button systemSaveBtn;
    @FXML
    public Button expressionSaveBtn;
    @FXML
    public VBox expressionContainer;
    @FXML
    public VBox systemContainer;
    public Label systemLabel;
    public Label expressionLabel;
    public Label expressionResult;
    public Label systemResult;
    @FXML
    private ChoiceBox<String> methodSelector;
    private final String[] methods = {"Метод половинного деления","Метод Ньютона", "Метод простой итерации"};

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        methodSelector.getItems().addAll(methods);
        methodSelector.setOnAction(this::getMethod);
        submitExpressionBtn.setOnAction(this::submitDataExpression);
        expressionID.getItems().addAll(0,1);
        systemNumber.getItems().addAll(0,1);
        expressionID.setOnAction(this::replaceExpression);
        systemNumber.setOnAction(this::replaceSystem);
        systemSubmit.setOnAction(this::submitDataSystem);
        systemDownload.setOnAction(this::getSystemDataFromFile);
        downloadBtn.setOnAction(this::getExpressionDataFromFile);
        expressionSaveBtn.setOnAction(this::saveExpressionData);
        systemSaveBtn.setOnAction(this::saveSystemData);
    }
    public void replaceExpression(ActionEvent event){
        expressionLabel.setText("График Функции: " +
                SingleExpression.getListOfFunction().get(expressionID.getValue()));
        drawExpressionGraph(expressionContainer,expressionID.getValue(), -20,20);
    }
    public void replaceSystem(ActionEvent event){
        systemLabel.setText("График Функции: \n" +
               SystemExpression.getListOfFunction().get(systemNumber.getValue() * 2) + "\n" +
                 SystemExpression.getListOfFunction().get(systemNumber.getValue() * 2 + 1));
        drawSystemGraph(systemContainer, systemNumber.getValue());
    }
    public void saveSystemData(ActionEvent event){
        try {
            PrintWriter writer = new PrintWriter("src/main/resources/com/example/lab2/Output");
            writer.println(systemResult.getText());
            writer.close();
        } catch (FileNotFoundException e) {
            expressionResult.setText(expressionResult.getText() + "\nУказано неверное имя файла");
        }
    }
    public void saveExpressionData(ActionEvent event){
        try {
            PrintWriter writer = new PrintWriter("src/main/resources/com/example/lab2/Output");
            writer.println(expressionResult.getText());
            writer.close();
        } catch (FileNotFoundException e) {
            expressionResult.setText(expressionResult.getText()+"\nУказано неверное имя файла");
        }
    }
    public void getExpressionDataFromFile(ActionEvent event){
        Scanner scanner;
        try {
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            scanner = new Scanner(file);
            leftPos.setText(scanner.next());
            rightPos.setText(scanner.next());
            epsilon.setText(scanner.next());
            maxIteration.setText(scanner.next());
            scanner.close();
        } catch (FileNotFoundException e) {
            expressionResult.setText(expressionResult.getText().substring(0, expressionResult.getText().indexOf(":")).trim()+": Указан неверное имя файла");
        } catch (NoSuchElementException e){
            expressionResult.setText(expressionResult.getText().substring(0, expressionResult.getText().indexOf(":")).trim()+": В указанном файле отсутствует некоторые данные");
        }
    }
    public void getSystemDataFromFile(ActionEvent event){
        Scanner scanner;
        try {
            FileChooser fileChooser = new FileChooser();
            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            scanner = new Scanner(file);
            firstX.setText(scanner.next());
            firstY.setText(scanner.next());
            systemEpsilon.setText(scanner.next());
            systemMaxIteration.setText(scanner.next());
            scanner.close();
        } catch (FileNotFoundException e) {
            systemResult.setText(systemResult.getText().substring(0, systemResult.getText().indexOf(":")).trim()+": Указан неверное имя файла");
        }
    }
    public void getMethod(ActionEvent event){
        System.out.println(methodSelector.getValue());
    }
    public void submitDataSystem(ActionEvent event){
        System.out.println("Метод простой итерации");
        try {
            drawSystemGraph(systemContainer,systemNumber.getValue());
            SystemExpression.simpleIteration(SystemExpression.getListOfF().get(systemNumber.getValue() * 2),
                    SystemExpression.getListOfPhi().get(systemNumber.getValue() * 2),
                    SystemExpression.getListOfF().get(systemNumber.getValue() * 2 + 1),
                    SystemExpression.getListOfPhi().get(systemNumber.getValue() * 2 + 1),
                    Double.parseDouble(firstX.getText()),Double.parseDouble(firstY.getText()),
                    SystemExpression.checkConvergence(SystemExpression.getListOfPhiDFX().get(systemNumber.getValue() * 2),
                            SystemExpression.getListOfPhiDFY().get(systemNumber.getValue() * 2),
                            SystemExpression.getListOfPhiDFX().get(systemNumber.getValue() * 2 + 1),
                            SystemExpression.getListOfPhiDFY().get(systemNumber.getValue() * 2 + 1),
                            Double.parseDouble(firstX.getText()),Double.parseDouble(firstY.getText())),
                    Double.parseDouble(systemEpsilon.getText()),Integer.parseInt(systemMaxIteration.getText()), systemResult);
            System.out.println(systemNumber.getValue());
            System.out.println(Double.parseDouble(firstX.getText()) + " ; " + Double.parseDouble(firstY.getText()));
            System.out.println("e = " + systemEpsilon.getText());
            System.out.println("n = " + systemMaxIteration.getText());
        }catch (NumberFormatException e){
            systemResult.setText(systemResult.getText().substring(0, systemResult.getText().indexOf(":")).trim()+": Стоит писать числа а не посторонние символы");
        }catch (NullPointerException e){
            systemResult.setText(systemResult.getText().substring(0, systemResult.getText().indexOf(":")).trim()+": Стоит выбрать если дают выбор");
        }
    }
    public void submitDataExpression(ActionEvent event){
        try {
            drawExpressionGraph(expressionContainer,expressionID.getValue(), Double.parseDouble(leftPos.getText()),Double.parseDouble( rightPos.getText()));
            if(SingleExpression.hasOneRoot(SingleExpression.getListOfF().get(expressionID.getValue()),
                    Double.parseDouble(leftPos.getText()),Double.parseDouble( rightPos.getText()))){
                switch (methodSelector.getItems().indexOf(methodSelector.getValue())){
                    case 0:
                        System.out.println("Метод половинного деления");
                        SingleExpression.halfMeth(SingleExpression.getListOfF().get(expressionID.getValue()),
                                SingleExpression.getListOfDF().get(expressionID.getValue()),
                                SingleExpression.getListOfSDF().get(expressionID.getValue()),
                                Double.parseDouble(leftPos.getText()),Double.parseDouble( rightPos.getText()),
                                Double.parseDouble(epsilon.getText()), expressionResult);
                        System.out.println(expressionID.getValue());
                        System.out.println(Double.parseDouble(leftPos.getText()) + " ; " + Double.parseDouble( rightPos.getText()));
                        System.out.println("e = " + epsilon.getText());
                        break;
                    case 1:
                        System.out.println("Метод Ньютона");
                        SingleExpression.newtonMethod(SingleExpression.getListOfF().get(expressionID.getValue()),
                                SingleExpression.getListOfDF().get(expressionID.getValue()),
                                SingleExpression.getListOfSDF().get(expressionID.getValue()),
                                Double.parseDouble(leftPos.getText()),Double.parseDouble( rightPos.getText()),
                                Double.parseDouble(epsilon.getText()), expressionResult );
                        System.out.println(expressionID.getValue());
                        System.out.println(Double.parseDouble(leftPos.getText()) + " ; " + Double.parseDouble( rightPos.getText()));
                        System.out.println("e = " + epsilon.getText());
                        break;
                    case 2:
                        System.out.println("Метод простой итерации");
                        SingleExpression.simpleIterations(SingleExpression.getListOfF().get(expressionID.getValue()),
                                SingleExpression.getListOfDF().get(expressionID.getValue()),
                                SingleExpression.getListOfSDF().get(expressionID.getValue()),
                                Double.parseDouble(leftPos.getText()),Double.parseDouble( rightPos.getText()),
                                Double.parseDouble(epsilon.getText()),Integer.parseInt( maxIteration.getText()), expressionResult);
                        System.out.println(expressionID.getValue());
                        System.out.println(Double.parseDouble(leftPos.getText()) + " ; " + Double.parseDouble( rightPos.getText()));
                        System.out.println("e = " + epsilon.getText());
                        System.out.println("n = " + maxIteration.getText());

                        break;
                    default:
                        expressionResult.setText(expressionResult.getText().substring(0, expressionResult.getText().indexOf(":")).trim()+":  Лапками вверх(");
                }
            }else{
                expressionResult.setText(expressionResult.getText().substring(0, expressionResult.getText().indexOf(":")).trim()+": На заданном промежутке не строго один корень");
            }
        }catch (NumberFormatException e){
            expressionResult.setText(expressionResult.getText().substring(0, expressionResult.getText().indexOf(":")).trim()+": Стоит писать числа а не посторонние символы");
        } catch (NullPointerException e){
            expressionResult.setText(expressionResult.getText().substring(0, expressionResult.getText().indexOf(":")).trim()+": Стоит выбрать если дают выбор");
        }

    }
    private void drawExpressionGraph(VBox container, int graphIndex, double leftPos, double rightPos){
        // Создаем оси координат
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("X");
        yAxis.setLabel("Y");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);

        XYChart.Series series = new XYChart.Series();
        series.setName(SingleExpression.getListOfFunction().get(expressionID.getValue()));
        if (rightPos - leftPos == 0){
            for (double i = leftPos - 5; i <= rightPos + 5; i+=0.01) {
                if (Math.abs(SingleExpression.getListOfF().get(graphIndex).apply(i)) < Math.abs(rightPos - leftPos) + 10){
                    series.getData().add(new XYChart.Data(i, SingleExpression.getListOfF().get(graphIndex).apply(i)));
                }
            }
        }else{
            for (double i = leftPos - 5; i <= rightPos + 5; i+=(rightPos - leftPos)/100) {
                if (Math.abs(SingleExpression.getListOfF().get(graphIndex).apply(i)) < Math.abs(rightPos - leftPos) + 10){
                    series.getData().add(new XYChart.Data(i, SingleExpression.getListOfF().get(graphIndex).apply(i)));
                }
            }
        }
        if(container.getChildren().size() < 2){
            lineChart.getData().add(series);
            container.getChildren().add(lineChart);
        } else{
            container.getChildren().remove(container.getChildren().size()-1);
            lineChart.getData().add(series);
            container.getChildren().add(lineChart);
        }
    }
    private void drawSystemGraph(VBox container, int graphIndex){
        final NumberAxis xAxis = new NumberAxis(-10, 10, 1);
        final NumberAxis yAxis = new NumberAxis(-10, 10, 1);
        final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setStyle("");

        scatterChart.setTitle("Графики функций");
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series1.setName( SystemExpression.getListOfFunction().get(systemNumber.getValue() * 2));
        series2.setName(SystemExpression.getListOfFunction().get(systemNumber.getValue() * 2 + 1));

        // Добавление данных в серии
        for (double x = -10; x <= 10; x += 0.1) {
            for (double y = -10; y <= 10; y += 0.1) {
                double z1 = SystemExpression.getListOfF().get(graphIndex * 2).apply(x,y);
                double z2 = SystemExpression.getListOfF().get(graphIndex * 2 + 1).apply(x,y);
                if (Math.abs(z1) < 0.06) {
                    series1.getData().add(new XYChart.Data<>(x, y));
                }
                if (Math.abs(z2) < 0.06) {
                    series2.getData().add(new XYChart.Data<>(x, y));
                }
            }
        }
        scatterChart.getData().addAll(series1, series2);
        if(container.getChildren().size() < 2){
            container.getChildren().add(scatterChart);
        } else{
            container.getChildren().remove(container.getChildren().size()-1);
            container.getChildren().add(scatterChart);
        }

    }
}