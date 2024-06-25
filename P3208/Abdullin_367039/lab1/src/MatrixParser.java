package lab1.src;

import java.math.BigDecimal;
import java.util.List;

public class MatrixParser {

  public BigDecimal[][] parseMatrix(List<String> lines, int matrixSize) {
    return lines.stream().map(line -> getDigits(line, matrixSize)).toArray(BigDecimal[][]::new);
  }

  private BigDecimal[] getDigits(String line, int matrixSize) {
    String[] s = line.split(" = ");
    if (s.length != 2) throw new RuntimeException("Incorrect input, should be `a_1 ... a_n = b`");

    String[] s1 = s[0].split(" ");
    if (s1.length != matrixSize)
      throw new RuntimeException("Count of row elements not equals size");

    String value = s[1];
    BigDecimal[] a = new BigDecimal[s1.length + 1];
    for (int i = 0; i < s1.length; i++) {
      try {
        double val = Double.parseDouble(s1[i].replace(',', '.'));
        a[i] = new BigDecimal(String.format("%.3f", val).replace(',', '.'));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Incorrect input " + s1[i]);
      }
    }
    try {
      double val = Double.parseDouble(value.replace(',', '.'));
      a[s1.length] = new BigDecimal(String.format("%.3f", val).replace(',', '.'));
    } catch (NumberFormatException e) {
      throw new RuntimeException("Incorrect input " + value);
    }

    return a;
  }
}
