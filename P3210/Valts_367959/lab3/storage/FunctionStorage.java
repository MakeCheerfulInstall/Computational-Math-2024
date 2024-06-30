package storage;

public class FunctionStorage {
    private static int numberOfFunction;

    public static void setNumberOfFunction(int numberOfFunction) {
        FunctionStorage.numberOfFunction = numberOfFunction;
    }

    public static double getIntegral(double a, double b){
        return getAntiDerivative(b) - getAntiDerivative(a);
    }

    public static double getFunction(double x){
        switch(numberOfFunction){
            case 1 -> {
                return getFirstFunction(x);
            }
            case 2 -> {
                return getSecondFunction(x);
            }
            case 3 -> {
                return getThirdFunction(x);
            }
            case 4 -> {
                return getFourthFunction(x);
            }
            default -> {
                return getFifthFunction(x);
            }
        }
    }

    public static double getAntiDerivative(double x){
        switch(numberOfFunction){
            case 1 -> {
                return getFirstAntiDerivative(x);
            }
            case 2 -> {
                return getSecondAntiDerivative(x);
            }
            case 3 -> {
                return getThirdAntiDerivative(x);
            }
            case 4 -> {
                return getFourthAntiDerivative(x);
            }
            default -> {
                return getFifthAntiDerivative(x);
            }
        }
    }

    private static double getFirstFunction(double x) {
        return 2 * Math.pow(x, 3) - 9 * Math.pow(x, 2) + 7 * x + 11;
    }

    private static double getFirstAntiDerivative(double x) {
        return 2 * Math.pow(x, 4) / 4 - 9 * Math.pow(x, 3) / 3 + 7 * Math.pow(x, 2) / 2 + 11 * x;
    }

    private static double getFifthFunction(double x) {
        return 1 / Math.sqrt(2*x-Math.pow(x, 2));
    }

    private static double getFifthAntiDerivative(double x) {
        return Math.asin(x - 1);
    }

    private static double getSecondFunction(double x) {
        return 3 * Math.pow(x, 5) + Math.pow(x, 2) + 0.1;
    }

    private static double getSecondAntiDerivative(double x) {
        return 3 * Math.pow(x, 6) / 6 + Math.pow(x, 3) / 3 + x / 10;
    }

    private static double getThirdFunction(double x) {
        return Math.sin(x) + Math.cos(x);
    }

    private static double getThirdAntiDerivative(double x) {
        return Math.sin(x) - Math.cos(x);
    }

    private static double getFourthFunction(double x) {
        return 1 / x;
    }

    private static double getFourthAntiDerivative(double x) {
        return Math.log(Math.abs(x));
    }

}
