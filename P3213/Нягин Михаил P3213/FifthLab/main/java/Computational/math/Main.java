package Computational.math;


import Computational.math.Utils.CalculatorTables;
import Computational.math.Utils.FabricMethods;
import Computational.math.Utils.FunctionalTable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {

    void main(){
        try(BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {
            FabricMethods fb = new FabricMethods();
            String line;
            while(true) {
                System.out.println("Выберите тип ввода: ");
                System.out.println("1. С файла");
                System.out.println("2. С клавиатуры");
                System.out.println("3. Выбор уравнения из списка");
                printWaiting();
                line = input.readLine();
                if(!line.matches("[1-3]")){
                    System.err.println("Введите только нумер способа");
                    continue;
                }
                switch (line){
                    case "1" -> {}
                    case "2" -> {}
                    case "3" -> {
                        var functionalTable = processingThirdVariant(input);
                        System.out.println("Введите искомый x");
                        printWaiting();
                        double xCurr = Double.parseDouble(input.readLine());
                        fb.executeEverything(functionalTable,xCurr);
                        System.out.println("Закончили экзекуцию");
                    }
                    default -> {}
                }

            }
        } catch (IOException e) {
            System.err.println(STR."Ошибка чтения \{e.getMessage()}");
        }catch (NullPointerException e){
            System.err.println("Инвалидный ввод!");
            System.exit(-1);
        }
    }

    public FunctionalTable processingThirdVariant(BufferedReader input) throws IOException{
        String line;
        System.out.println("Выберите одно из уравнений: ");
        System.out.println("1. sin(x)");
        System.out.println("2. cos(x)");
        System.out.println("3. x^2");
        printWaiting();
        line = input.readLine();
        if(!line.matches("[1-3]")){
            System.err.println("Введите только нумер способа");
            return null;
        }
        Function<Double,Double> f;
        switch (line){
            case "1" -> {f = Math::sin;}
            case "2" -> {f = Math::cos;}
            case "3" -> {f=x->Math.pow(x,2);}
            default -> f = null;
        }
        System.out.println("Введите исследуемый интервал в формате(например -5 5)");
        printWaiting();
        String[] distanceStr = input.readLine().split(" ");
        double a = Double.parseDouble(distanceStr[0]);
        double b = Double.parseDouble(distanceStr[1]);
        System.out.println("Введите количество точек на интервале: ");
        printWaiting();
        int amountOfPoints = Integer.parseInt(input.readLine());

        FunctionalTable functionalTable = CalculatorTables.createTable(a,b,amountOfPoints,f);
        List<List<Double>> finiteDiff = new ArrayList<>();
        CalculatorTables.finiteDiff(finiteDiff, List.of(functionalTable.getyArr()));
        System.out.println(STR."Конечные разности \{finiteDiff}");
        System.out.println("Ваша таблица");
        System.out.println(functionalTable);
        return functionalTable;
    }

    private static void printWaiting(){
        System.out.print("> ");
    }
}