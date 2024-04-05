package Computational.math;

import Computational.math.Functions.Functions;
import Computational.math.Functions.SystemFunctions;
import Computational.math.GraphicPart.MainComponents.MainFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

public class Main {
    public static void main(String[] args) {
        FabricMethods fabricMethods = new FabricMethods();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                System.out.println("Введите нумер одного из методов в списке: ");
                System.out.println("------------------------------------------");
                System.out.println("1. Метод половинного деления");
                System.out.println("2. Метод секущих");
                System.out.println("3. Метод простой итерации");
                System.out.println("4. Метод Ньютона");
                System.out.print(">>> ");
                String data = bf.readLine();
                if(data==null){
                    System.exit(-1);
                }
                if (data.equals("exit")) System.exit(1);
                try {
                    if (Integer.parseInt(data) <= 4 && Integer.parseInt(data) >= 1) {
                        MethodName methodName = MethodName.values()[Integer.parseInt(data) - 1];
                        if (methodName == MethodName.NEWTON_METHOD) {
                            proceningNewtonMethod(bf);
                            continue;
                        }
                        System.out.println("Выберите одно уравнение и введите его нумер: ");
                        Functions.printAllFunctions();
                        System.out.print(">>> ");
                        int numberOfChosenFunction = Integer.parseInt(bf.readLine());

                        MainFrame.drawSingleFunction(methodName.name(), new Functions(numberOfChosenFunction).getFunction());


                        System.out.println("Введите левую границу");
                        System.out.print(">>> ");
                        double a = Double.parseDouble(bf.readLine());
                        System.out.println("Введите правую границу");
                        System.out.print(">>> ");
                        double b = Double.parseDouble(bf.readLine());

                        if (a >= b) {
                            System.out.println("левая граница должна быть меньше");
                            continue;
                        }

                        System.out.println("Введите точность");
                        System.out.print(">>> ");

                        float epsilon = Float.parseFloat(bf.readLine());

                        fabricMethods.executeMethod(methodName, a, b, epsilon, numberOfChosenFunction);
                        System.out.println();
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Введите нумер метода");
                }
            } while (true);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void proceningNewtonMethod(BufferedReader bf) throws IOException {
        System.out.println("Выберите одну систему уравнений и введите её нумер: ");
        SystemFunctions.printAllSystem();
        System.out.print(">>> ");
        int numberOfChosenSystem = Integer.parseInt(bf.readLine());
        if (numberOfChosenSystem < 1 || numberOfChosenSystem > 2) {
            System.err.println("Вы должны выбрать одну систему из списка");
            return;
        }
        SystemFunctions sf = new SystemFunctions(numberOfChosenSystem);
        MainFrame.drawSystem("Метод Ньютона", sf.getChosenSystem());

        System.out.println("Введите начальные X0:");
        System.out.print(">>> ");
        double x0 = Double.parseDouble(bf.readLine());
        System.out.println("Введите начальные Y0:");
        System.out.print(">>> ");
        double y0 = Double.parseDouble(bf.readLine());
        System.out.println("Задайте точность: ");
        System.out.print(">>> ");
        float epsilon = Float.parseFloat(bf.readLine());
        FabricMethods fb = new FabricMethods();
        fb.executeMethod(MethodName.NEWTON_METHOD,x0,y0,epsilon,numberOfChosenSystem);

    }


    //   todo Вынести всю эту логику в отдельный класс?
    public static MethodName whichIsChosen(String data) {
        switch (data) {
            case "1":
                System.out.println("Выбран метод 1: ");
                break;
            case "2":
                System.out.println("Выбран метод 2");
                break;
            case "3":
                System.out.println("Выбран метод 3");
                break;
            case "4":
                System.out.println("Выбран метод 4");
                break;
            default:
                System.out.println("Получен не валидный ввод: " + data);
                break;
        }
        return MethodName.NEWTON_METHOD;
    }
}