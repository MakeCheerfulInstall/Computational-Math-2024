package util;

import storage.FunctionStorage;

public class Printer {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static String getRedText(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    public static String getGreenText(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    public static String getBlueText(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String getYellowText(String text) {
        return ANSI_YELLOW + text + ANSI_RESET;
    }

    public static void printFunctions() {
        System.out.println(Printer.getYellowText("[1] 2x^3 - 9x^2 + 3x + 11"));
        System.out.println(Printer.getYellowText("[2] 3x^5 + x^2 + 0.1"));
        System.out.println(Printer.getYellowText("[3] sin(x) + cos(x)"));
        System.out.println(Printer.getYellowText("[4] 1 / x"));
        System.out.println(Printer.getYellowText("[5] 1 / x^2"));
        System.out.print(getBlueText("""
                    Ваш выбор:\s"""));
    }

    public static void printMethods() {
        System.out.println(Printer.getYellowText("[1] Метод прямоугольников"));
        System.out.println(Printer.getYellowText("[2] Метод трапеций"));
        System.out.println(Printer.getYellowText("[3] Метод симпсона"));
        System.out.print(getBlueText("""
                Ваш выбор:\s"""));
    }

    public static void printRectangleMethods() {
        System.out.println(Printer.getYellowText("[1] Правы"));
        System.out.println(Printer.getYellowText("[2] Левые"));
        System.out.println(Printer.getYellowText("[3] Средние"));
        System.out.print(getBlueText("""
                Ваш выбор:\s"""));
    }

    public static void printResult(double a, double b, int n, double integral1, double accuracy, double error, double Runge){
        System.out.println(getGreenText("—————————————————RESULT—————————————————"));
        System.out.println(getGreenText("Точное I = " + FunctionStorage.getIntegral(a, b)));
        System.out.println(getGreenText("полученное I = " + integral1));
        System.out.println(getGreenText("AАбсолютная погрешность = " + error));
        System.out.println(getGreenText("Относительная погрешность = " + 100 * Math.abs(error/(FunctionStorage.getIntegral(a, b))) + " %"));
        System.out.println("Погрешност по рунге = " + Runge);
        System.out.println(getGreenText("ε = " + accuracy));
        System.out.println(getGreenText("n = " + n));
        System.out.println(getGreenText("————————————————————————————————————————"));
    }



}
