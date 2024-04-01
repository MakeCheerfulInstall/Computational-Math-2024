package org.example;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Выберете способ ввода: 1 - через консоль, 2 - через файл.");
        DataValidator dv = new DataValidator();
        Scanner scanner = new Scanner(System.in);
        Output output = new Output();
        double a, b, epsilon;
        int inputChoice = scanner.nextInt();
        if (inputChoice == 1) {
            try {
                System.out.print("Введите номер метода (1 - метод половинного деления, 3 - метод Ньютона, 5 - метод простой итерации, 7 - метод простой итерации для систем уравнений): ");
                int methodChoice = scanner.nextInt();
                System.out.print("Введите желаемую погрешность epsilon: ");
                epsilon = scanner.nextDouble();
                if (epsilon <= 0) {
                    System.out.println("Погрешность должна быть положительным числом.");
                    System.exit(0);
                }
                System.out.println("Выберете способ вывода: 1 - в консоль, 2 - в файл:");
                int outputChoice = scanner.nextInt();
                switch (methodChoice) {
                    case 1:
                        System.out.println("Введите границы интервала [a, b]: ");
                        System.out.print("a = ");
                        a = scanner.nextDouble();
                        System.out.print("b = ");
                        b = scanner.nextDouble();
                        System.out.print("Введите номер уравнения: ");
                        int equationChoice = scanner.nextInt();
                        if (!dv.checkInterval(a, b, equationChoice)) {
                            System.out.println("На интервале нет корней");
                        } else if (dv.countIntervalRoots(a, b, equationChoice) > 1) {
                            System.out.println("На интервале больше 1 корня");
                        } else {
                            Method1 method1 = new Method1();
                            double root = method1.solve(a, b, equationChoice, epsilon);
                            if (outputChoice == 1) {
                                output.consoleOutput(root, method1.getIterations(), method1.getFunctionValue());
                            } else if (outputChoice == 2) {
                                output.fileOutput(root, method1.getIterations(), method1.getFunctionValue());
                            } else {
                                System.out.println("Ошибка при выборе способа вывода");
                            }
                            SwingUtilities.invokeLater(() -> {
                                FunctionDrawer plotter = new FunctionDrawer("Function", equationChoice, a, b, root, method1.getFunctionValue());
                                plotter.pack();
                                plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                plotter.setVisible(true);
                            });
                        }
                        break;
                    case 3:
                        System.out.println("Введите границы интервала [a, b]: ");
                        System.out.print("a = ");
                        a = scanner.nextDouble();
                        System.out.print("b = ");
                        b = scanner.nextDouble();
                        System.out.print("Введите номер уравнения: ");
                        equationChoice = scanner.nextInt();
                        if (!dv.checkInterval(a, b, equationChoice)) {
                            System.out.println("На интервале нет корней");
                        } else if (dv.countIntervalRoots(a, b, equationChoice) > 1) {
                            System.out.println("На интервале больше 1 корня");
                        } else {
                            Method3 method3 = new Method3();
                            if (outputChoice == 1) {
                                output.consoleOutput(method3.solve(equationChoice, epsilon, a, b), method3.getIterations(), method3.getFunctionValue());
                            } else if (outputChoice == 2) {
                                output.fileOutput(method3.solve(equationChoice, epsilon, a, b), method3.getIterations(), method3.getFunctionValue());
                            } else {
                                System.out.println("Ошибка при выборе способа вывода");
                            }
                            SwingUtilities.invokeLater(() -> {
                                FunctionDrawer plotter = new FunctionDrawer("Function", equationChoice, a, b, method3.solve(equationChoice, epsilon, a, b), method3.getFunctionValue());
                                plotter.pack();
                                plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                plotter.setVisible(true);
                            });
                        }

                        break;
                    case 5:
                        System.out.println("Введите границы интервала [a, b]: ");
                        System.out.print("a = ");
                        a = scanner.nextDouble();
                        System.out.print("b = ");
                        b = scanner.nextDouble();
                        System.out.print("Введите номер уравнения: ");
                        equationChoice = scanner.nextInt();
                        if (!dv.checkInterval(a, b, equationChoice)) {
                            System.out.println("На интервале нет корней");
                        } else if (dv.countIntervalRoots(a, b, equationChoice) > 1) {
                            System.out.println("На интервале больше 1 корня");
                        } else {
                            Method5 method5 = new Method5();
                            if(!method5.checkShodimost(a, b, equationChoice)){
                                break;
                            }
                            if (outputChoice == 1) {
                                output.consoleOutput(method5.solve(equationChoice, epsilon, a, b), method5.getIterations(), method5.getFunctionValue());
                            } else if (outputChoice == 2) {
                                output.fileOutput(method5.solve(equationChoice, epsilon, a, b ), method5.getIterations(), method5.getFunctionValue());
                            } else {
                                System.out.println("Ошибка при выборе способа вывода");
                            }
                            SwingUtilities.invokeLater(() -> {
                                FunctionDrawer plotter = new FunctionDrawer("Function", equationChoice, a, b, method5.solve(equationChoice, epsilon, a, b), method5.getFunctionValue());
                                plotter.pack();
                                plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                plotter.setVisible(true);
                            });
                        }
                        break;
                    case 7:
                        System.out.print("Введите номер системы: ");
                        equationChoice = scanner.nextInt();
                        Method7 method7 = new Method7();
                        System.out.println("Введите приближения для X и Y:");
                        double initialGuessX = scanner.nextDouble();
                        double initialGuessY = scanner.nextDouble();
                        if(!method7.checkShodimost(equationChoice, initialGuessX, initialGuessY)){
                            break;
                        }
                        double[] solution = method7.solve(initialGuessX, initialGuessY, equationChoice, epsilon);
                        try {
                            System.out.println("Решение системы:");
                            System.out.println("x = " + solution[0]);
                            System.out.println("y = " + solution[1]);
                            System.out.println("Количество итераций: "+ method7.getIterations());
                            System.out.println("Вектор погрешностей x" );
                            for (int i = 0; i<=method7.getIterations(); i++){
                                System.out.println(method7.getPogreshnostx()[i]);
                            }
                            System.out.println("Вектор погрешностей y");
                            for (int i = 0; i<=method7.getIterations(); i++){
                                System.out.println(method7.getPogreshnosty()[i]);
                            }
                        } catch (IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        SwingUtilities.invokeLater(() -> {
                            SystemDrawer example = new SystemDrawer("Two Variable Functions Plot", equationChoice, initialGuessX, initialGuessX+1, solution );
                            example.pack();
                            example.setVisible(true);
                        });
                        break;
                    default:
                        System.out.println("Ваш выбор некорректный.");
                }
            } catch (InputMismatchException ie) {
                System.out.println("Неверный формат ввода");
            }
        } else {
            try {
                FileReader fr = new FileReader("src/main/resources/input.txt");
                Scanner scan = new Scanner(fr);
                int equationChoice = Integer.parseInt(scan.nextLine().trim());
                a = Double.parseDouble(scan.nextLine().trim().replaceAll(",", "\\."));
                b = Double.parseDouble(scan.nextLine().trim().replaceAll(",", "\\."));
                epsilon = Double.parseDouble(scan.nextLine().trim().replaceAll(",", "\\."));
                int methodChoice = Integer.parseInt(scan.nextLine().trim());
                int outputChoice = Integer.parseInt(scan.nextLine().trim());
                if (Math.abs(a - b) < 0.1) {
                    System.out.println("Значения должны быть на расстоянии 0,1 и более");
                } else {
                    switch (methodChoice) {
                        case 1:
                            Method1 method1 = new Method1();
                            if (outputChoice == 1) {
                                output.consoleOutput(method1.solve(a, b, equationChoice, epsilon), method1.getIterations(), method1.getFunctionValue());
                            } else if (outputChoice == 2) {
                                output.fileOutput(method1.solve(a, b, equationChoice, epsilon), method1.getIterations(), method1.getFunctionValue());
                            } else {
                                System.out.println("Ошибка при выборе способа вывода");
                            }
                            SwingUtilities.invokeLater(() -> {
                                FunctionDrawer plotter = new FunctionDrawer("Function", equationChoice, a, b, method1.solve(a, b, equationChoice, epsilon), method1.getFunctionValue());
                                plotter.pack();
                                plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                plotter.setVisible(true);
                            });
                            break;
                        case 3:
                            Method3 method3 = new Method3();
                            if (outputChoice == 1) {
                                output.consoleOutput(method3.solve(equationChoice, epsilon, a, b), method3.getIterations(), method3.getFunctionValue());
                            } else if (outputChoice == 2) {
                                output.fileOutput(method3.solve(equationChoice, epsilon, a, b), method3.getIterations(), method3.getFunctionValue());
                            } else {
                                System.out.println("Ошибка при выборе способа вывода");
                            }
                            SwingUtilities.invokeLater(() -> {
                                FunctionDrawer plotter = new FunctionDrawer("Function", equationChoice, a, b, method3.solve(equationChoice, epsilon, a, b), method3.getFunctionValue());
                                plotter.pack();
                                plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                plotter.setVisible(true);
                            });
                            break;
                        case 5:
                            Method5 method5 = new Method5();
                            if(!method5.checkShodimost(a, b, equationChoice)){
                                break;
                            }
                            if (outputChoice == 1) {
                                output.consoleOutput(method5.solve(equationChoice, epsilon, a, b), method5.getIterations(), method5.getFunctionValue());
                            } else if (outputChoice == 2) {
                                output.fileOutput(method5.solve(equationChoice, epsilon, a, b), method5.getIterations(), method5.getFunctionValue());
                            } else {
                                System.out.println("Ошибка при выборе способа вывода");
                            }
                            SwingUtilities.invokeLater(() -> {
                                FunctionDrawer plotter = new FunctionDrawer("Function", equationChoice, a, b, method5.solve(equationChoice, epsilon, a, b), method5.getFunctionValue());
                                plotter.pack();
                                plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                plotter.setVisible(true);
                            });
                            break;

                        default:
                            System.out.println("Ваш выбор некорректный.");
                    }
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoSuchElementException e) {
                System.out.println("Неверный формат заполнения файла.");
                System.out.println("""
                        Формат входного файла:
                        1. Номер уравнения: 1-3
                        2. Коэффициент а
                        3. Коэффициент b
                        4. Точность e
                        5. Метод решения: 1 - метод половинного деления, 3 - метод Ньютона, 5 - метод простой итерации
                        6. Вывод результата в файл/консоль: 1 - в консоль, 2 - в файл
                        """);
            }
        }
    }
}



