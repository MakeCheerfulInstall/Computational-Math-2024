package lab2.corrections;

import Abdullin_367039.lab2.corrections.Refinement;
import Abdullin_367039.lab2.corrections.Value;
import Abdullin_367039.lab2.equations.Equation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class SimpleIterationRefinement implements Refinement {
  private static final String METHOD_ERROR = "> Условие сходимости не выполняется";

  @Override
  public String toString() {
    return "Simple Iteration";
  }

  @Override
  public Value solve(Equation eq, double[] line, BigDecimal eps) {
    BigDecimal x_0 = BigDecimal.valueOf(line[getIndexMaxDFValueInLine(line, eq)]);

    Function<BigDecimal, BigDecimal> recurrent =
        getRecurrent(eq, getDifferentiate(eq.getFunction(), x_0.doubleValue()));

    BigDecimal s = getDifferentiate(recurrent, line[0]);
    BigDecimal e = getDifferentiate(recurrent, line[1]);

    if (s.compareTo(BigDecimal.ONE) > 0 && e.compareTo(BigDecimal.ONE) > 0) {
      throw new RuntimeException(METHOD_ERROR);
    }

    return prepareAnswer(applyAlgorithm(x_0, recurrent, eps), line);
  }

  private BigDecimal applyAlgorithm(
      BigDecimal start, Function<BigDecimal, BigDecimal> recurrent, BigDecimal eps) {

    BigDecimal res;
    do {
      res = start;
      start = recurrent.apply(start);
    } while (res.subtract(start).abs().compareTo(eps) > 0);

    return start;
  }

  private int getIndexMaxDFValueInLine(double[] line, Equation equation) {
    if (getDifferentiate(equation.getFunction(), line[0])
            .compareTo(getDifferentiate(equation.getFunction(), line[1]))
        > 0) {
      return 0;
    } else {
      return 1;
    }
  }

  private Function<BigDecimal, BigDecimal> getRecurrent(Equation equation, BigDecimal max) {
    BigDecimal lambda = BigDecimal.ONE.divide(max, 20, RoundingMode.HALF_UP).negate();
    return ((value) -> equation.apply(value).multiply(lambda).add(value));
  }

  private BigDecimal getDifferentiate(Function<BigDecimal, BigDecimal> f, double value) {
    var delta = BigDecimal.valueOf(1E-6);
    var big = BigDecimal.valueOf(value);
    return f.apply(big.add(delta)).subtract(f.apply(big)).divide(delta, 8, RoundingMode.HALF_UP);
  }
}
