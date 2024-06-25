package lab2.corrections;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class SecantRefinement {
  private static final int SCALE = 20;

  public BigDecimal solve(
      BigDecimal x_0, BigDecimal x_1, BigDecimal eps, Function<BigDecimal, BigDecimal> function) {

    BigDecimal current = x_1, f0, f, prev = x_0;
    int i = 0;
    do {
      System.out.println(i);
      f = function.apply(current);
      f0 = function.apply(x_0);
      System.out.print("x_k-1 = " + prev.doubleValue() + "; ");
      System.out.print("x_k = " + current.doubleValue() + "; ");
      prev = current;
      current =
          current.subtract(
              current
                  .subtract(x_0)
                  .divide(f.subtract(f0), SCALE, RoundingMode.HALF_UP)
                  .multiply(f));
      System.out.print("x_k+1 = " + current.doubleValue() + "; ");
      System.out.print("f(x_k+1) = " + function.apply(current).doubleValue() + "; ");
      System.out.println("abs = " + current.subtract(prev).abs().doubleValue());
      i++;
    } while (f.abs().compareTo(eps) > 0);

    return current;
  }
}
