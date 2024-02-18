package lab1;

import java.text.DecimalFormat;

public class GaussExecutor {
  private final DecimalFormat FORMAT = new DecimalFormat("#.##########");

  public void solve(double[][] matrix) {
    var m = rightMethod(matrix);
    if (m != null) {
      var vectors = backMethod(m);
      printTriangleMatrix(m, "> Треугольная матрица:");
      System.out.printf("> Детерминант: %s\n\n", getDeterminate(m));
      printVectors(vectors, "> Векторы неизвестных:");
      printVectors(getResidualVectors(matrix, vectors), "> Векторы невязок:");
    }
  }

  private double[][] rightMethod(double[][] m) {
    for (int i = 0; i < m.length; i++) {
      if (Double.compare(m[i][i], 0) == 0) {
        int swapRow = findNonZeroDiagonalElementRow(m, i);
        if (swapRow != -1) {
          swapRows(m, i, swapRow);
        } else {
          int rankCoefficients = calculateRank(m);
          System.out.println("> Детерминант: 0");
          if (rankCoefficients < m.length) {
            System.out.println("> Векторы неизвестных: СЛАУ имеет бесконечное количество решений");
          } else {
            System.out.println("> Векторы неизвестных: СЛАУ не имеет решений");
          }
          return null;
        }
      }
      for (int j = i + 1; j < m.length; j++) {
        double factor = m[j][i] / m[i][i];
        for (int k = 0; k <= m.length; k++) {
          m[j][k] -= m[i][k] * factor;
        }
      }
      printTriangleMatrix(m, "> Remove i = " + (i + 1));
    }
    return m;
  }

  private int findNonZeroDiagonalElementRow(double[][] m, int col) {
    for (int i = col + 1; i < m.length; i++) {
      if (m[i][col] != 0) {
        return i;
      }
    }
    return -1;
  }

  private void swapRows(double[][] m, int row1, int row2) {
    double[] temp = m[row1];
    m[row1] = m[row2];
    m[row2] = temp;
  }

  private double[] backMethod(double[][] m) {
    double[] solution = new double[m.length];
    for (int i = m.length - 1; i >= 0; i--) {
      solution[i] = m[i][m.length];
      for (int j = i + 1; j < m.length; j++) {
        solution[i] -= m[i][j] * solution[j];
      }
      double res = solution[i] / m[i][i];
      solution[i] = formatDouble(res, FORMAT);
    }
    return solution;
  }

  private void printVectors(double[] vs, String message) {
    System.out.println(message);
    for (int i = 0; i < vs.length; i++) {
      System.out.printf("x%s = %s\n", i + 1, vs[i]);
    }
    System.out.println();
  }

  private double getDeterminate(double[][] m) {
    double determinate = 1;
    for (int i = 0; i < m.length; i++) {
      determinate *= m[i][i];
    }
    return formatDouble(determinate, FORMAT);
  }

  private void printTriangleMatrix(double[][] m, String mes) {
    System.out.println(mes);
    for (double[] doubles : m) {
      for (int j = 0; j <= m.length; j++) {
        if (j == m.length) {
          System.out.print("= " + formatDouble(doubles[j], FORMAT));
        } else {
          System.out.print(formatDouble(doubles[j], FORMAT) + " ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  private double formatDouble(double value, DecimalFormat format) {
    String form = format.format(value).replace(',', '.');
    double n = Double.parseDouble(form);
    if (Math.abs(value - n) < 0.000000001) {
      return n;
    }
    return value;
  }

  private int calculateRank(double[][] matrix) {
    int rowCount = matrix.length;
    int colCount = matrix[0].length;

    int rank = 0;
    boolean[] rowMarked = new boolean[rowCount];

    for (int col = 0; col < colCount; col++) {
      boolean found = false;
      for (int row = 0; row < rowCount && !found; row++) {
        if (!rowMarked[row] && Math.abs(matrix[row][col]) < 0.0000001) {
          rank++;
          rowMarked[row] = true;
          found = true;

          for (int k = row + 1; k < rowCount; k++) {
            double factor = matrix[k][col] / matrix[row][col];
            for (int j = col; j < colCount; j++) {
              matrix[k][j] -= matrix[row][j] * factor;
            }
          }
        }
      }
    }

    return rank;
  }

  private double[] getResidualVectors(double[][] m, double[] solutions) {
    var res = new double[solutions.length];
    for (int i = 0; i < m.length; i++) {
      double sum = 0;
      for (int j = 0; j < m.length; j++) {
        sum += m[i][j] * solutions[j];
      }
      double value = formatDouble(m[i][m.length] - sum, FORMAT);
      res[i] = value;
    }
    return res;
  }
}
