public class GaussMethod {
    private final int n;
    private double[][] matrix;

    private double determinant;


    public GaussMethod(int n, double[][] matrix) {
        this.n = n;
        this.matrix = matrix;
    }

    public void getDiscrepancy(double[][] matrix, double[] x, int n) {
        double[] discrepancy = new double[n];

        for (int i = 0; i < n; i++) {
            double r = matrix[i][n];
            for (int j = 0; j < n; j++) {
                r -= matrix[i][j] * x[j];
            }

            discrepancy[i] = r;
        }

        for (double i : discrepancy) System.out.print(i + " ");

    }


    public void getDeterminant(double[][] matrix, double determinant) {
        determinant *= matrix[0][0];
        for (int i = 1; i < n; i++) {
            determinant *= matrix[i][i];
            if (determinant == 0) {
                System.out.println("Определитель равен нулю. Система имеет бесконечное количество решений.");
                break;
            }
        }
        System.out.println("Определитель = " + determinant);
    }

    public void calculate() {
        double determinant = 1;
        double[][] matrix2 = new double[n][n + 1];
        matrix2 = matrix;
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] == 0) {
                for (int p = i + 1; p < n; p++) {
                    if (matrix[p][i] != 0) {
                        double[] temp = matrix[i];
                        matrix[i] = matrix[p];
                        matrix[p] = temp;
                        determinant *= -1;
                        break;
                    }
                }
            }

            // Приведение к треугольному виду
            for (int j = i + 1; j < n; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k < n + 1; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }

        System.out.println("Треугольная матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.printf("%.2f", matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        getDeterminant(matrix, determinant);

        // Решение системы обратным ходом

        for (int i = n - 1; i >= 0; i--) {
            x[i] = matrix[i][n];
            for (int j = i + 1; j < n; j++) {
                x[i] -= matrix[i][j] * x[j];
            }
            x[i] /= matrix[i][i];
        }

        // Вывод результата
        System.out.println("Вектор неизвестных:");
        for (int i = 0; i < n; i++) {
            System.out.print("x" + (i + 1) + " = ");
            System.out.printf("%.2f", x[i]);
            System.out.println();
        }

        System.out.println("Вектор невязки:");
        getDiscrepancy(matrix2, x, n);

    }
}
