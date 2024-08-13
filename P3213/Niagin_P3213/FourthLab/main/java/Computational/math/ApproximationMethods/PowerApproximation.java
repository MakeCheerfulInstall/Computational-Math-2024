package Computational.math.ApproximationMethods;

import Computational.math.FunctionalTable;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.function.Function;

public class PowerApproximation extends AbstractMethod{
    LinearApproximation ln = new LinearApproximation();
    public PowerApproximation() {
        super("Степенная аппроксимация",MethodName.PowerApproximation);
    }

    @Override
    public Function<Double, Double> apply(FunctionalTable data) throws MalformedTableException {
        var table = data.getTable();
        for (int i = 0; i < table[0].length; i++) {
            table[0][i] = Math.log(table[0][i]);
            table[1][i] = Math.log(table[1][i]);
        }
        ln.apply(new FunctionalTable(table));
        double[] args = ln.getArguments();
        System.out.println("fi(x) = a * x ** b");
        System.out.println("fi(x) = " + Math.exp(args[0])+"* x ** " + args[1]);
        return x -> Math.exp(args[0]) * Math.pow(x, args[1]);
    }
}
