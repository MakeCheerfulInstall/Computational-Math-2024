package methods;

import storage.FunctionStorage;
import util.Printer;

public class RectangleMethod {
    private static int typeOfRectangleMethod;

    public static void setTypeOfRectangleMethod(int typeOfRectangleMethod) {
        RectangleMethod.typeOfRectangleMethod = typeOfRectangleMethod;
    }

    public static int getTypeOfRectangleMethod() {
        return typeOfRectangleMethod;
    }

    public static double execute(double a, double b, double n) {
        switch (typeOfRectangleMethod){
            case 1 -> {
                return executeRight(a, b, n);
            }
            case 2 -> {
                return executeLeft(a, b, n);
            }
            default-> {
                return executeMedium(a, b, n);
            }
        }

    }

    private static double executeRight(double a, double b, double n){
        double sum = 0;
        double h = (b-a)/n;
        a += h;
        for(int i = 1; i < n+1; i++){
            sum += (FunctionStorage.getFunction(a));
            a += h;
        }
        return h * sum;
    }

    private static double executeLeft(double a, double b, double n){
        double sum = 0;
        double h = (b-a)/n;
        for(int i = 0; i < n; i++){
            sum += (FunctionStorage.getFunction(a));
            a += h;
        }
        return h * sum;
    }

    private static double executeMedium(double a, double b, double n){
        double sum = 0;
        double h = (b-a)/n;
        for(int i = 0; i < n; i++){
            sum += (FunctionStorage.getFunction(a + h/2));
            a += h;
        }
        return h * sum;
    }


}
