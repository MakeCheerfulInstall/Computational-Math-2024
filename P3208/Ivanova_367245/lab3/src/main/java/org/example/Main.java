package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Выберете функцию:
                1 - x^2 
                2 -  3x^3-2x^2-7x-8
                3 - 2x^3-3x^2+5x-9
                4 - 1/x
                5 - tan(x)
                """);
        int functionChoice = scanner.nextInt();
        if (functionChoice > 5) {
            System.out.println("Выберете номер функции от 1 до 5");
            System.exit(0);
        }
        System.out.println("Введите границы интегрирования");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        Convergence convergence = new Convergence();
        if (!convergence.check(a, b, functionChoice)){
            System.out.println("Интеграл не сходится, разделим его на 2 интеграла");
            System.out.println("Введите epsilon");
            double epsilon = scanner.nextDouble();
            System.out.println("""
                    Выберете метод интегрирования:
                    1 - Метод левых прямоугольников
                    2 - Метод правых прямоугольников
                    3 - Метод средних прямоугольников 
                    4 - Метод трапеций
                    5 - Метод Симпсона
                    """);
            int methodChoice = scanner.nextInt();
            if (methodChoice > 5) {
                System.out.println("Выберете номер метода от 1 до 5");
                System.exit(0);
            }
            try {
                double root1 = 0;
                double root2 = 0;
                double count = 0;
                switch (methodChoice) {
                    case 1:
                        Method1 method1 = new Method1();
                        root1 = method1.solve(a, convergence.getPoint() - epsilon, functionChoice, epsilon);
                        root2 = method1.solve(convergence.getPoint() + epsilon, b, functionChoice, epsilon);
                        count = method1.getMaxIteration();
                    case 2:
                        Method2 method2 = new Method2();
                        root1 = method2.solve(a, convergence.getPoint() - epsilon, functionChoice, epsilon);
                        root2 = method2.solve(convergence.getPoint() + epsilon, b, functionChoice, epsilon);
                        count = method2.getMaxIteration();
                    case 3:
                        Method3 method3 = new Method3();
                        root1 = method3.solve(a, convergence.getPoint() - epsilon, functionChoice, epsilon);
                        root2 = method3.solve(convergence.getPoint() + epsilon, b, functionChoice, epsilon);
                        count = method3.getMaxIteration();
                    case 4:
                        Method4 method4 = new Method4();
                        root1 = method4.solve(a, convergence.getPoint() - epsilon, functionChoice, epsilon);
                        root2 = method4.solve(convergence.getPoint() + epsilon, b, functionChoice, epsilon);
                        count = method4.getMaxIteration();
                    case 5:
                        Method5 method5 = new Method5();
                        root1 = method5.solve(a, convergence.getPoint() - epsilon, functionChoice, epsilon);
                        root2 = method5.solve(convergence.getPoint() + epsilon, b, functionChoice, epsilon);
                        count = method5.getMaxIteration();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + methodChoice);
                }
                double answer = root1+root2;
                System.out.println("Точка разрыва - " + convergence.getPoint());
                System.out.println("Значение 1-го интеграла: " + root1);
                System.out.println("Значение 2-го интеграла: " + root2);
                System.out.println("Значение интеграла: " + answer);
                System.out.println("Число разбиения интервала интегрирования: " + count);

            } catch (InputMismatchException i) {
                System.out.println("Неверный формат ввода");
            }

        }else {
            System.out.println("""
                    Выберете метод интегрирования:
                    1 - Метод левых прямоугольников
                    2 - Метод правых прямоугольников
                    3 - Метод средних прямоугольников 
                    4 - Метод трапеций
                    5 - Метод Симпсона
                    """);
            int methodChoice = scanner.nextInt();
            if (methodChoice > 5) {
                System.out.println("Выберете номер метода от 1 до 5");
                System.exit(0);
            }
            try {
                System.out.println("Выберете точность вычисления");
                double epsilon = scanner.nextDouble();
                double root = 0;
                double count = 0;
                switch (methodChoice) {
                    case 1:
                        Method1 method1 = new Method1();
                        root = method1.solve(a, b, functionChoice, epsilon);
                        count = method1.getMaxIteration();
                    case 2:
                        Method2 method2 = new Method2();
                        root = method2.solve(a, b, functionChoice, epsilon);
                        count = method2.getMaxIteration();
                    case 3:
                        Method3 method3 = new Method3();
                        root = method3.solve(a, b, functionChoice, epsilon);
                        count = method3.getMaxIteration();
                    case 4:
                        Method4 method4 = new Method4();
                        root = method4.solve(a, b, functionChoice, epsilon);
                        count = method4.getMaxIteration();
                    case 5:
                        Method5 method5 = new Method5();
                        root = method5.solve(a, b, functionChoice, epsilon);
                        count = method5.getMaxIteration();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + methodChoice);
                }
                System.out.println("Значение интеграла: " + root);
                System.out.println("Число разбиения интервала интегрирования: " + count);

            } catch (InputMismatchException i) {
                System.out.println("Неверный формат ввода");
            }
        }
    }
}