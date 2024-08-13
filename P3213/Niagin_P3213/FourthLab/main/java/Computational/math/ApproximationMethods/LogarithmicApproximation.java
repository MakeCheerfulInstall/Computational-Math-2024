package Computational.math.ApproximationMethods;

import Computational.math.FunctionalTable;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.function.Function;

public class LogarithmicApproximation extends AbstractMethod{
    LinearApproximation ln = new LinearApproximation();
    public LogarithmicApproximation() {
        super("Логарифмическая аппроксимация",MethodName.LogarithmicApproximation);
    }

    @Override
    public Function<Double, Double> apply(FunctionalTable data) throws MalformedTableException {
        var functionTable = data.getTable();

        for (int i = 0; i < functionTable[0].length; i++) {
            if(functionTable[0][i] <= 0) continue;
            functionTable[0][i] = Math.log(functionTable[0][i]);
        }

        ln.apply(new FunctionalTable(functionTable));
        double[] args = ln.getArguments();

        return x -> args[1] * Math.log(x) + args[0];
    }
}
