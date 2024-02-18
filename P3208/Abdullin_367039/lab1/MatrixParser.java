package lab1;

import java.util.List;

public class MatrixParser {
  public double[][] parseMatrix(List<String> lines, int matrixSize) {
    return lines.stream().map(line -> getDigits(line, matrixSize)).toArray(double[][]::new);
  }

  private double[] getDigits(String line, int matrixSize) {
    String[] s = line.split(" = ");
    if (s.length != 2) throw new RuntimeException("Incorrect input");

    String[] s1 = s[0].split(" ");
    if (s1.length != matrixSize) throw new RuntimeException("Incorrect input");

    String value = s[1];
    double[] a = new double[s1.length + 1];
    for (int i = 0; i < s1.length; i++) {
      try {
        a[i] = Double.parseDouble(s1[i]);
      } catch (NumberFormatException e) {
        throw new RuntimeException("Incorrect input");
      }
    }
    try {
      a[s1.length] = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new RuntimeException("Incorrect input");
    }

    return a;
  }
}
