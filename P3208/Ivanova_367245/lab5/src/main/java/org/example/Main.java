package org.example;

import javax.swing.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Выберете способ ввода: 1 - через консоль, 2 - через файл, 3 - выбрать функцию из предложенных");
        Scanner scanner = new Scanner(System.in);
        double t = 0;
        int inputChoice = scanner.nextInt();
        if (inputChoice == 1) {
            try {
                System.out.print("Введите количество точек:");
                int amount = scanner.nextInt();
                double[] x = new double[amount];
                double[] y = new double[amount];
                System.out.print("Введите координаты точек:");
                for (int i = 0; i < amount; i++) {
                    x[i] = scanner.nextDouble();
                    y[i] = scanner.nextDouble();
                }
                System.out.println("Введите точку (значение x), в которой будем считать интерполяцию");
                t = scanner.nextDouble();
                Output output = new Output();
                output.consoleOutput(x, y, amount, t);
            } catch (InputMismatchException ie) {
                System.out.println("Неверный формат ввода");
            }
        } else if (inputChoice == 2) {
            try {
                FileReader fr = new FileReader("src/main/resources/input.txt");
                Scanner scan = new Scanner(fr);
                int amount = Integer.parseInt(scan.nextLine().trim());
                double[] x = new double[amount];
                double[] y = new double[amount];
                t = Double.parseDouble(scan.nextLine().trim());
                System.out.println("Количество точек: " + amount);
                System.out.println("Значение x, в котором будем считать приблежнное значение функции: " + t);
                int i = 0;
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        x[i] = Double.parseDouble(parts[0]);
                        y[i] = Double.parseDouble(parts[1]);
                        i++;
                    }
                }
                scan.close();
                for (int j = 0; j < amount - 1; j++) {
                    System.out.println(x[j] + " " + y[j]);
                }
                Output output = new Output();
                output.consoleOutput(x, y, amount, t);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (NoSuchElementException e) {
                System.out.println("Неверный формат заполнения файла.");
                System.out.println("""
                        Формат входного файла:
                        1. Количество точек
                        2. x1 y1
                        3. x2 y2
                        ...
                        """);
            }
        } else {
            try {
                System.out.println("Выберете функцию:" +
                        "1. sin(x)" +
                        " 2. x^2");
                int functionChoice = scanner.nextInt();
                System.out.println("Выберете интервал (введите 2 числа через пробел)");
                double a = scanner.nextDouble();
                double b = scanner.nextDouble();
                System.out.println("Введите количество точек на интервале");
                int amount = scanner.nextInt();
                double[] x = new double[amount];
                double[] y = new double[amount];
                System.out.println("Введите значение x, в котором будем считать приближенное значение функции");
                t = scanner.nextDouble();
                Functions functions = new Functions();
                double h = (b - a) / amount;
                for (int i = 0; i < amount; i++){
                    x[i] = a;
                    a+=h;
                }
                y = functions.getPoints(x, amount, functionChoice);
                Output output = new Output();
                output.consoleOutput(x, y, amount, t);
            } catch (InputMismatchException i) {
                System.out.println("Неверный формат ввода");
            }
        }

    }

}



