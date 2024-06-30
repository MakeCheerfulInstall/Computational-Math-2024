package lab2.solution;

import lab2.models.ISysFunc;

public class Newton {
    public void printMatrix(final double[] f) {
        System.out.println("x - " + f[0]);
        System.out.println("y - " + f[1]);
    }

    public double[] newtonMethod(ISysFunc func, double[] x0, double eps) {
        int counter = 0;
        double[] x1;
        double[] x2 = x0.clone();
        do {
            x1 = x2;
            double[] dx = new GaussMatrix(func.derivativeForNewton(x2), new double[]{-func.f1(x2), -func.f2(x2)}).solveMatrix();
            x2 = sum(x1, dx);
            counter++;
            System.out.println("iteration " + counter);
            printMatrix(x2);
        } while (getNorm(x1, x2) > eps);
        return x2;
    }


    public double getNorm(double[] x1, double[] x2) {
        return Math.max(Math.abs(x1[0] - x2[0]), Math.abs(x1[1] - x2[1]));
    }

    public double[] sum(double[] x1, double[] x2) {
        return new double[]{x1[0] + x2[0], x1[1] + x2[1]};
    }
}
