package lab3.solvers;

import Abdullin_367039.lab3.domains.Integral;
import Abdullin_367039.lab3.solvers.IntegralSolver;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrapezoidIntegralSolver implements IntegralSolver {
  @Override
  public Result solve(Integral integral) {
    throwIfNotConverge(integral);

    BigDecimal common, duble;
    do {
      common = applyAlgorithm(integral);
      integral.setN(integral.getN() * 2);
      duble = applyAlgorithm(integral);
    } while (integral.getEps().compareTo(common.subtract(duble).abs()) < 0);

    return prepareResult(duble, integral.getN());
  }

  private BigDecimal applyAlgorithm(Integral integral) {
    BigDecimal x = integral.getLeft(), h = getH(integral), sum = BigDecimal.ZERO;
    var f = integral.getFunction();
    x = x.add(h);
    for (int i = 0; i < integral.getN() - 1; i++) {
      sum = sum.add(f.apply(x));
      x = x.add(h);
    }

    return h.divide(BigDecimal.valueOf(2), 20, RoundingMode.HALF_UP)
        .multiply(
            f.apply(integral.getLeft())
                .add(f.apply(integral.getRight()))
                .add(BigDecimal.valueOf(2).multiply(sum)));
  }

  @Override
  public String toString() {
    return "Метод трапеций";
  }
}
