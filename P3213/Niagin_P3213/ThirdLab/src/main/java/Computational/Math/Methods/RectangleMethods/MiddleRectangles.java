package Computational.Math.Methods.RectangleMethods;

import Computational.Math.Methods.AbstractMethod;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.Function;

public class MiddleRectangles extends AbstractMethod {

    public MiddleRectangles() {
        super("Метод средних прямоугольников");
    }

    @Override
    public Double solve(Function<Double, Double> function, Double a, Double b, int n,boolean isNeedToPrint) throws MalformedTableException {
        if(isNeedToPrint)
            printMethodName();
        var h = (b-a)/n;
        var builder = MonospaceTable.build();
        builder.columns("i","xi","fi","x_{i-1/2}","y_{i-1/2}","current result");
        var result = 0d;
        double xPrev = a;
        Double fMiddle;
        builder.row("0",String.format("%.3f",xPrev),String.format("%.3f",function.apply(xPrev)),"-","-","0");
        for (int i = 0; i < n; i++) {
            var xMiddle = xPrev + h/2;
            xPrev += h;
            fMiddle = function.apply(xMiddle);
            if(fMiddle.isInfinite() || function.apply(xPrev).isInfinite()){
                return null;
            }
            result+= h*fMiddle;
            builder.row("" +(i + 1), String.format("%.3f",xPrev),String.format("%.3f",function.apply(xPrev)),String.format("%.3f",xMiddle),
                    String.format("%.3f",fMiddle),String.format("%.3f",result));
        }
        if(isNeedToPrint)
            System.out.println(builder.getTable().toStringHorizontal());
        return result;
    }
}
