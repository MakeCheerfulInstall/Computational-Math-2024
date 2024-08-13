package Computational.Math.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Functions {
    private static final ArrayList<Function<Double,Double>> functions = new ArrayList<>(
            List.of(x -> x*x,
                    x->4*Math.pow(x,3) - 5*x*x + 6*x - 7,
                    x-> 1/(x*x-3*x)
            )
    );
    public static void printFunctions(){
        System.out.println("1. x**2");
        System.out.println("2. 4*x**3 - 5*x**2 + 6*x - 7");
        System.out.println("3. 1/ (x**2 - 3*x)");
    }
    public static Function<Double,Double> getFunctionById(int id){
        return functions.get(id);
    }
    public static ArrayList<Function<Double,Double>> getFunctions(){
        return functions;
    }
}
