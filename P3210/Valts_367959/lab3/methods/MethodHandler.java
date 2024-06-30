package methods;

import util.Printer;

import java.util.concurrent.TimeoutException;

public class MethodHandler {
    private static int typeOfMethod;

    public static void setTypeOfMethod(int typeOfMethod) {
        MethodHandler.typeOfMethod = typeOfMethod;
    }

    public static double[] execute(double a, double b, double accuracy) throws TimeoutException {
        int n = 4;
        int k = getK();
        double integral0 = calculateI(a, b, n);
        double integral1;
        int i = 0;
        double Runge;
        while(true){
            i++;
            n *= 2;
            integral1 = calculateI(a, b, n);
            Runge = (Math.abs(integral1 - integral0)/(Math.pow(2, k) - 1));
            if(Runge < accuracy) break;
            integral0 = integral1;
            if (i>5000) throw new TimeoutException();
        }
        Printer.printResult(a, b, n, integral1, accuracy, Math.abs(integral1 - integral0)/(Math.pow(2, k) - 1), Runge);
        return new double[]{integral1, n};
    }

    private static int getK() {
        switch(typeOfMethod){
            case 1 -> {
                if(RectangleMethod.getTypeOfRectangleMethod() == 3) return 2;
                else return 1;
            }
            case 2 -> {
                return 2;
            }
            default-> {
                return 4;
            }
        }
    }

    private static double calculateI(double a, double b, double n){
        switch(typeOfMethod){
            case 1 -> {
                return RectangleMethod.execute(a, b, n);
            }
            case 2 -> {
                return TrapezoidMethod.execute(a, b, n);
            }
            default-> {
                return SimpsonMethod.execute(a, b, n);
            }
        }
    }

    public static int getTypeOfMethod() {
        return typeOfMethod;
    }
}
