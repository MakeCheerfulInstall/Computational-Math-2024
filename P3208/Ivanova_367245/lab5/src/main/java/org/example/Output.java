package org.example;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    public void consoleOutput(double[] x, double[] y, int amount, double t) {

        for (int i = 0; i < amount; i++) {
            for (int j = i+1; j < amount - 1; j++) {
                if (x[i] == x[j]) {
                    System.out.println("Значения x не должны повторяться.");
                    System.exit(0);
                }
            }
        }

        double[][] differences = new double[amount][amount];

        for (int i = 0; i < amount; i++) {
            differences[i][0] = y[i];
        }

        for (int j = 1; j < amount; j++) {
            for (int i = 0; i < amount - j; i++) {
                differences[i][j] = differences[i + 1][j - 1] - differences[i][j - 1];
            }
        }

        System.out.println("Таблица конечных разностей:");
        System.out.printf("%-10s", "x");
        for (int j = 0; j < amount; j++) {
            System.out.printf("%-10s", "Δ^" + j + "y");
        }
        System.out.println();

        for (int i = 0; i < amount; i++) {
            System.out.printf("%-10.4f", x[i]);
            for (int j = 0; j < amount - i; j++) {
                System.out.printf("%-10.4f", differences[i][j]);
            }
            System.out.println();
        }
        int sign = 0;
        double h = Math.round(x[1] - x[0]);
        for (int i = 1; i < amount - 1; i++) {
            if (Math.round(x[i + 1] - x[i]) != h) {
                sign = 1;
            }
        }
        Lagrange lagrange = new Lagrange();
        Newton newton = new Newton();
        Gauss gauss = new Gauss();
        System.out.println("Многочлен Лагранжа:");
        System.out.println("Интерполяция при x = " + t + " равна " + lagrange.solve(x, y, amount, t));
        String title1 = "Метод Лагранжа";
        String mainGraphLabel = "Основная функция";
        FunctionDrawer functionDrawer1 = new FunctionDrawer(title1, amount, x, y, mainGraphLabel, t, lagrange.solve(x, y, amount, t));
        functionDrawer1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        functionDrawer1.pack();
        functionDrawer1.setVisible(true);
        if (sign == 1) {
            System.out.println("Метод Ньютона c разделенными разностями:");
            System.out.println("Интерполяция при x = " + t + " равна " + newton.solve(x, y, amount, t));
            String title = "Метод Ньютона";
            FunctionDrawer functionDrawer2 = new FunctionDrawer(title, amount, x, y, mainGraphLabel, t, newton.solve(x, y, amount, t));
            functionDrawer2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            functionDrawer2.pack();
            functionDrawer2.setVisible(true);
        } else {
            System.out.println("Нельзя использовать метод Ньютона с разделенными разностями, x не должны быть равноотстоящими");
        }
        if (amount % 2 == 0) {
            System.out.println("Нельзя использовать интерполяционный многочлен Гаусса, так как количество точек должно быть нечетным.");
        } else if (sign == 1) {
            System.out.println("Нельзя использовать интерполяционный многочлен Гаусса, так как узлы должны быть равноотстоящими.");
        } else {
            Functions f = new Functions();
            System.out.println("Метод Гаусса:");
            System.out.println("Интерполяция при x = " + t + " равна " + gauss.solve(x, y, amount, t));
            String title2 = "Метод Гаусса";
            FunctionDrawer functionDrawer3 = new FunctionDrawer(title2, amount, x, y, mainGraphLabel, t, gauss.solve(x, y, amount, t));
            functionDrawer3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            functionDrawer3.pack();
            functionDrawer3.setVisible(true);
        }

    }


    public void fileOutput(double[] x, double[] y, int amount, String filename) {
        double[][] differences = new double[amount][amount];

        for (int i = 0; i < amount; i++) {
            differences[i][0] = y[i];
        }

        for (int j = 1; j < amount; j++) {
            for (int i = 0; i < amount - j; i++) {
                differences[i][j] = differences[i + 1][j - 1] - differences[i][j - 1];
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Таблица конечных разностей:\n");
            writer.write(String.format("%-10s", "x"));
            for (int j = 0; j < amount; j++) {
                writer.write(String.format("%-10s", "Δ^" + j + "y"));
            }
            writer.write("\n");
            for (int i = 0; i < amount; i++) {
                writer.write(String.format("%-10.4f", x[i]));
                for (int j = 0; j < amount - i; j++) {
                    writer.write(String.format("%-10.4f", differences[i][j]));
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
