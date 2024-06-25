package algo;

import model.Data;
import model.Result;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Math.abs;

public class IterationSolution implements Solution {
    private final MathContext context = new MathContext(3, RoundingMode.HALF_UP);
    private double[][] newMatrix;
    private int permuts;

    public boolean checkDiagonal(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    continue;
                }
                sum += abs(matrix[i][j]);
            }
            if (sum >= abs(matrix[i][i])) {
                return false;
            }
        }
        return true;
    }

    private void permuteMatrix(double[][] matrix, int index) {
        permuts = 0;
        if (index >= matrix.length - 1) {
            if (checkDiagonal(matrix)) {
                newMatrix = new double[matrix.length][matrix.length + 1];

                for (int i = 0; i < matrix.length; i++) {
                    System.arraycopy(matrix[i], 0, newMatrix[i], 0, matrix.length + 1);
                }
            }
        } else {
            for (int i = index; i < matrix.length; i++) {
                var t = matrix[index];
                matrix[index] = matrix[i];
                matrix[i] = t;

                permuteMatrix(matrix, index + 1);

                t = matrix[index];
                matrix[index] = matrix[i];
                matrix[i] = t;
            }
            permuts++;
        }
    }

    @Override
    public Result compute(Data data) {
        if (checkDiagonal(data.matrix())) {
            Result rs = iterationMethod(data.matrix(), data.accuracy());
            presentAnswer(rs);
            return rs;
        }
        permuteMatrix(data.matrix(), 0);
        System.out.println("Было сделано перестановок: " + permuts);

        if (newMatrix != null) {
            System.out.println("Новая матрица после перестановки: ");
            for (double[] matrix : newMatrix) {
                for (int j = 0; j < newMatrix.length + 1; j++) {
                    System.out.print(new BigDecimal(matrix[j], context) + " ");
                }
                System.out.print("\n");
            }
            Result rs = iterationMethod(newMatrix, data.accuracy());
            presentAnswer(rs);
            return rs;
        } else {
            System.out.println("Диагональное преобладание отсутствует");
        }
        return null;
    }

    private Result iterationMethod(double[][] matrix, double eps) {
        var rs = new Result();
        var x = new double[matrix.length];

        double norma, sum, t;
        do {
            ArrayList<Double> esps = new ArrayList<>();
            norma = 0;

            for (int i = 0; i < matrix.length; i++) {
                t = x[i];
                sum = 0;

                for (int j = 0; j < matrix.length; j++) {
                    if (j != i)
                        sum += matrix[i][j] * x[j];
                }
                x[i] = (getRow(matrix)[i] - sum) / matrix[i][i];
                esps.add(abs(x[i] - t));

                if (abs(x[i] - t) > norma) {
                    norma = abs(x[i] - t);
                }
            }
            rs.addIter(x);
            rs.addE(esps);
        } while (norma > eps);

        rs.setResult(x);

        ArrayList<Double> residuals = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            double S = 0;
            for (int j = 0; j < matrix.length; j++) {
                S += matrix[i][j] * x[j];
            }
            residuals.add(S - getRow(matrix)[i]);
        }

        rs.setResiduals(residuals);
        return rs;
    }

    public double[] getRow(double[][] otherMatrix) {
        double[] vector = new double[otherMatrix.length];
        for (int i = 0; i < otherMatrix.length; i++) {
            vector[i] = otherMatrix[i][otherMatrix.length];
        }
        return vector;
    }

    private void presentAnswer(Result rs) {
        System.out.println(rs.getTable());
        System.out.print("Решение системы: ");
        printVector(rs.getResult());
        System.out.print("Вектор невязки: ");
        printVector(rs.getResiduals());
    }

    private void printVector(ArrayList<Double> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + " = " + String.format("%.15f", list.get(i)));
        }
    }
}
