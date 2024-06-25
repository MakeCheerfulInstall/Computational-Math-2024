package lab6.solver;


public class RungeKutta implements ISolver {
    @Override
    public double[][] solve(double start, double end, double y0, double h, double e, FuncXY funcXY) {
        int n = (int) ((end - start) / h + 1);
        double[][] result = new double[n][3];
        result[0][0] = start;
        result[0][1] = y0;
        result[0][2] = funcXY.solve(result[0][0], result[0][1]);

        for (int i = 1; i < n; i++) {
            result[i][0] = result[i - 1][0] + h;
            result[i][1] = calcY(result[i - 1][0], result[i - 1][1], h, funcXY);
            result[i][2] = funcXY.solve(result[i][0], result[i][1]);
        }

        return result;
    }

    private double calcY(double x, double y, double h, FuncXY funcXY) {
        double k1 = h * funcXY.solve(x, y);
        double k2 = h * funcXY.solve(x + h/2, y + k1/2);
        double k3 = h * funcXY.solve(x + h/2, y + k2/2);
        double k4 = h * funcXY.solve(x + h, y + k3);
        return y + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
    }
}
