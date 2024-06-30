package lab2.solution;

public class GaussMatrix {
    private final int SIZE;
    private final double[][] matrix;
    private final double[] f;

    public GaussMatrix(double[][] matrix, double[] f) {
        this.matrix = matrix.clone();
        for(int i = 0; i < matrix.length; i++) {
            this.matrix[i] = matrix[i].clone();
        }
        this.f = f.clone();
        this.SIZE = matrix.length;
    }

    public double[] solveMatrix() {
        if (noZeroColumns()) {
            for (int i = 0; i < SIZE; i++) {
                if (getIndexOfMaxInColumn(i) == i) {
                    for (int j = i + 1; j < SIZE; j++) {
                        matrix[i][j] /= matrix[i][i];
                    }
                    f[i] /= matrix[i][i];
                    matrix[i][i] = 1;

                    for (int j = i + 1; j < SIZE; j++) {
                        for (int k = i + 1; k < SIZE; k++) {
                            matrix[j][k] -= matrix[i][k] * matrix[j][i];
                        }
                        f[j] -= matrix[j][i] * f[i];
                        matrix[j][i] = 0;
                    }
                } else {
                    int index = getIndexOfMaxInColumn(i);
                    swapInMatrix(index, i);
                    swapInArray(index, i);
                    i--;
                }
            }
            for (int i = SIZE - 2; i >= 0; i--) {
                for (int j = SIZE - 1; j > i; j--) {
                    f[i] -= matrix[i][j] * f[j];
                }
            }
        } else {
            System.out.println("There is zero-column.");
            return null;
        }
        return f;
    }

    private boolean noZeroColumns() {
        for (int j = 0; j < SIZE; j++) {
            int counter = 0;
            for (double[] doubles : matrix) {
                if (doubles[j] == 0) {
                    counter++;
                }
            }
            if (counter == SIZE) {
                return false;
            }
        }
        return true;
    }

    private int getIndexOfMaxInColumn(int column) {
        int maxIndex = column;
        for (int i = column; i < SIZE; i++) {
            if (Math.abs(matrix[i][column]) > Math.abs(matrix[maxIndex][column])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private void swapInMatrix(int firstIndex, int secondIndex) {
        double[] temp = matrix[firstIndex];
        matrix[firstIndex] = matrix[secondIndex];
        matrix[secondIndex] = temp;
    }

    private void swapInArray(int firstIndex, int secondIndex) {
        double temp = f[firstIndex];
        f[firstIndex] = f[secondIndex];
        f[secondIndex] = temp;
    }
}
