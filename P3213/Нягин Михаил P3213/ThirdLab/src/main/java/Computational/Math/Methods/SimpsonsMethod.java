package Computational.Math.Methods;

import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.Function;

public class SimpsonsMethod extends AbstractMethod{
    public SimpsonsMethod() {
        super("Метод симпсона");
    }

    @Override
    public Double solve(Function<Double, Double> function, Double a, Double b, int n,boolean isNeedToPrint) throws MalformedTableException {
        if (n % 2 != 0){
            throw new IllegalArgumentException("n должен быть четным для работы метода Симпсона!");
        }
        JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
        if(isNeedToPrint)
            printMethodName();
        var h = (b-a)/n;
        var sumaFromY1ToYLast = 0d;
        var sumaFromY2ToYPreLast = 0d;
        builder.columns("result");
        for (int i = 1; i < n; i+=2) {
            Double functionValue = function.apply(a + i * h);
            if(functionValue.isInfinite()){
                return null;
            }
            sumaFromY1ToYLast += functionValue;
        }
        for (int i = 2; i < n-1; i+=2) {
            Double functionValue =function.apply(a + i * h);
            if(functionValue.isInfinite()){
                return null;
            }
            sumaFromY2ToYPreLast +=functionValue;
        }
        var result = (h/3) * (function.apply(a) + 4*sumaFromY1ToYLast + 2*sumaFromY2ToYPreLast + function.apply(b));
        builder.row(String.format("%.3f",result));
        if(isNeedToPrint) {
            System.out.println("n=" + n);
            System.out.println(builder.getTable());
        }
        return result;
    }
}
