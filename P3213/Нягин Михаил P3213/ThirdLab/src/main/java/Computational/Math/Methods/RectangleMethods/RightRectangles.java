package Computational.Math.Methods.RectangleMethods;

import Computational.Math.Methods.AbstractMethod;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.Function;

public class RightRectangles extends AbstractMethod {
    public RightRectangles() {
        super("Метод правых прямоугольников");
    }

    @Override
    public Double solve(Function<Double, Double> function, Double a, Double b, int n,boolean isNeedToPrint) throws MalformedTableException {
        if(isNeedToPrint)printMethodName();
        var builder = MonospaceTable.build();
        builder.columns("i","xi","fi","currentSum");
        var xCurrent = a;
        var fCurrent = function.apply(xCurrent);
        var h = (b-a)/n;
        var result = 0d;
        for (int i = 0; i < n ; i++) {
            xCurrent += h;
            fCurrent = function.apply(xCurrent);
            if(fCurrent.isInfinite())
                return null;
            result += fCurrent*h;
            builder.row(i+"",String.format("%.3f",xCurrent),String.format("%.3f",fCurrent),String.format("%.3f",result));
        }
        if(isNeedToPrint)
            System.out.println(builder.getTable().toStringHorizontal());
        return result;
    }
}
