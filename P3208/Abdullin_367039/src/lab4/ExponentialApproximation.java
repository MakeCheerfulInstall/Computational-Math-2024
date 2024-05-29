package lab4;

import Abdullin_367039.lab4.FunctionDrawer;

import javax.swing.*;

public class ExponentialApproximation {
  private double[] epsilon;
  private double a;
  private double b;
  private double sko;

  public double[] solve(double[] x, double[] y, int amount) {
    epsilon = new double[amount];
    double sx = 0;
    double sxx = 0;
    double sy = 0;
    double sxy = 0;
    for (int i = 0; i < amount; i++) {
      sx += x[i];
      sxx += x[i] * x[i];
      sy += Math.log(y[i]);
      sxy += x[i] * Math.log(y[i]);
    }
    b = (sxy * amount - sx * sy) / (sxx * amount - sx * sx);
    a = Math.exp((sy - b * sx) / amount);
    double[] result = new double[amount];
    for (int i = 0; i < amount; i++) {
      result[i] = a * Math.exp(x[i] * b);
    }
    sko = 0;
    for (int i = 0; i < amount; i++) {
      epsilon[i] = result[i] - y[i];
      sko += epsilon[i] * epsilon[i];
    }
    sko = Math.sqrt(sko / amount);
    return result;
  }

  public void draw(double[] x, double[] y, double[] result, int amount) {
    String title = "Exponential Approximation";
    Abdullin_367039.lab4.FunctionDrawer functionDrawer = new FunctionDrawer(title, amount, x, y, result);
    functionDrawer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    functionDrawer.pack();
    functionDrawer.setVisible(true);
  }

  public double getDeterminationCoefficient(double[] result, double y[], int amount) {
    double midPhi = 0;
    double r2 = 0;
    for (int i = 0; i < amount; i++) {
      midPhi += result[i];
    }
    midPhi = midPhi / amount;
    double chisl = 0;
    double znam = 0;
    for (int i = 0; i < amount; i++) {
      chisl += (y[i] - result[i]) * (y[i] - result[i]);
      znam += (y[i] - midPhi) * (y[i] - midPhi);
    }
    r2 = 1 - chisl / znam;
    return r2;
  }

  public double[] getEpsilon() {
    return epsilon;
  }

  public double getA() {
    return a;
  }

  public double getB() {
    return b;
  }

  public double getSko() {
    return sko;
  }
}
