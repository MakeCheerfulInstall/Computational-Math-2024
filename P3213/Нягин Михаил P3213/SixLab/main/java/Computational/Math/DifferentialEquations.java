package Computational.Math;

import org.math.plot.Plot2DPanel;
import org.math.plot.canvas.*;

import javax.swing.*;
import java.util.Scanner;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.util.FastMath;

public class DifferentialEquations {
    private static int num;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Выберите дифференциальное уравнение: ");
                System.out.println("1. 3x + 7y");
                System.out.println("2. y + cos(2x)");
                System.out.println("3. x^3 + y");
                num = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите число");
                continue;
            }
            if (num < 1 || num > 3) {
                System.out.println("Пожалуйста, введите цифру от 1 до 3");
                continue;
            } else {
                break;
            }
        }

        System.out.println("Введите y0, x0, xn, h, e");
        String[] data = scanner.nextLine().split(" ");
        if (data.length != 5) {
            System.out.println("Вы ввели неверные данные");
            System.exit(0);
        }

        double y0, x0, xn, h, e;
        try {
            y0 = Double.parseDouble(data[0].replace(',', '.'));
            x0 = Double.parseDouble(data[1].replace(',', '.'));
            xn = Double.parseDouble(data[2].replace(',', '.'));
            h = Double.parseDouble(data[3].replace(',', '.'));
            e = Double.parseDouble(data[4].replace(',', '.'));
        } catch (NumberFormatException ex) {
            System.out.println("Вы ввели неверные данные");
            System.exit(0);
            return;
        }

        UnivariateFunction f = getFunction(num);

        double epsilon = checkEuler(f, y0, x0, xn, h, h / 2, 1);
        while (epsilon > e) {
            h /= 2;
            epsilon = checkEuler(f, y0, x0, xn, h, h / 2, 2);
        }
        System.out.println("Точность метода Эйлера по правилу Рунге: " + epsilon);

        epsilon = checkRunge(f, y0, x0, xn, h, h / 2, 4);
        while (epsilon > e) {
            h /= 2;
            epsilon = checkRunge(f, y0, x0, xn, h, h / 2, 4);
        }
        System.out.println("Точность метода Рунге-Кутта по правилу Рунге: " + epsilon);

        // Графики и таблица данных
        Plot2DPanel plot = new Plot2DPanel();
        double[] xExact = new double[(int)((xn - x0) / h) + 1];
        double[] yExact = getExactSolution(num, xExact, x0, y0);

        plot.addLinePlot("Exact solution", xExact, yExact);

        double[][] eulerResult = euler(f, y0, x0, xn, h);
        plot.addLinePlot("Euler", eulerResult[0], eulerResult[1]);

        double[][] rungeResult = rungeKutt(f, y0, x0, xn, h);
        plot.addLinePlot("Runge-Kutt", rungeResult[0], rungeResult[1]);

        // Метод Адамса
        double[][] adamsResult = new double[0][];
        double dif = 1e8;
        h *= 2;
        while (dif > e) {
            h /= 2;
            adamsResult = adams(f, y0, x0, xn, h, 4);
            dif = 0;
            for (int i = 0; i < Math.min(adamsResult[1].length, yExact.length); i++) {
                dif = Math.max(dif, Math.abs(adamsResult[1][i] - yExact[i]));
            }
        }
        System.out.println("Точность метода Адамса: " + e);
        plot.addLinePlot("Adams", adamsResult[0], adamsResult[1]);

        JFrame frame = new JFrame("Differential Equations");
        frame.setContentPane(plot);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static UnivariateFunction getFunction(int num) {
        switch (num) {
            case 1:
                return x -> 3 * x + 7;
            case 2:
                return x -> FastMath.cos(2 * x);
            default:
                return x -> FastMath.pow(x, 3);
        }
    }

    private static double[][] euler(UnivariateFunction f, double y0, double x0, double xn, double h) {
        int n = (int) ((xn - x0) / h);
        double[] x = new double[n + 1];
        double[] y = new double[n + 1];
        x[0] = x0;
        y[0] = y0;

        for (int i = 0; i < n; i++) {
            x[i + 1] = x[i] + h;
            y[i + 1] = y[i] + h * f.value(x[i]);
        }
        return new double[][]{x, y};
    }

    private static double[][] rungeKutt(UnivariateFunction f, double y0, double x0, double xn, double h) {
        int n = (int) ((xn - x0) / h);
        double[] x = new double[n + 1];
        double[] y = new double[n + 1];
        x[0] = x0;
        y[0] = y0;

        for (int i = 0; i < n; i++) {
            double k1 = h * f.value(x[i]);
            double k2 = h * f.value(x[i] + h / 2);
            double k3 = h * f.value(x[i] + h / 2);
            double k4 = h * f.value(x[i] + h);
            x[i + 1] = x[i] + h;
            y[i + 1] = y[i] + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        }
        return new double[][]{x, y};
    }

    private static double[][] adams(UnivariateFunction f, double y0, double x0, double xn, double h, int m) {
        int n = (int) ((xn - x0) / h);
        double[] x = new double[n + 1];
        double[] y = new double[n + 1];
        x[0] = x0;
        y[0] = y0;

        for (int i = 0; i < m; i++) {
            x[i + 1] = x[i] + h;
            y[i + 1] = y[i] + h * f.value(x[i]);
        }
        for (int i = m; i < n; i++) {
            x[i + 1] = x[i] + h;
            y[i + 1] = y[i] + h * (
                    55 * f.value(x[i]) - 59 * f.value(x[i - 1]) + 37 * f.value(x[i - 2]) - 9 * f.value(x[i - 3])
            ) / 24;
        }
        return new double[][]{x, y};
    }

    private static double checkEuler(UnivariateFunction f, double y0, double x0, double xn, double h1, double h2, int p) {
        double[] y_h1 = euler(f, y0, x0, xn, h1)[1];
        double[] y_h2 = euler(f, y0, x0, xn, h2)[1];
        return Math.abs(y_h1[y_h1.length - 1] - y_h2[y_h2.length - 1]) / (Math.pow(2, p) - 1);
    }

    private static double checkRunge(UnivariateFunction f, double y0, double x0, double xn, double h1, double h2, int p) {
        double[] y_h1 = rungeKutt(f, y0, x0, xn, h1)[1];
        double[] y_h2 = rungeKutt(f, y0, x0, xn, h2)[1];
        return Math.abs(y_h1[y_h1.length - 1] - y_h2[y_h2.length - 1]) / (Math.pow(2, p) - 1);
    }

    private static double[] getExactSolution(int num, double[] x, double x0, double y0) {
        // Здесь должна быть функция для нахождения точного решения
        // Примерный вид функции
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = exactFunction(num, x[i], x0, y0);
        }
        return y;
    }

    private static double exactFunction(int num, double x, double x0, double y0) {
        // Здесь необходимо реализовать точное решение
        // Это пример, вам нужно реализовать правильную функцию на основе ваших уравнений
        return 0;
    }
}
