package lab4;

import Abdullin_367039.lab4.FunctionDrawer;

import javax.swing.*;

public class QuadraticApproximation {
  private final int number = 2;
  private double[] epsilon;
  double a0 = 0;
  double a1 = 0;
  double a2 = 0;
  double sko = 0;

  public double[] solve(double[] x, double[] y, int amount) {
    double sx = 0;
    double sxx = 0;
    double sxxx = 0;
    double sxxxx = 0;
    double sy = 0;
    double sxy = 0;
    double sxxy = 0;
    for (int i = 0; i < amount; i++) {
      sx += x[i];
    }
    for (int i = 0; i < amount; i++) {
      sxx += x[i] * x[i];
    }
    for (int i = 0; i < amount; i++) {
      sxxx += x[i] * x[i] * x[i];
    }
    for (int i = 0; i < amount; i++) {
      sxxxx += x[i] * x[i] * x[i] * x[i];
    }
    for (int i = 0; i < amount; i++) {
      sy += y[i];
    }
    for (int i = 0; i < amount; i++) {
      sxy += x[i] * y[i];
    }
    for (int i = 0; i < amount; i++) {
      sxxy += x[i] * x[i] * y[i];
    }
    a0 =
        (sy * sxx * sxxxx
                + sxxy * sx * sxxx
                + sxy * sxx * sxxx
                - sxxy * sxx * sxx
                - sx * sxy * sxxxx
                - sy * sxxx * sxxx)
            / (amount * sxx * sxxxx
                + sx * sxx * sxxx
                + sxx * sx * sxxx
                - sxx * sxx * sxx
                - sx * sx * sxxxx
                - amount * sxxx * sxxx);
    a1 =
        (amount * sxy * sxxxx
                + sx * sxx * sxxy
                + sxx * sy * sxxx
                - sxx * sxy * sxx
                - sx * sy * sxxxx
                - amount * sxxy * sxxx)
            / (amount * sxx * sxxxx
                + sx * sxx * sxxx
                + sxx * sx * sxxx
                - sxx * sxx * sxx
                - sx * sx * sxxxx
                - amount * sxxx * sxxx);
    a2 =
        (amount * sxx * sxxy
                + sx * sy * sxxx
                + sxx * sx * sxy
                - sxx * sxx * sy
                - sx * sx * sxxy
                - amount * sxxx * sxy)
            / (amount * sxx * sxxxx
                + sx * sxx * sxxx
                + sxx * sx * sxxx
                - sxx * sxx * sxx
                - sx * sx * sxxxx
                - amount * sxxx * sxxx);
    double[] result = new double[amount];
    for (int i = 0; i < amount; i++) {
      result[i] = a0 + a1 * x[i] + a2 * x[i] * x[i];
    }
    epsilon = new double[amount];
    for (int i = 0; i < amount; i++) {
      epsilon[i] = result[i] - y[i];
    }
    for (int i = 0; i < amount; i++) {
      sko += epsilon[i] * epsilon[i];
    }
    sko = Math.sqrt(sko / amount);
    return result;
  }

  public void draw(double[] x, double[] y, double[] result, int amount) {
    String title = "Quadratic Approximation";
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

  public double getA0() {
    return a0;
  }

  public double getA1() {
    return a1;
  }

  public double getA2() {
    return a2;
  }

  public double[] getEpsilon() {
    return epsilon;
  }

  public double getSko() {
    return sko;
  }
}
