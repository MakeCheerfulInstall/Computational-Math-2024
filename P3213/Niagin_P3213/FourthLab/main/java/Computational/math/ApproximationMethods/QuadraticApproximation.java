package Computational.math.ApproximationMethods;

import Computational.math.FunctionalTable;
import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.Function;

public class QuadraticApproximation extends AbstractMethod{

    public QuadraticApproximation() {
        super("Квадратичная аппроксимация",MethodName.QuadraticApproximation);
    }

    @Override
    public Function<Double, Double> apply(FunctionalTable data) throws MalformedTableException {
        var table = data.getTable();
        var x = data.getSumXi();
        var xx=data.getSumXiWithPow(2);
        var xxx=data.getSumXiWithPow(3);
        var xxxx=data.getSumXiWithPow(4);

        var y = data.getSumYi();
        var xy = data.getMultiplyXandYWithPows();
        var xxy = data.getMultiplyXandYWithPows(2,1);

        double n = table[0].length;

        double[][] leftPart = {
                {n,x,xx},{x,xx,xxx},{xx,xxx,xxxx}
        };
        double[] constants = {y,xy,xxy};
        double[] answers = solveLinearSystem(leftPart,constants);

        var a = answers[0];var b=answers[1];var c=answers[2];
        Function<Double,Double> P = xInput -> a+b*xInput+c*Math.pow(xInput,2);

       //todo а как иначе жить -- без sout?
        System.out.println("fi(x) = a + b * x + c * x ** 2\n" +
                "coeffs (a, b, c): ("+roundDouble(answers[0])+","+roundDouble(answers[1])+","+ roundDouble(answers[2])+")");

        return P;
    }
}
