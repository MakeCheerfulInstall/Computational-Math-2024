package lab6.util;

import java.util.Scanner;

public class Reader {

    private static final Scanner scanner = new Scanner(System.in);

    public static int methodInput() {
        System.out.println(Printer.getBlueText("Выберете действие:"));
        System.out.println(Printer.getYellowText("1. Решение методом Эйлера"));
        System.out.println(Printer.getYellowText("2. Решение методом Рунге-Кутта 4 порядка"));
        System.out.println(Printer.getYellowText("3. Решение методом Милна"));
        System.out.println(Printer.getRedText("4. Выход из программы"));
        try {
            int method = Integer.parseInt(scanner.next().trim());
            if (method >= 1 && method <= 4) return method;
            else return methodInput();
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return methodInput();
        }
    }

    public static int functionSelection() {
        System.out.println(Printer.getBlueText("Выберете функцию:"));
        System.out.println(Printer.getYellowText("1. x^2 - 2 * y"));
        System.out.println(Printer.getYellowText("2. 2 * x"));
        System.out.println(Printer.getYellowText("3. y + (1+x) * y^2"));
        try {
            int functionNumber = Integer.parseInt(scanner.next().trim());
            if (functionNumber > 0 && functionNumber <= 3) {
                return functionNumber;
            } else {
                System.out.println(Printer.getRedText("Ошибка ввода!"));
                return functionSelection();
            }
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return functionSelection();
        }
    }

    public static double inputA() {
        System.out.print(Printer.getBlueText("Введите начало интервала a: "));
        try {
            return Double.parseDouble(scanner.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return inputA();
        }
    }

    public static double inputB() {
        System.out.print(Printer.getBlueText("Введите конец интервала b: "));
        try {
            return Double.parseDouble(scanner.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return inputB();
        }
    }

    public static double inputY() {
        System.out.print(Printer.getBlueText("Введите y0: "));
        try {
            return Double.parseDouble(scanner.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return inputY();
        }
    }

    public static double inputH() {
        System.out.print(Printer.getBlueText("Введите шаг h: "));
        try {
            return Double.parseDouble(scanner.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return inputH();
        }
    }

    public static int inputE() {
        System.out.print(Printer.getBlueText("Введите точность e: "));
        try {
            return Integer.parseInt(scanner.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return inputE();
        }
    }

    public static double inputEps() {
        System.out.print(Printer.getBlueText("Введите точность вычислений eps: "));
        try {
            return Double.parseDouble(scanner.next().trim());
        } catch (NumberFormatException ignored) {
            System.out.println(Printer.getRedText("Ошибка ввода!"));
            return inputEps();
        }
    }
}
