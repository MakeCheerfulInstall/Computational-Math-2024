package lab2.equations;

import Abdullin_367039.lab2.equations.Equation;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;

public class Transendental implements Equation {
  private final UnaryOperator<BigDecimal> function;
  private final String name;

  public Transendental(UnaryOperator<BigDecimal> function, String name) {
    this.function = function;
    this.name = name;
  }

  @Override
  public BigDecimal apply(BigDecimal value) {
    return function.apply(value);
  }

  public UnaryOperator<BigDecimal> getFunction() {
    return function;
  }

  @Override
  public List<BigDecimal> getKoef() {
    return null;
  }

  public String getName() {
    return name;
  }
}
