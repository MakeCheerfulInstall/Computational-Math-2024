package lab1.src;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussExecutor {

  public void solve(BigDecimal[][] matrix) {
    var m = rightMethod(matrix);
    if (m != null) {
      var vectors = backMethod(m);
      printTriangleMatrix(m, "> Треугольная матрица:");
      System.out.printf("> Детерминант: %.5f\n\n", getDeterminate(m).doubleValue());
      printVectors(vectors, "> Векторы неизвестных:");
      printVectors(getResidualVectors(matrix, vectors), "> Векторы невязок:");
    }
  }

  private BigDecimal[][] rightMethod(BigDecimal[][] m) {
    for (int i = 0; i < m.length; i++) {
      if (m[i][i].compareTo(BigDecimal.ZERO) == 0) {
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
        BigDecimal factor = m[j][i].divide(m[i][i], 20, RoundingMode.HALF_UP);
        for (int k = 0; k <= m.length; k++) {
          m[j][k] = m[j][k].subtract(m[i][k].multiply(factor));
        }
      }
      printTriangleMatrix(m, "> Remove i = " + (i + 1));
    }
    return m;
  }

  private int findNonZeroDiagonalElementRow(BigDecimal[][] m, int col) {
    for (int i = col + 1; i < m.length; i++) {
      if (m[i][col].compareTo(BigDecimal.ZERO) != 0) {
        return i;
      }
    }
    return -1;
  }

  private void swapRows(BigDecimal[][] m, int row1, int row2) {
    BigDecimal[] temp = m[row1];
    m[row1] = m[row2];
    m[row2] = temp;
  }

  private BigDecimal[] backMethod(BigDecimal[][] m) {
    BigDecimal[] solution = new BigDecimal[m.length];
    for (int i = m.length - 1; i >= 0; i--) {
      solution[i] = m[i][m.length];
      for (int j = i + 1; j < m.length; j++) {
        solution[i] = solution[i].subtract(m[i][j].multiply(solution[j]));
      }
      BigDecimal res = solution[i].divide(m[i][i], 20, RoundingMode.HALF_UP);
      solution[i] = res;
    }
    return solution;
  }

  private void printVectors(BigDecimal[] vs, String message) {
    System.out.println(message);
    for (int i = 0; i < vs.length; i++) {
      System.out.printf("x%s = %.5f\n", i + 1, vs[i].doubleValue());
    }
    System.out.println();
  }

  private BigDecimal getDeterminate(BigDecimal[][] m) {
    BigDecimal determinate = BigDecimal.ONE;
    for (int i = 0; i < m.length; i++) {
      determinate = determinate.multiply(m[i][i]);
    }
    return determinate;
  }

  private void printTriangleMatrix(BigDecimal[][] m, String mes) {
    System.out.println(mes);
    for (BigDecimal[] doubles : m) {
      for (int j = 0; j <= m.length; j++) {
        if (j == m.length) {
          System.out.print("= " + String.format("%.5f", doubles[j].doubleValue()));
        } else {
          System.out.print(String.format("%.5f", doubles[j].doubleValue()) + " ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  private int calculateRank(BigDecimal[][] matrix) {
    int rowCount = matrix.length;
    int colCount = matrix[0].length;

    int rank = 0;
    boolean[] rowMarked = new boolean[rowCount];

    for (int col = 0; col < colCount; col++) {
      boolean found = false;
      for (int row = 0; row < rowCount && !found; row++) {
        if (!rowMarked[row] && (matrix[row][col].compareTo(BigDecimal.ZERO) != 0)) {
          rank++;
          rowMarked[row] = true;
          found = true;

          for (int k = row + 1; k < rowCount; k++) {
            BigDecimal factor = matrix[k][col].divide(matrix[row][col], 20, RoundingMode.HALF_UP);
            for (int j = col; j < colCount; j++) {
              matrix[k][j] = matrix[k][j].subtract(matrix[row][j].multiply(factor));
            }
          }
        }
      }
    }

    return rank;
  }

  private BigDecimal[] getResidualVectors(BigDecimal[][] m, BigDecimal[] solutions) {
    var res = new BigDecimal[solutions.length];
    for (int i = 0; i < m.length; i++) {
      BigDecimal sum = BigDecimal.ZERO;
      for (int j = 0; j < m.length; j++) {
        sum = sum.add(m[i][j].multiply(solutions[j]));
      }
      res[i] = m[i][m.length].subtract(sum);
    }
    return res;
  }
}
