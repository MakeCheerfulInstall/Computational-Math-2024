package Computational.Math;

import Computational.Math.GraphicPart.MainComponents.MainFrame;
import Computational.Math.Methods.OneStep.EulerMethod;
import Computational.Math.Methods.OneStep.RungeKutta;
import Computational.Math.Utils.FinalResultWithAccuracy;
import Computational.Math.Utils.Processing;
import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.lang.Math.*;


public class Main {
    Processing processing = new Processing();


    void main() {
        String inputLine;
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите цифру выбранного ОДУ: ");
            printFunctions();
            printWait();
            inputLine = bf.readLine();
            int num = Integer.parseInt(inputLine);
            var chosenFunction = getFunction(num);
            System.out.println("Введите x_0 y_0 x_n step accuracy через пробел");
            printWait();
            inputLine = bf.readLine();
            String[] data = inputLine.split(" ");
            var x_0 = Double.parseDouble(data[0]);
            var y_0 = Double.parseDouble(data[1]);
            var x_n = Double.parseDouble(data[2]);
            var step = Double.parseDouble(data[3]);
            var accuracy = Double.parseDouble(data[4]);
            FinalResultWithAccuracy EulerResults = (processing.applyOneStep(new EulerMethod(), x_n, chosenFunction, x_0, y_0, step, accuracy));
            FinalResultWithAccuracy RungeKuttaResults = (processing.applyOneStep(new RungeKutta(), x_n, chosenFunction, x_0, y_0, step, accuracy));
            FinalResultWithAccuracy adamsResults = processing.applyManySteps(getCorrectFunction(num, x_0, y_0), 4, x_n, chosenFunction, x_0, y_0, step, accuracy);
            System.out.println(STR."Точность метода Эйлера: \{EulerResults.getAccuracy()}");
            System.out.println(STR."Точность метода Рунге-Кутта : \{RungeKuttaResults.getAccuracy()}");
            System.out.println(STR."Точность метода Адама: \{adamsResults.getAccuracy()}");
            var minStepSize = min(min(EulerResults.getStep(),RungeKuttaResults.getStep()),adamsResults.getStep());



            List<Double> correctYList = new ArrayList<>();
            var correctFunction = getCorrectFunction(num,x_0,y_0);
            List<Double> correctXList = new ArrayList<>();
            for (double tmp = x_0; tmp <= x_n + minStepSize; tmp+=minStepSize) {
                correctYList.add(correctFunction.apply(tmp));
                correctXList.add(tmp);
            }
            ArrayList<Double> EulerArr = (ArrayList<Double>) EulerResults.getAnswer(minStepSize);
            ArrayList<Double> RungeKuttaArr = (ArrayList<Double>) RungeKuttaResults.getAnswer(minStepSize);
            ArrayList<Double> AdamsArr = (ArrayList<Double>) adamsResults.getAnswer(minStepSize);
            StringBuilder sb = new StringBuilder();
            JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
            sb.append("");
            builder.columns("Нумер итерации","X","Метод эйлера","Метод Рунге-Кутта","Метод Адамса","Точные значения");
            int minLen = Math.min(Math.min(Math.min(correctYList.size(),EulerArr.size()),RungeKuttaArr.size()),AdamsArr.size());

            for (int i = 0; i < minLen; i++) {
                builder.row(STR."\{i}",correctXList.get(i)+"", EulerArr.get(i)+"", RungeKuttaArr.get(i)+"", AdamsArr.get(i)+"",correctYList.get(i)+"");
            }

            System.out.println(builder.getTable().toStringHorizontal());
            System.out.println("Зеленый - Эйлер, Розовый - Рунге-Кутта, Красный - Адам, Черный - точные значения" );
            MainFrame.drawArrays("🐱‍🐱‍👤🐱‍🐱‍👤🐱‍", (ArrayList<Double>) correctXList,EulerArr,RungeKuttaArr,AdamsArr, (ArrayList<Double>) correctYList,5,5);

        } catch (IOException e) {
            System.err.println(STR."Ошибка чтения \{e.getMessage()}");
            System.exit(-1);
        } catch (IllegalStateException | MalformedTableException e) {
            System.err.println(e.getMessage());
            System.exit(-2);
        }
    }

    void printWait() {
        System.out.print("> ");
    }

    void printFunctions() {
        System.out.println("1. y' = 3x + 7y");
        System.out.println("2. y' = y + cos(2x)");
        System.out.println("3. y' = x^3 + y");
    }

    public BinaryOperator<Double> getFunction(int num) {
        return switch (num) {
            case 1 -> (x, y) -> (3 * x) + (7 * y);
            case 2 -> (x, y) -> y + Math.cos(2 * x);
            case 3 -> (x, y) -> Math.pow(x, 3) + y;
            default -> throw new IllegalStateException(STR."Введите цифру из предложенных ОДУ, а не: \{num}");
        };
    }

    public Function<Double, Double> getCorrectFunction(int num, double x_0, double y_0) {
        return switch (num) {
            case 1 -> x -> {
                var CResult = EquationSolve.solveEquation(
                        C -> (C * Math.exp(7 * x_0) - 21 * x_0 - 3) / 49 - y_0
                );
                return (CResult * Math.exp(7 * x) - 21 * x - 3) / 49;
            };
            case 2 -> x -> {
                var CResult = EquationSolve.solveEquation(C ->
                        2 * sin(2 * x_0) / 5 - cos(2 * x_0) / 5 + C * exp(x_0) - y_0
                );
                var firstElement = 2 * sin(2 * x) / 5;
                var secondElement = cos(2 * x) / 5;
                return firstElement - secondElement + exp(x) * CResult;
            };
            case 3 -> x -> {
                var CResult = EquationSolve.solveEquation(
                        C -> C * exp(x_0) - Math.pow(x_0, 3) - 3 * pow(x_0, 2) - 6 * x_0 - 6 - y_0
                );
                return CResult * exp(x) - Math.pow(x, 3) - 3 * pow(x, 2) - 6 * x - 6;
            };
            default -> throw new IllegalStateException(STR."Unexpected value: \{num}");
        };
    }


}