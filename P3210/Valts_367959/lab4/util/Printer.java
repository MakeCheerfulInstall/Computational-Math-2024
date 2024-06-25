package lab4.util;

import lab4.work.Approximation;

import java.util.ArrayList;

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

    public static void printInputExample() {
        System.out.println(getYellowText("——————————————————————————————INPUT EXAMPLE——————————————————————————————"));
        System.out.println(getYellowText("Input number of dots: ") + getYellowText("n"));
        System.out.println(getYellowText("Input dots:"));
        System.out.println(getYellowText("x1 y1"));
        System.out.println(getYellowText("x2 y2"));
        System.out.println(getYellowText("... ..."));
        System.out.println(getYellowText("—————————————————————————————————————————————————————————————————————————"));
    }

    public static void printP(double[] coefficients, double deviation, double R2) {
        switch (Approximation.getNumberApprox()) {
            case 1 -> System.out.println(getYellowText("P1(x) = ")
                    + getGreenText("(" + String.format("%.4f", coefficients[0]) + ")") + getYellowText("x + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[1]) + ")")
                    + getYellowText(" σ = ") + getRedText(String.format("%.4f", deviation))
                    + getYellowText(" R^2 = ") + getRedText(String.format("%.4f", R2)));
            case 2 -> System.out.println(getYellowText("P2(x) = ")
                    + getGreenText("(" + String.format("%.4f", coefficients[2]) + ")") + getYellowText("x^2 + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[1]) + ")") + getYellowText("x + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[0]) + ")")
                    + getYellowText(" σ = ") + getRedText(String.format("%.4f", deviation))
                    + getYellowText(" R^2 = ") + getRedText(String.format("%.4f", R2)));
            case 3 -> System.out.println(getYellowText("P3(x) = ")
                    + getGreenText("(" + String.format("%.4f", coefficients[3]) + ")") + getYellowText("x^3 + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[2]) + ")") + getYellowText("x^2 + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[1]) + ")") + getYellowText("x + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[0]) + ")")
                    + getYellowText(" σ = ") + getRedText(String.format("%.4f", deviation))
                    + getYellowText(" R^2 = ") + getRedText(String.format("%.4f", R2)));
            case 4 -> System.out.println(getYellowText("P4(x) = ")
                    + getGreenText("(" + String.format("%.4f", coefficients[0]) + ")") + getYellowText("x^")
                    + getGreenText("(" + String.format("%.4f", coefficients[1]) + ")")
                    + getYellowText(" σ = ") + getRedText(String.format("%.4f", deviation))
                    + getYellowText(" R^2 = ") + getRedText(String.format("%.4f", R2)));
            case 5 -> System.out.println(getYellowText("P5(x) = ")
                    + getGreenText("(" + String.format("%.4f", coefficients[0]) + ")") + getYellowText("e^")
                    + getGreenText("(" + String.format("%.4f", coefficients[1]) + getYellowText("x") + getGreenText(")"))
                    + getYellowText(" σ = ") + getRedText(String.format("%.4f", deviation))
                    + getYellowText(" R^2 = ") + getRedText(String.format("%.4f", R2)));
            default -> System.out.println(getYellowText("P6(x) = ")
                    + getGreenText("(" + String.format("%.4f", coefficients[0]) + ")") + getYellowText("ln(x) + ")
                    + getGreenText("(" + String.format("%.4f", coefficients[1]) + ")")
                    + getYellowText(" σ = ") + getRedText(String.format("%.4f", deviation))
                    + getYellowText(" R^2 = ") + getRedText(String.format("%.4f", R2)));
        }
    }

    public static void printR(double R) {
        System.out.println(Printer.getYellowText("R = ") + Printer.getRedText(String.format("%.4f", R)));
    }

    public static void printTable(ArrayList<double[]> table) {
        StringBuilder sb = new StringBuilder();
        sb.append(getYellowText(String.format("%-6s", "X")));
        for (double[] lines : table) {
            sb.append(getBlueText(String.format("%-10s", String.format("%.2f", lines[0]))));
        }
        sb.append("\n");
        sb.append(getYellowText(String.format("%-6s", "Y")));
        for (double[] lines : table) {
            sb.append(getBlueText(String.format("%-10s", String.format("%.2f", lines[1]))));
        }
        sb.append("\n");
        sb.append(getYellowText(String.format("%-6s", "P(x)")));
        for (double[] lines : table) {
            sb.append(getBlueText(String.format("%-10s", String.format("%.4f", lines[2]))));
        }
        sb.append("\n");
        sb.append(getYellowText(String.format("%-6s", "ξ")));
        for (double[] lines : table) {
            sb.append(getBlueText(String.format("%-10s", String.format("%.4f", lines[3]))));
        }
        System.out.println(sb);
    }

    public static void printLabel() {
        switch (Approximation.getNumberApprox()) {
            case 1 ->
                    System.out.println("\n" + getGreenText("—————————————————————————————————LINEAR——————————————————————————————————"));
            case 2 ->
                    System.out.println("\n" + getGreenText("————————————————————————————————QUADRATIC————————————————————————————————"));
            case 3 ->
                    System.out.println("\n" + getGreenText("——————————————————————————————————CUBIC——————————————————————————————————"));
            case 4 ->
                    System.out.println("\n" + getGreenText("——————————————————————————————————POWER——————————————————————————————————"));
            case 5 ->
                    System.out.println("\n" + getGreenText("———————————————————————————————EXPONENTIAL———————————————————————————————"));
            default ->
                    System.out.println("\n" + getGreenText("———————————————————————————————LOGARITHMIC———————————————————————————————"));
        }
    }

    public static void printResult(int result) {
        System.out.println("\n"
                + getRedText("————————————————")
                + getYellowText(" RESULT : ")
                + getGreenText("P" + result + "(x) is the best approximation ")
                + getRedText("————————————————"));
    }
}