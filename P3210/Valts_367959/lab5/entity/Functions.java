package lab5.entity;

public class Functions {

    private static int numberOfFunction;

    public static void setNumberOfFunction(int numberOfFunction) {
        Functions.numberOfFunction = numberOfFunction;
    }

    public static double f(double x){
        switch(numberOfFunction){
            case 1 -> {
                return f1(x);
            }
            case 2 -> {
                return f2(x);
            }
            default -> {
                return f3(x);
            }
        }
    }

    public static double f1(double x){
        return Math.pow(x,2) + 239;
    }

    public static double f2(double x){
        return Math.pow(2, x) - x + 1;
    }

    public static double f3(double x){
        return Math.log(x);
    }
}
