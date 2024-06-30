package Computational.Math.Methods;

import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.Function;

public class TrapezoidMethod extends AbstractMethod{
    public TrapezoidMethod() {
        super("Метод трапеций");
    }

    @Override
    public Double solve(Function<Double, Double> function, Double a, Double b, int n,boolean isNeedToPrint) throws MalformedTableException {
        if(isNeedToPrint)printMethodName();
        var builder = MonospaceTable.build();
        var h = (b-a)/n;
        var y0 = function.apply(a);
        var yLast = function.apply(b);
        //сумма yi, исключая 0 и последний
        var sumYi = 0d;
        var result = 0d;
        builder.columns("i","xi","fi","result");
        builder.row("0",String.format("%.3f",a),String.format("%.3f",y0),"-");
        for (int i = 1; i < n; i++) {
            Double functionValue = function.apply(a + h*i);
            if(functionValue.isInfinite())
                return null;
            sumYi += functionValue;
            builder.row(i+"",String.format("%.3f",a+h*i),String.format("%.3f",function.apply(a + h*i)),"-");
        }
        result += h*((y0+yLast)/2 + sumYi);
        builder.row(n+"",String.format("%.3f",b),String.format("%.3f",yLast),String.format("%.3f",result));
        if(isNeedToPrint)
            System.out.println(builder.getTable().toStringHorizontal());
        return result;
    }
}
