package lab2.corrections;

import Abdullin_367039.lab2.corrections.Value;
import Abdullin_367039.lab2.equations.Equation;
import Abdullin_367039.lab2.equations.Transendental;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.analysis.solvers.PolynomialSolver;

import java.math.BigDecimal;
import java.util.Collections;

public interface Refinement {
  PolynomialSolver POLYNOMIAL_SOLVER = new LaguerreSolver();
  BisectionSolver BISECTION_SOLVER = new BisectionSolver();
  String NEWTON = "1";
  String SIMPLE_ITERATION = "2";
  String HALF_DIVISION = "3";
  String NO_ROOTS_ON_LINE = "> Нет корней на отрезке!";
  String MORE_THAN_ONE_ROOT = "> На интервале несколько корней!";

  Abdullin_367039.lab2.corrections.Value solve(Equation equation, double[] line, BigDecimal eps);

  default Value prepareAnswer(BigDecimal res, double[] line) throws RuntimeException {
    var dV = res.doubleValue();
    if (!(((Double.compare(line[0], dV) <= 0)) && (Double.compare(line[1], dV) >= 0))) {
      throw new RuntimeException(NO_ROOTS_ON_LINE);
    }
    return () -> res;
  }

  default double throwIfMoreThanOneRoot(double[] line, Equation equation) {
    if (equation instanceof Transendental) {
      UnivariateFunction function = x -> equation.apply(BigDecimal.valueOf(x)).doubleValue();
      return BISECTION_SOLVER.solve(100, function, line[0], line[1]);
    } else {
      var k = equation.getKoef();
      Collections.reverse(k);
      var eq = new PolynomialFunction(k.stream().mapToDouble(BigDecimal::doubleValue).toArray());
      try {
        return POLYNOMIAL_SOLVER.solve(100, eq, line[0], line[1]);
      } catch (Exception e) {
        throw new RuntimeException(MORE_THAN_ONE_ROOT);
      }
    }
  }
}
