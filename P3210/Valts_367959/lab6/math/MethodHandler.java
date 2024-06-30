package lab6.math;

public class MethodHandler {
    private static int methodNumber;

    public static void setMethodNumber(int methodNumber) {
        MethodHandler.methodNumber = methodNumber;
    }

    public static double[][] execute(double a, double b, double y0, double h, double eps){
        switch (methodNumber){
            case(1) -> {
                return Euler.execute(a, b, y0, h);
            }
            case(2) -> {
                return RungeKutta.execute(a, b, y0, h);
            }
            default -> {
                return Miln.execute(a, b, y0, h, eps);
            }
        }
    }
}
