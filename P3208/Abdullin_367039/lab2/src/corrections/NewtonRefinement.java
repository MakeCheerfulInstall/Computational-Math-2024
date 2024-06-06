package lab2.corrections;

import Abdullin_367039.lab2.corrections.Refinement;
import Abdullin_367039.lab2.corrections.Value;
import Abdullin_367039.lab2.equations.Equation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class NewtonRefinement implements Refinement {
  private static final int SCALE = 40;

  public BigDecimal solve(
      Function<BigDecimal, BigDecimal> function,
      Function<BigDecimal, BigDecimal> derivative,
      BigDecimal eps,
      BigDecimal x_0) {
    BigDecimal prev;
    do {
      prev = x_0;
      x_0 =
          x_0.subtract(
              function.apply(x_0).divide(derivative.apply(x_0), SCALE, RoundingMode.HALF_UP));
    } while (function.apply(prev).abs().compareTo(eps) > 0);

    return x_0;
  }

  @Override
  public String toString() {
    return "Newton Refinement";
  }

  @Override
  public Value solve(Equation eq, double[] line, BigDecimal eps)
      throws RuntimeException {

    BigDecimal x_0 = BigDecimal.valueOf(line[0]);
    Function<BigDecimal, BigDecimal> df = ((BigDecimal b) -> getDifferentiate(eq, b.doubleValue()));
    return prepareAnswer(solve(eq.getFunction(), df, eps, x_0), line);
  }

  private BigDecimal getDifferentiate(Equation equation, double value) {
    var delta = BigDecimal.valueOf(1E-6);
    var big = BigDecimal.valueOf(value);
    return equation
        .apply(big.add(delta))
        .subtract(equation.apply(big))
        .divide(delta, 8, RoundingMode.HALF_UP);
  }

}
