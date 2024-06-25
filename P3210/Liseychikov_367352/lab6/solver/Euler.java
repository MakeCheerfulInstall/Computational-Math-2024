package lab6.solver;


public class Euler implements ISolver {
    @Override
    public double[][] solve(double start, double end, double y0, double h, double e, FuncXY funcXY) {
        int n = (int) ((end - start) / h + 1);
        double[][] resultTable = new double[n][3];

        resultTable[0][0] = start;
        resultTable[0][1] = y0;
        resultTable[0][2] = funcXY.solve(resultTable[0][0], resultTable[0][1]);

        for (int i = 1; i < n; i++) {
            resultTable[i][0] = resultTable[i - 1][0] + h;
            resultTable[i][1] = resultTable[i - 1][1] + h * funcXY.solve(resultTable[i - 1][0], resultTable[i - 1][1]);
            resultTable[i][2] = funcXY.solve(resultTable[i][0], resultTable[i][1]);
        }
        return resultTable;
    }
}
