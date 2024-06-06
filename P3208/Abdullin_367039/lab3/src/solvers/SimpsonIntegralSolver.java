package lab3.solvers;

import Abdullin_367039.lab3.domains.Integral;
import Abdullin_367039.lab3.solvers.IntegralSolver;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SimpsonIntegralSolver implements IntegralSolver {
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
    BigDecimal x = integral.getLeft(), h = getH(integral);
    List<BigDecimal> values = new ArrayList<>();
    var f = integral.getFunction();
    x = x.add(h);

    for (int i = 1; i < integral.getN(); i++) {
      values.add(f.apply(x));
      x = x.add(h);
    }

    BigDecimal odd = BigDecimal.ZERO;
    for (int i = 1; i < values.size(); i += 2) {
      odd = odd.add(values.get(i));
    }

    BigDecimal even = BigDecimal.ZERO;
    for (int i = 0; i < values.size(); i += 2) {
      even = even.add(values.get(i));
    }

    return h.divide(BigDecimal.valueOf(3), 20, RoundingMode.HALF_UP)
        .multiply(
            integral
                .getLeft()
                .add(integral.getRight())
                .add(BigDecimal.valueOf(4).multiply(even))
                .add(BigDecimal.valueOf(2).multiply(odd)));
  }

  @Override
  public String toString() {
    return "Метод Симпсона";
  }
}
