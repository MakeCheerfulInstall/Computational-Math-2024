package Computational.math.Methods.SimpleIteration;

import Computational.math.Functions.Functions;
import Computational.math.Methods.AbstractMethod;
import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

public class SimpleIteration extends AbstractMethod {
    private double a;
    private double b;
    private final float epsilon;
    private final Functions function;
    //for beautiful tables:D
    private JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
    private MonospaceTable table;

    public SimpleIteration(double a, double b, float epsilon, int numberOfChosenFunction) {
        super("Методы простых итераций", new Functions(numberOfChosenFunction).getFunction());
        this.epsilon = epsilon;
        this.a = a;
        this.b = b;
        this.function = new Functions(numberOfChosenFunction);
    }

    @Override
    public void solve() {
        try {
            super.printMethodName();
            System.out.println("Для функции: ");
            function.printFunction();


            builder.columns("№", "x", "x_next", "fi", "F(x_next)", "|a-b|");

            double fa = function.getDifValueFunction(a);
            double fb = function.getDifValueFunction(b);
            double methodLambda = -1 / Math.max(fa, fb);
            double leftInterval = methodLambda * fa + 1;
            double rightInterval = methodLambda * fb + 1;
            double xi, fi, xNext, fNext, iter = 0;
            if (!(leftInterval < 1 && rightInterval < 1)) {
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tУсловие сходимости не выполнено!");
                return;
            }
            System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tУсловие сходимости выполнено!");
            xi = a;
            xNext = methodLambda * function.getValueOfChosenFunction(xi) + xi;
            do {
                fi = methodLambda * function.getValueOfChosenFunction(xNext) + xNext;
                if (iter != 0) {
                    xi = xNext;
                    xNext = fi;
                }
                fNext = function.getValueOfChosenFunction(xNext);
                iter++;
                builder.row(iter + "", String.format("%.3f", xi), String.format("%.3f", xNext), String.format("%.3f", fi), String.format("%.3f", fNext), String.format("%.3f", Math.abs(xNext - xi)));
                if (iter > 300) {
                    System.err.println("Нет корней");
                }
            } while (Math.abs(xNext - xi) > epsilon);
            table = builder.getTable();
            System.out.println(table.toStringHorizontal());
        } catch (MalformedTableException e) {
            System.err.println(e.getMessage());
        }
    }
}
