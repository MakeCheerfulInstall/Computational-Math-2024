package Computational.math.ApproximationMethods;

import Computational.math.FunctionalTable;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.Arrays;
import java.util.function.Function;

public class ExponentialApproximation extends AbstractMethod{
    private final LinearApproximation ln = new LinearApproximation();
    public ExponentialApproximation(){
        super("Экспоненциальная аппроксимация",MethodName.ExponentialApproximation);
    }
    @Override
    public Function<Double, Double> apply(FunctionalTable data) throws MalformedTableException {
        var functionTable = data.getTable();
        var controlSum = 0d;
        for (int i = 0; i < functionTable[0].length; i++) {
            controlSum += functionTable[0][i];
            if(functionTable[1][i] <= 0) continue;
            functionTable[1][i] = Math.log(functionTable[1][i]);
        }
        System.out.println("CONTROL SX: " + controlSum);
        ln.apply(new FunctionalTable(functionTable));
        double[] args = ln.getArguments();
        var a = Math.exp(args[0]);
        var b = args[1];
        System.out.println("-------\nafter got: ");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        Function<Double,Double> P=x-> a*Math.exp(b*x);
//        printResult(new FunctionalTable(functionTable),P);
        System.out.println("a*exp(b*x)");
        System.out.println(a+"*exp("+b+"*x)");
        System.out.println(P.apply(0.2));
        return x-> a*Math.exp(b*x);
    }
    public void notMine(double[][] functionTable) throws MalformedTableException {
        double[][] modifiedFunctionTable = Arrays.stream(functionTable).map(double[]::clone).toArray(double[][]::new);
        for (double[] xy: modifiedFunctionTable) {
            if (xy[1] <= 0) continue;
            xy[1] = Math.log(xy[1]);
        }
        Function<Double,Double> linear = ln.apply(new FunctionalTable(modifiedFunctionTable));
        double[] coefficients = ln.getArguments();
        coefficients[1] = Math.exp(coefficients[1]);
        Function<Double, Double> f = x->coefficients[1]*Math.exp(coefficients[0]*x);
        var S = 0d;
        for (int i = 0; i < functionTable[0].length; i++) {
            var currentP = f.apply(functionTable[0][i]);
            var currentEpsilon = currentP-functionTable[1][i];
            S += Math.pow(currentP-functionTable[1][i],2);
        }
        System.out.println("S="+S);
        System.out.println("coefficients[0] = " + coefficients[0]);
        System.out.println("coefficients[1] = " + coefficients[1]);
        System.exit('a'+'b'-98);
        //        return new ApproximationResult(ApproximationType.EXPONENTIAL, coefficients, f, deviationMeasure(functionTable, f));
    }

}
