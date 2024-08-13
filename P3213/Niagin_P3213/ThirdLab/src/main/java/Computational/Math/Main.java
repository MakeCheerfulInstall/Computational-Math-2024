package Computational.Math;

import Computational.Math.Utils.FabricMethods;
import Computational.Math.Utils.Functions;
import Computational.Math.Utils.MethodName;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        FabricMethods fabricMethods = new FabricMethods();
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Выберите название метода: ");
                fabricMethods.printNameMethods();
                System.out.print(">>> ");
                String inputLine = bf.readLine();

                if (inputLine == null || inputLine.isEmpty()) {
                    System.out.println("Неверный выбор метода. Повторите попытку.");
                    continue;
                }
                int idMethod = Integer.parseInt(inputLine);
                MethodName methodName = getNameByID(idMethod);

                System.out.println("Выберите одно из уравнений: ");
                Functions.printFunctions();
                System.out.print(">>> ");
                inputLine =bf.readLine();
                if (inputLine == null || inputLine.isEmpty()) {
                    System.out.println("Неверный выбор метода. Повторите попытку.");
                    continue;
                }
                int idFunction = Integer.parseInt(inputLine) - 1;
                if (idFunction < 0 || idFunction >= Functions.getFunctions().size()) {
                    System.out.println("Неверный выбор уравнения. Повторите попытку.");
                    continue;
                }

                System.out.println("Выберите начало отрезка:");
                System.out.print(">>> ");
                inputLine = bf.readLine();
                if (inputLine == null || inputLine.isEmpty()) {
                    System.out.println("Неверный выбор отрезка. Повторите попытку.");
                    continue;
                }
                double a = Double.parseDouble(inputLine);

                System.out.println("Выберите конец отрезка:");
                System.out.print(">>> ");
                double b = Double.parseDouble(bf.readLine());

                System.out.println("Выберите точность");
                System.out.print(">>> ");
                double epsilon = Double.parseDouble(bf.readLine());
                if(epsilon <= 0){
                    System.err.println("Точность должна быть больше нуля");
                    continue;
                }
                try {
                    fabricMethods.executeMethod(methodName, Functions.getFunctionById(idFunction), a, b, epsilon);
                } catch (MalformedTableException e) {
                    System.err.println("Ошибка при создании таблицы: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            System.exit(-1);
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат числа: " + e.getMessage());
            System.exit(-1);
        }
    }

    public static MethodName getNameByID(int id) {
        return switch (id) {
            case 1 -> MethodName.SIMPSON;
            case 2 -> MethodName.RECTANGLES_LEFT;
            case 3 -> MethodName.RECTANGLES_MIDDLE;
            case 4 -> MethodName.RECTANGLES_RIGHT;
            case 5 -> MethodName.TRAPEZOID;
            default -> null;
        };
    }
}
