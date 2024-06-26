package com.example.lab4.methods;

import java.util.ArrayList;
import lombok.Getter;
import org.apache.commons.math3.linear.*;
@Getter
public class QuadApproximation extends Method {
    private Double a;
    private Double b;
    private Double c;

    public double f(double x) {
        return a * Math.pow(x, 2) + b * x + c;
    }
    protected Double determ;

    @Override
    public void calculate(ArrayList<Double> arrayOfX, ArrayList<Double> arrayOfY, int n) {
        double[] terms = new double[3];
        double[][] matrixData = new double[3][3];
        double[] vector = new double[3];

        for (int i = 0;i<n;i++) {
            double x = arrayOfX.get(i);
            double y = arrayOfY.get(i);
            terms[0] += Math.pow(x, 4);
            terms[1] += Math.pow(x, 3);
            terms[2] += Math.pow(x, 2);

            matrixData[0][0] += Math.pow(x, 4);
            matrixData[0][1] += Math.pow(x, 3);
            matrixData[0][2] += Math.pow(x, 2);
            matrixData[1][0] += Math.pow(x, 3);
            matrixData[1][1] += Math.pow(x, 2);
            matrixData[1][2] += x;
            matrixData[2][0] += Math.pow(x, 2);
            matrixData[2][1] += x;
            matrixData[2][2] += 1;

            vector[0] += x * x * y;
            vector[1] += x * y;
            vector[2] += y;
        }

        RealMatrix coefficients = new Array2DRowRealMatrix(matrixData, false);
        DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

        RealVector constants = new ArrayRealVector(vector, false);
        RealVector solution = solver.solve(constants);

        a = solution.getEntry(0);
        b = solution.getEntry(1);
        c = solution.getEntry(2);
        double phiAvg = 0;

        for (int i = 0; i < n; i++){
            ArrayList<Double> tmp = new ArrayList<>();
            tmp.add(arrayOfX.get(i));
            tmp.add(arrayOfY.get(i));
            phiAvg += f(arrayOfX.get(i));
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
        return "Квадратичная аппроксимация";
    }
    @Override
    protected String getStringFun() {
        return "phi(x)="+ a +" * x^2 + " + b +" * x +" + c;
    }
}
