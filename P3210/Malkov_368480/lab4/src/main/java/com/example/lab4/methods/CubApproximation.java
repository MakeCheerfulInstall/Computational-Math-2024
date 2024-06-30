package com.example.lab4.methods;

import lombok.Getter;
import org.apache.commons.math3.linear.*;

import java.util.ArrayList;
@Getter
public class CubApproximation extends Method{
    private Double a;
    private Double b;
    private Double c;
    private Double d;

    public double f(double x) {
        return a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
    }
    protected Double determ;

    @Override
    public void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n) {
        double[][] matrixData = new double[4][4];
        double[] vector = new double[4];

        for (int i = 0; i < n; i++) {
            double x = arrayOfX.get(i);
            double y = arrayOfY.get(i);
            double x2 = Math.pow(x, 2);
            double x3 = Math.pow(x, 3);
            double x4 = Math.pow(x, 4);
            double x5 = Math.pow(x, 5);
            double x6 = Math.pow(x, 6);

            matrixData[0][0] += x6;
            matrixData[0][1] += x5;
            matrixData[0][2] += x4;
            matrixData[0][3] += x3;
            matrixData[1][0] += x5;
            matrixData[1][1] += x4;
            matrixData[1][2] += x3;
            matrixData[1][3] += x2;
            matrixData[2][0] += x4;
            matrixData[2][1] += x3;
            matrixData[2][2] += x2;
            matrixData[2][3] += x;
            matrixData[3][0] += x3;
            matrixData[3][1] += x2;
            matrixData[3][2] += x;
            matrixData[3][3] += 1;

            vector[0] += y * x3;
            vector[1] += y * x2;
            vector[2] += y * x;
            vector[3] += y;
        }

        RealMatrix coefficients = new Array2DRowRealMatrix(matrixData, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

        RealVector constants = new ArrayRealVector(vector, false);
        RealVector solution = solver.solve(constants);

        a = solution.getEntry(0);
        b = solution.getEntry(1);
        c = solution.getEntry(2);
        d = solution.getEntry(3);
        double phiAvg = 0;

        for (int i = 0; i < n; i++){
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(arrayOfX.get(i));
            tmp.add(arrayOfY.get(i));
            tmp.add(f(arrayOfX.get(i)));
            tmp.add(f(arrayOfX.get(i)) - arrayOfY.get(i));
            table.add(tmp);
            S += Math.pow(f(arrayOfX.get(i)) - arrayOfY.get(i), 2);
        }
        sko = Math.sqrt(S/n);

        phiAvg = phiAvg / n;
        double ssTot = 0;
        double ssRes = 0;

        for (int i = 0; i < n; i++) {
            double fi = f(arrayOfX.get(i));
            ssTot += Math.pow(arrayOfY.get(i) - phiAvg, 2);
            ssRes += Math.pow(arrayOfY.get(i) - fi, 2);
        }

        determ = 1 - (ssRes / ssTot);
    }

    @Override
    public String getNameMethod() {
        return "Кубическая аппроксимация";
    }
    @Override
    protected String getStringFun() {
        return "phi(x)="+ a +"* x ^ 3 + " + b + " * x ^ 2 + " + c +" * x + " + d;
    }

}
