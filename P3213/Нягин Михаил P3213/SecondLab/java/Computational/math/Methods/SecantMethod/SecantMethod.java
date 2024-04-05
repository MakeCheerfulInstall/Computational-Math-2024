package Computational.math.Methods.SecantMethod;

import Computational.math.Functions.Functions;
import Computational.math.Methods.AbstractMethod;
import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.function.BinaryOperator;

public class SecantMethod extends AbstractMethod {
    private double a;
    private double b;
    private final float epsilon;
    private final Functions functions;
    //for beautiful tables:D
    private JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
    private MonospaceTable table;


    public SecantMethod(double a, double b, float epsilon, int numberOfChosenFunction) {
        super("Метод секущих",new Functions(numberOfChosenFunction).getFunction());
        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
        this.functions = new Functions(numberOfChosenFunction);
    }

    public void solve() {
        printMethodName();
        System.out.println("Для функции: ");functions.printFunction();
        BinaryOperator<Double> calculateNextX = (xCurrent,xPrevious) -> {
            double divider = functions.getValueOfChosenFunction(xCurrent) - functions.getValueOfChosenFunction(xPrevious);
            double divisible = xCurrent - xPrevious;
            return xCurrent - (divisible/divider)*functions.getValueOfChosenFunction(xCurrent);
        };
        try {
            builder.columns("№", "x_i-1", "x_i", "x_i+1", "f(x_i+1)", "|x_i+1-x_i|");
            int iteration = 0;
            double xCurrent=b,xPrevious=a,xNext = b;
            double fNext;
            do{
                if(iteration != 0){
                    xPrevious = xCurrent;
                    xCurrent = xNext;
                }
                xNext = calculateNextX.apply(xCurrent,xPrevious);
                fNext = functions.getValueOfChosenFunction(xNext);
                if(iteration > 200){
                    System.err.println("Нет корней на данном промежутке");
                    break;
                }
                iteration++;
                builder.row(iteration+"",String.format("%.3f",xPrevious),String.format("%.3f",xCurrent),String.format("%.3f",xNext),String.format("%.3f",fNext),String.format("%.3f",Math.abs(xNext-xCurrent)));
            }while(Math.abs(xNext - xCurrent) > epsilon);

            table = builder.getTable();
            System.out.println(table.toStringHorizontal());
        } catch (MalformedTableException e) {
            System.err.println(e.getMessage());
        }

    }
}
