package lab3.domains;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public class Equation {
  private final UnaryOperator<BigDecimal> fun;
  private final String name;

  public Equation(UnaryOperator<BigDecimal> fun, String name) {
    this.fun = fun;
    this.name = name;
  }

  public UnaryOperator<BigDecimal> getFun() {
    return fun;
  }

  @Override
  public String toString() {
    return name;
  }
}
