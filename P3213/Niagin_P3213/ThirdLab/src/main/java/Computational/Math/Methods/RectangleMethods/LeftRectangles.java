package Computational.Math.Methods.RectangleMethods;

import Computational.Math.Methods.AbstractMethod;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.Function;

public class LeftRectangles extends AbstractMethod {

    public LeftRectangles(){
        super("Метод левых прямоугольников");
    }

    @Override
    public Double solve(Function<Double, Double> function, Double a, Double b, int n,boolean isNeedToPrint) throws MalformedTableException {
        if (isNeedToPrint)
            printMethodName();
        var builder = MonospaceTable.build();
        builder.columns("i","xi","fi","currentSum");
        double h = (b-a)/n;
        double currentX = a;
        Double previousFun;
        double result = 0;
        builder.row("0",String.format("%.3f",currentX),String.format("%.3f",function.apply(currentX)), result +"");
        for (int i = 1; i < n + 1; i++) {
            previousFun = function.apply(currentX);
            if(previousFun.isInfinite())
                return null;
            currentX += h;
            result += previousFun*h;
            builder.row(i+"",String.format("%.3f",currentX),String.format("%.3f",function.apply(currentX)), String.format("%.3f",result));
        }
        if(isNeedToPrint){
            System.out.println(builder.getTable());
        }
        return result;
    }
}
