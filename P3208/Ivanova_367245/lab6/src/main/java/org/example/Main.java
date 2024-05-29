package org.example;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Functions f = new Functions();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите функцию:");
        System.out.println("1. y' = x+y");
        System.out.println("2. y' = x^2 + y + 1");
        System.out.println("3. y' = e^x");
        int functionChoice = scanner.nextInt();
        System.out.println("Выберите интервал (2 числа через пробел)");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        System.out.println("Введите начальные условия (x0 и y0 через пробел)");
        double x0 = scanner.nextDouble();
        double y0 = scanner.nextDouble();
        System.out.println("Выберите h:");
        double h = scanner.nextDouble();
        ImprovedEulerMethod improvedEulerMethod = new ImprovedEulerMethod();
        RungeKuttaMethod rungeKuttaMethod = new RungeKuttaMethod();
        AdamsMethod adamsMethod = new AdamsMethod();
        improvedEulerMethod.solve(x0, y0, a, b, h, functionChoice);
        rungeKuttaMethod.solve(x0, y0, a, b, h, functionChoice);
        adamsMethod.solve(x0, y0, a, b, h, functionChoice);

        int n = (int) Math.round((b - a) / h + 1);

        System.out.println("Модифицированный метод Эйлера:");
        System.out.printf("%-5s %-10s %-10s %-10s %-12s%n", "i", "x_i", "y_i", "f(x_i, y_i)", "y_exact");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-5d %-10.4f %-10.4f %-10.4f %-10.4f%n", i, improvedEulerMethod.getX()[i], improvedEulerMethod.getY()[i], improvedEulerMethod.getF()[i], f.exactY(functionChoice, improvedEulerMethod.getX()[i], x0, y0));
        }
        System.out.println("Погрешность:" + improvedEulerMethod.RungeRule(n - 1, h));

        System.out.println("Метод Рунге-Кутты 4 порядка:");
        System.out.printf("%-5s %-10s %-10s %-10s %-12s%n", "i", "x_i", "y_i", "f(x_i, y_i)", "y_exact");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-5d %-10.4f %-10.4f %-10.4f %-10.4f%n", i, rungeKuttaMethod.getX()[i], rungeKuttaMethod.getY()[i], rungeKuttaMethod.getF()[i], f.exactY(functionChoice, rungeKuttaMethod.getX()[i], x0, y0));
        }
        System.out.println("Погрешность:" + rungeKuttaMethod.RungeRule(n-1, h));

        System.out.println("Метод Адамса:");
        System.out.printf("%-5s %-10s %-10s %-10s %-12s%n", "i", "x_i", "y_i", "f(x_i, y_i)", "y_exact");
        for (int i = 0; i < n; i++) {
            System.out.printf("%-5d %-10.4f %-10.4f %-10.4f %-10.4f%n", i, adamsMethod.getX()[i], adamsMethod.getY()[i], adamsMethod.getF()[i], f.exactY(functionChoice, adamsMethod.getX()[i], x0, y0));
        }
        System.out.println("Погрешность:" + adamsMethod.getInaccuracy(n, functionChoice, x0, y0));
        double [] yV = new double [n];
        for (int i = 0; i < n; i++){
            yV[i] = f.exactY(functionChoice, improvedEulerMethod.getX()[i], x0, y0);
        }
        JFrame frame1 = new JFrame("Модифицированный Метод Эйлера");
        FunctionGraph panel1 = new FunctionGraph(improvedEulerMethod.getX(), yV, improvedEulerMethod.getX(), improvedEulerMethod.getY(), n, "Модифицированный Метод Эйлера");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.add(panel1);
        frame1.pack();
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        JFrame frame2 = new JFrame("Метод Рунге Кутта 4 порядка");
        FunctionGraph panel2 = new FunctionGraph(rungeKuttaMethod.getX(), yV, rungeKuttaMethod.getX(), rungeKuttaMethod.getY(), n, "Метод Рунге Кутта 4 порядка");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.add(panel2);
        frame2.pack();
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
        JFrame frame3 = new JFrame("Метод Адамса");
        FunctionGraph panel3 = new FunctionGraph(adamsMethod.getX(), yV, adamsMethod.getX(), adamsMethod.getY(), n, "Метод Адамса");
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.add(panel3);
        frame3.pack();
        frame3.setLocationRelativeTo(null);
        frame3.setVisible(true);
    }
}
