package Computational.math.ApproximationMethods;

import Computational.math.FunctionalTable;
import Computational.math.SimpleIteration;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.function.Function;

public class CubicApproximation extends AbstractMethod {
    public CubicApproximation() {
        super("Кубическая",MethodName.CubicApproximation);
    }

    @Override
    public Function<Double, Double> apply(FunctionalTable data) throws MalformedTableException,SingularMatrixException {
        var table = data.getTable();

        double x_sum = 0, x2_sum = 0, x3_sum = 0, x4_sum = 0, x5_sum = 0, x6_sum = 0,
                y_sum = 0, xy_sum = 0, x2y_sum = 0, x3y_sum = 0;

        for (int i = 0; i < table.length; i++) {
            x_sum += table[i][0];
            x2_sum += Math.pow(table[i][0], 2);
            x3_sum += Math.pow(table[i][0], 3);
            x4_sum += Math.pow(table[i][0], 4);
            x5_sum += Math.pow(table[i][0], 5);
            x6_sum += Math.pow(table[i][0], 6);
            y_sum += table[i][1];
            xy_sum += table[i][0] * table[i][1];
            x2y_sum += Math.pow(table[i][0], 2) * table[i][1];
            x3y_sum += Math.pow(table[i][0], 3) * table[i][1];
        }

        /**
         * n, sx, sx^2, sx^3 = sy
         * sx, sx^2, sx^3, sx^4 = sxy
         * sx^2, sx^3, sx^4, sx^5 = sx^2*y
         * sx^3, sx^4, sx^5, sx^6 = sx^3*y
         */
        var matrix = new double[][]{
                {(double) table.length, x_sum, x2_sum, x3_sum},
                {x_sum, x2_sum, x3_sum, x4_sum},
                {x2_sum, x3_sum, x4_sum, x5_sum},
                {x3_sum, x4_sum, x5_sum, x6_sum}
        };

        var constants = new double[]{y_sum, xy_sum, x2y_sum, x3y_sum};
            double[] solution = solveLinearSystem(matrix, constants);
//        SimpleIteration simpleIteration = new SimpleIteration(matrix,constants,0.0001);
//        Double[] solution = simpleIteration.solve();
            Function<Double, Double> P = x -> Math.pow(x, 3) * solution[0] + Math.pow(x, 2) * solution[1] +
                    x * solution[2] + solution[3];

            printResult(data, P);
            return P;

    }



}


