package Computational.math.ApproximationMethods;

import Computational.math.CalculatorUtils;
import Computational.math.FunctionalTable;
import lombok.Getter;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.netirc.library.jtables.JTablesBuilder;
import org.netirc.library.jtables.exception.MalformedTableException;
import org.netirc.library.jtables.table.MonospaceTable;

import java.util.ArrayList;
import java.util.function.Function;

public abstract class AbstractMethod {
    private final String name;
    @Getter
    private final MethodName methodName;
    private double R2;
    public AbstractMethod(String name,MethodName methodName){
        this.name = name;
        this.methodName = methodName;
    }
    public abstract Function<Double,Double> apply(FunctionalTable data) throws MalformedTableException;
    public void printMethodName(){
        for (int i = 0; i < 200; i++) {
            if(i == 50){
                System.out.print("\t"+name+"\t");
            }
            System.out.print('*');
        }
        System.out.println();
    }

    public String getName() {
        return name;
    }
    public String roundDouble(Double target){
        return String.format("%.3f",target);
    }
    public double[] solveLinearSystem(double[][] coefficients, double[] constants) {
        DecompositionSolver solver = new LUDecomposition(new Array2DRowRealMatrix(coefficients)).getSolver();
        return solver.solve(new ArrayRealVector(constants)).toArray();
    }
    public void printResult(FunctionalTable functionalTable, Function<Double,Double> P) throws MalformedTableException{

        JTablesBuilder<MonospaceTable> builder = MonospaceTable.build();
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        ArrayList<String> PApproximation = new ArrayList<>();
        ArrayList<String> epsilon = new ArrayList<>();
        var S = 0d;
        x.add("X");
        y.add("Y");
        PApproximation.add("P");
        epsilon.add("ε");

        var table = functionalTable.getTable();
        for (int i = 0; i < table[0].length; i++) {
            x.add(roundDouble(table[0][i]));
            y.add(roundDouble(table[1][i]));
            var currentP = P.apply(table[0][i]);
            var currentEpsilon = currentP-table[1][i];
            PApproximation.add(roundDouble(currentP));
            epsilon.add(roundDouble(currentEpsilon*currentEpsilon));
            S += Math.pow(currentP-table[1][i],1);
        }
        builder.columns(x.toArray(new String[0]));
        builder.row(y);
        builder.row(PApproximation);
        builder.row(epsilon);
        System.out.println(builder.getTable().toStringHorizontal());
        System.out.println("S= " + roundDouble(S));
        System.out.println("σ = " + roundDouble(Math.sqrt(S/table[0].length)));
        this.R2 = CalculatorUtils.coefficientOfDetermination(functionalTable,P);
        System.out.println("R^2="+roundDouble(this.R2));
    }

    public double getR2() {
        if(R2 != 0d)
            return R2;
        return 0d;
    }


}
