package lab6.math;

public class Functions {
    private static int functionNumber;

    public static void setFunctionNumber(int functionNumber) {
        Functions.functionNumber = functionNumber;
    }

    public static double f(double x, double y) {
        switch (functionNumber) {
            case (1) -> {
                return Math.pow(x, 2) - 2 * y;
            }
            case (2) -> {
                return 2 * x;
            }
            default -> {
                return y + (1 + x) * Math.pow(y, 2);
            }
        }
    }

    public static double solution(double x) {
        switch (functionNumber) {
            case (1) -> {
                return (1 / Math.exp(2 * x)) + (Math.pow(x, 2) / 2) - (x / 2) + (1d / 4);
            }
            case (2) -> {
                return Math.pow(x, 2);
            }
            default -> {
                return -Math.exp(x) / (x * Math.exp(x));
            }
        }
    }
}