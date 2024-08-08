package Computational.Math;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.*;

public class EquationSolve {
    public static double solveEquation(UnivariateFunction equation){
        BrentSolver solver = new BrentSolver();

        double solution = solver.solve(100, equation,-1000,1000);

        return solution;
    }

}
