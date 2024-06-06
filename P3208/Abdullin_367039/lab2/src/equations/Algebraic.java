package lab2.equations;

import Abdullin_367039.lab2.equations.Equation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class Algebraic implements Equation {

  private UnaryOperator<BigDecimal> function;
  private final List<BigDecimal> koef;

  public Algebraic(List<BigDecimal> koef) {
    this.koef = koef;
    initialize();
  }

  private void initialize() {
    function =
        ((value) -> {
          BigDecimal res = BigDecimal.ZERO;
          for (int i = 0; i < koef.size(); i++) {
            res = res.add(koef.get(i).multiply(value.pow(koef.size() - 1 - i)));
          }
          return res;
        });
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
    return new ArrayList<>(koef);
  }

  @Override
  public String toString() {
    var builder = new StringBuilder();

    for (int i = 0; i < koef.size(); i++) {
      var current = koef.get(i);
      if (i == koef.size() - 1) {
        if (current.compareTo(BigDecimal.ZERO) > 0) {
          builder.append(String.format("+ %s ", current.doubleValue()));
        } else if (current.compareTo(BigDecimal.ZERO) < 0) {
          builder.append(String.format(String.format("- %s ", current.abs().doubleValue())));
        }
      } else {
        if (current.compareTo(BigDecimal.ONE) == 0) {
          builder.append(String.format("+ x^{%s} ", koef.size() - 1 - i));
        } else if (current.compareTo(BigDecimal.ONE.negate()) == 0) {
          builder.append(String.format("- x^{%s} ", koef.size() - 1 - i));
        } else if (current.compareTo(BigDecimal.ZERO) > 0) {
          builder.append(String.format("+ %s*x^{%s} ", current.doubleValue(), koef.size() - 1 - i));
        } else if (current.compareTo(BigDecimal.ZERO) < 0) {
          builder.append(
              String.format("- %s*x^{%s} ", current.abs().doubleValue(), koef.size() - 1 - i));
        }
      }
    }
    return builder.toString();
  }
}
