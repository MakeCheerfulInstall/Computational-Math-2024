package methods;

import storage.FunctionStorage;

public class TrapezoidMethod {
    public static double execute(double a, double b, double n) {
        double sum = 0;
        double h = (b-a)/n;
        a += h;
        for(int i = 1; i < n; i++){
            sum += (FunctionStorage.getFunction(a));
            a += h;
        }
        return h * (sum+(FunctionStorage.getFunction(a)+FunctionStorage.getFunction(b))/2);
    }
}
