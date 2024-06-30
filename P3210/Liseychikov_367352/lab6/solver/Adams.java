package lab6.solver;

public class Adams implements ISolver {
    @Override
    public double[][] solve(double start, double end, double y0, double h, double e, FuncXY funcXY) {
        int n = (int) ((end - start) / h + 1);
        double[][] resultTable = new double[n][7];

        resultTable[0][0] = start;
        resultTable[0][1] = y0;
        resultTable[0][2] = funcXY.solve(resultTable[0][0], resultTable[0][1]);

        resultTable[0][3] = h * funcXY.solve(resultTable[0][0], resultTable[0][1]);
        resultTable[0][4] = h * funcXY.solve(resultTable[0][0] + h / 2, resultTable[0][1] + resultTable[0][3] / 2);
        resultTable[0][5] = h * funcXY.solve(resultTable[0][0] + h / 2, resultTable[0][1] + resultTable[0][4] / 2);
        resultTable[0][6] = h * funcXY.solve(resultTable[0][0] + h, resultTable[0][1] + resultTable[0][5]);

        for (int i = 1; i < 4; i++) {
            resultTable[i][0] = resultTable[i - 1][0] + h;
            resultTable[i][1] = resultTable[i - 1][1] + (resultTable[i - 1][3] + 2 * resultTable[i - 1][4] + 2 * resultTable[i - 1][5] + resultTable[i - 1][6]) / 6;
            resultTable[i][2] = funcXY.solve(resultTable[i][0], resultTable[i][1]);

            resultTable[i][3] = h * funcXY.solve(resultTable[i][0], resultTable[i][1]);
            resultTable[i][4] = h * funcXY.solve(resultTable[i][0] + h / 2, resultTable[i][1] + resultTable[i][3] / 2);
            resultTable[i][5] = h * funcXY.solve(resultTable[i][0] + h / 2, resultTable[i][1] + resultTable[i][4] / 2);
            resultTable[i][6] = h * funcXY.solve(resultTable[i][0] + h, resultTable[i][1] + resultTable[i][5]);
        }

        for (int i = 4; i < n; i++) {
            resultTable[i][0] = resultTable[i - 1][0] + h;
            double y = resultTable[i - 1][1] + h / 24 * (55 * resultTable[i - 1][3] - 59 * resultTable[i - 2][3] + 37 * resultTable[i - 3][3] - 9 * resultTable[i - 4][3]);
            double f = 0;
            double tmp = 0;
            while (Math.abs(y - tmp) > 0.1) {
                tmp = y;
                f = funcXY.solve(resultTable[i][0], tmp);
                y = resultTable[i - 1][1] + h / 24 * (9 * f + 19 * resultTable[i - 1][3] - 5 * resultTable[i - 2][3] + resultTable[i - 3][3]);
            }
            resultTable[i][1] = y;
            resultTable[i][2] = f;
        }

        return resultTable;
    }
}
