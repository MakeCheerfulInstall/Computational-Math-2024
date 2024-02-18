package lab1;

import java.text.DecimalFormat;
import java.util.List;

public class MatrixParser {
  private final DecimalFormat FORMAT = new DecimalFormat("#.###");

  public double[][] parseMatrix(List<String> lines, int matrixSize) {
    return lines.stream().map(line -> getDigits(line, matrixSize)).toArray(double[][]::new);
  }

  private double[] getDigits(String line, int matrixSize) {
    String[] s = line.split(" = ");
    if (s.length != 2) throw new RuntimeException("Incorrect input, should be `a_1 ... a_n = b`");

    String[] s1 = s[0].split(" ");
    if (s1.length != matrixSize)
      throw new RuntimeException("Count of row elements not equals size");

    String value = s[1];
    double[] a = new double[s1.length + 1];
    for (int i = 0; i < s1.length; i++) {
      try {
        double val = Double.parseDouble(s1[i].replace(',', '.'));
        a[i] = Double.parseDouble(FORMAT.format(val));
      } catch (NumberFormatException e) {
        throw new RuntimeException("Incorrect input " + s1[i]);
      }
    }
    try {
      a[s1.length] = Double.parseDouble(value.replace(',', '.'));
    } catch (NumberFormatException e) {
      throw new RuntimeException("Incorrect input " + value);
    }

    return a;
  }
}
