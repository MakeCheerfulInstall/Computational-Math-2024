package lab3.solvers;

import Abdullin_367039.lab3.domains.Integral;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.TrapezoidIntegrator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface IntegralSolver {
  Result solve(Integral integral);

  default void throwIfNotConverge(Integral integral) {
    TrapezoidIntegrator i = new TrapezoidIntegrator();
    UnivariateFunction function =
            (x) -> integral.getFunction().apply(BigDecimal.valueOf(x)).doubleValue();

    i.integrate(10000, function, integral.getLeft().doubleValue(), integral.getRight().doubleValue());
  }

  default Result prepareResult(BigDecimal result, int endN) {
    return new Result(result, endN);
  }

  default BigDecimal getH(Integral integral) {
    return integral
        .getRight()
        .subtract(integral.getLeft())
        .divide(BigDecimal.valueOf(integral.getN()), 20, RoundingMode.HALF_UP);
  }

  class Result {
    private final BigDecimal result;
    private final int endN;

    public BigDecimal getResult() {
      return result;
    }

    public int getEndN() {
      return endN;
    }

    public Result(BigDecimal result, int endN) {
      this.result = result;
      this.endN = endN;
    }
  }
}
