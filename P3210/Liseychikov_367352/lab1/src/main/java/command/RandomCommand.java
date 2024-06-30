package command;


import Util.ParseUtil;
import algo.IterationSolution;
import algo.Solution;
import model.Data;
import model.Result;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RandomCommand implements Command {
    private final ParseUtil parseUtil = new ParseUtil();
    private final Solution solution = new IterationSolution();
    private final MathContext context = new MathContext(3, RoundingMode.HALF_UP);

    @Override
    public Result execute() {
        int size = parseUtil.parseSize();
        double accuracy = parseUtil.parseAccuracy();
        double[][] matrix = generateMatrix(size);
        printMatrix(matrix);
        Data data = new Data(matrix, accuracy);
        return solution.compute(data);
    }

    private double[][] generateMatrix(int size) {
        double[][] matrix = new double[size][size + 1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length + 1; j++) {
                matrix[i][j] = Math.random() * 50 - 25;
            }
        }
        return matrix;
    }

    private void printMatrix(double[][] matrix) {
        System.out.println("Сгенерированная матрица: ");
        for (double[] vect : matrix) {
            for (int j = 0; j < matrix.length + 1; j++) {
                System.out.print(new BigDecimal(vect[j], context) + " ");
            }
            System.out.print("\n");
        }
    }
}