package lab3.domains;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public class Integral {
  private UnaryOperator<BigDecimal> function;
  private BigDecimal left;
  private BigDecimal right;
  private BigDecimal eps;
  private int n;

  public UnaryOperator<BigDecimal> getFunction() {
    return function;
  }

  public BigDecimal getLeft() {
    return left;
  }

  public BigDecimal getRight() {
    return right;
  }

  public BigDecimal getEps() {
    return eps;
  }

  public int getN() {
    return n;
  }

  public void setFunction(UnaryOperator<BigDecimal> function) {
    this.function = function;
  }

  public void setLeft(BigDecimal left) {
    this.left = left;
  }

  public void setRight(BigDecimal right) {
    this.right = right;
  }

  public void setEps(BigDecimal eps) {
    this.eps = eps;
  }

  public void setN(int n) {
    this.n = n;
  }
}
