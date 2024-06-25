package methods;

import storage.FunctionStorage;

public class SimpsonMethod {

    public static double execute(double a, double b, double n) {
        double oddSum = 0;
        double evenSum = 0;
        double h = (b - a) / n;
        a += h;
        for(int i = 1; i < n; i++){
            if(i % 2 == 1) oddSum += (FunctionStorage.getFunction(a));
            else evenSum += (FunctionStorage.getFunction(a));
            a += h;
        }
        return h/3 * (FunctionStorage.getFunction(a) + 4*oddSum + 2*evenSum + FunctionStorage.getFunction(b)) ;
    }

}
