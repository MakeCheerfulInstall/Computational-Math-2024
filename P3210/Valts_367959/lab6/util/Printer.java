package lab6.util;

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

    public static void printInterval(double a, double b, double h) {
        System.out.println(getBlueText("\nРешение на интервале [" + a + ", " + b + "] с шагом h = " + h));
    }

    public static void printTable(double[][] result, int e) {
        System.out.printf(getYellowText("%-7s %-20s %-30s %-30s\n"), "i", "x_i", "y_i", "f(x_i, y_i)");
        for (int i = 0; i < result.length; i++) {
            System.out.printf(getYellowText("%-7d") + getGreenText("%-20.5f %-30.5f %-30.5f\n")
                    , i
                    , (Math.round(result[i][0] * Math.pow(10, e)) / Math.pow(10, e))
                    , (Math.round(result[i][1] * Math.pow(10, e)) / Math.pow(10, e))
                    , (Math.round(result[i][2] * Math.pow(10, e)) / Math.pow(10, e)));
        }
        System.out.println();
    }
}
