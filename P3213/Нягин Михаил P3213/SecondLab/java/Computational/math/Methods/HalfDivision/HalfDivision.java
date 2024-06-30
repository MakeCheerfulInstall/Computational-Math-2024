package Computational.math.Methods.HalfDivision;

import Computational.math.Functions.Functions;
import Computational.math.Methods.AbstractMethod;
import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;


public class HalfDivision extends AbstractMethod {
        private double a;
        private double b;
        private final float epsilon;
        private final Functions function;
        //for beautiful tables:D
        private JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
        private MonospaceTable table;


    public HalfDivision(double a, double b, float epsilon, int numberOfChosenFunction) {
        super("Метод половинного деления", new Functions(numberOfChosenFunction).getFunction());
        this.a = a;
        this.b = b;
        this.epsilon = epsilon;
        this.function = new Functions(numberOfChosenFunction);
    }
    @Override
    public void solve() {
        try {
            super.printMethodName();
            System.out.println("Для функции: ");function.printFunction();

            builder.columns("№","a","b","x","f(a)","f(b)","f(x)","|a-b|");
            double x;
            int iterationsCounter = 0;
            do {
                x = (a + b) / 2;
                double fa = function.getValueOfChosenFunction(a);
                double fx = function.getValueOfChosenFunction(x);
                if (fa * fx > 0) {
                    a = x;
                } else {
                    b = x;
                }
                iterationsCounter++;
                builder.row("" + iterationsCounter, String.format("%.3f", a), String.format("%.3f", b), String.format("%.3f", x), String.format("%.3f", fa), String.format("%.3f", function.getValueOfChosenFunction(b)), String.format("%.3f", fx), Math.abs(a - b) + "");
                if (iterationsCounter > 300){
                    System.err.println("На данном отрезке нет корней");
                    return;
            }
            } while (Math.abs(a - b) > epsilon || Math.abs(function.getValueOfChosenFunction(x)) > epsilon);
            x = (a + b) / 2;
            builder.row("final",String.format("%.3f",a),String.format("%.3f",b),String.format("%.3f",x),String.format("%.3f", function.getValueOfChosenFunction(a)),String.format("%.3f", function.getValueOfChosenFunction(b)),String.format("%.3f", function.getValueOfChosenFunction(x)),Math.abs(a-b)+"");
            table = builder.getTable();
            System.out.print(table.toStringHorizontal());
        }catch (MalformedTableException e){
            System.err.println("Ошибка при работе с таблицей");
        }
    }

}
