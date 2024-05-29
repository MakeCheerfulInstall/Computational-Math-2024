package lab2.corrections;

import Abdullin_367039.lab2.corrections.Refinement;
import Abdullin_367039.lab2.corrections.Value;
import Abdullin_367039.lab2.equations.Equation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HalfDivisionRefinement implements Refinement {

  @Override
  public String toString() {
    return "Half Division";
  }

  @Override
  public Value solve(Equation equation, double[] line, BigDecimal eps) {
    BigDecimal left = BigDecimal.valueOf(line[0]), right = BigDecimal.valueOf(line[1]), x;
    var f = equation.getFunction();
    do {
      x = left.add(right).divide(BigDecimal.valueOf(2), 20, RoundingMode.HALF_UP);
      if (f.apply(left).multiply(f.apply(x)).signum() == 1) {
        left = x;
      } else {
        right = x;
      }
    } while (f.apply(x).abs().compareTo(eps) > 0 && left.subtract(right).abs().compareTo(eps) > 0);

    return prepareAnswer(x, line);
  }
}
