package org.example;

import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Выберете способ ввода: 1 - через консоль, 2 - через файл.");
        Scanner scanner = new Scanner(System.in);
        int amount = 0;
        double[] x = new double[12], y = new double[12];
        int inputChoice = scanner.nextInt();
        if (inputChoice == 1) {
            try {
                System.out.print("Введите количество точек:");
                amount = scanner.nextInt();
                if (amount < 8 || amount > 12) {
                    System.out.println("Таблица должна содержать от 8 до 12 точек");
                    System.exit(0);
                }
                System.out.print("Введите координаты точек:");
                for (int i = 0; i < amount; i++) {
                    x[i] = scanner.nextDouble();
                    y[i] = scanner.nextDouble();
                }

            } catch (InputMismatchException ie) {
                System.out.println("Неверный формат ввода");
            }
        } else {
            try {
                FileReader fr = new FileReader("src/main/resources/input.txt");
                Scanner scan = new Scanner(fr);
                amount = Integer.parseInt(scan.nextLine().trim());
                System.out.println("Количество точек: " + amount);
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
                for (int j = 0; j < amount; j++) {
                    System.out.println(x[j] + " " + y[j]);
                }

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
        }
        LinearApproximation linearApproximation = new LinearApproximation();
        QuadraticApproximation quadraticApproximation = new QuadraticApproximation();
        QubicApproximation qubicApproximation = new QubicApproximation();
        ExponentialApproximation exponentialApproximation = new ExponentialApproximation();
        LogarithmicApproximation logarithmicApproximation = new LogarithmicApproximation();
        PowerApproximation powerApproximation = new PowerApproximation();
        linearApproximation.draw(x, y, linearApproximation.solve(x, y, amount), amount);
        quadraticApproximation.draw(x, y, quadraticApproximation.solve(x, y, amount), amount);
        qubicApproximation.draw(x, y, qubicApproximation.solve(x, y, amount), amount);
        exponentialApproximation.draw(x, y, exponentialApproximation.solve(x, y, amount), amount);
        logarithmicApproximation.draw(x, y, logarithmicApproximation.solve(x, y, amount), amount);
        powerApproximation.draw(x, y, powerApproximation.solve(x, y, amount), amount);
        System.out.println("Вывести ответ в консоль (1) или в файл (2) ?");
        int outputChoice = scanner.nextInt();
        if (outputChoice == 1) {
            double[] sko = new double[6];
            String[] name = new String[6];
            System.out.println("Коэффициенты аппроксимирующих функций:");
            System.out.println("Линейная аппроксимация: a = " + linearApproximation.getA() + ", b = " + linearApproximation.getB());
            for (int i = 0; i < amount; i++) {
                System.out.println("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + linearApproximation.solve(x, y, amount)[i] + ", eps = " + linearApproximation.getEpsilon()[i]);
            }
            System.out.println("Коэффициент корреляции Пирсона: " + linearApproximation.getPearsonCoefficient(x, y, amount));
            System.out.println("Коэффициент детерминации: " + linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount));
            if (linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                System.out.println("Высокая точность аппроксимации");
            } else if (linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                System.out.println("Удовлетворительная аппроксимация");
            } else if (linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                System.out.println("Слабая аппроксимация");
            } else {
                System.out.println("Точность аппроксимации недостаточна");
            }
            System.out.println("Квадратичная аппроксимация: a0 = " + quadraticApproximation.getA0() + ", a1 = " + quadraticApproximation.getA2() + ", a2 = " + quadraticApproximation.getA2());
            for (int i = 0; i < amount; i++) {
                System.out.println("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + quadraticApproximation.solve(x, y, amount)[i] + ", eps = " + quadraticApproximation.getEpsilon()[i]);
            }
            System.out.println("Коэффициент детерминации: " + quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount));
            if (quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                System.out.println("Высокая точность аппроксимации");
            } else if (quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                System.out.println("Удовлетворительная аппроксимация");
            } else if (quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                System.out.println("Слабая аппроксимация");
            } else {
                System.out.println("Точность аппроксимации недостаточна");
            }
            System.out.println("Кубическая аппроксимация: a0 = " + qubicApproximation.getA0() + ", a1 = " + qubicApproximation.getA1() + ", a2 = " + qubicApproximation.getA2() + ", a3 = " + qubicApproximation.getA3());
            for (int i = 0; i < amount; i++) {
                System.out.println("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + qubicApproximation.solve(x, y, amount)[i] + ", eps = " + qubicApproximation.getEpsilon()[i]);
            }
            System.out.println("Коэффициент детерминации: " + qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount));
            if (qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                System.out.println("Высокая точность аппроксимации");
            } else if (qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                System.out.println("Удовлетворительная аппроксимация");
            } else if (qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                System.out.println("Слабая аппроксимация");
            } else {
                System.out.println("Точность аппроксимации недостаточна");
            }
            System.out.println("Экспоненциальная аппроксимация: a = " + exponentialApproximation.getA() + ", b = " + exponentialApproximation.getB());
            for (int i = 0; i < amount; i++) {
                System.out.println("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + exponentialApproximation.solve(x, y, amount)[i] + ", eps = " + exponentialApproximation.getEpsilon()[i]);
            }
            System.out.println("Коэффициент детерминации: " + exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount));
            if (exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                System.out.println("Высокая точность аппроксимации");
            } else if (exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                System.out.println("Удовлетворительная аппроксимация");
            } else if (exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                System.out.println("Слабая аппроксимация");
            } else {
                System.out.println("Точность аппроксимации недостаточна");
            }
            System.out.println("Логарифмическая аппроксимация: a = " + logarithmicApproximation.getA() + ", b = " + logarithmicApproximation.getB());
            for (int i = 0; i < amount; i++) {
                System.out.println("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + logarithmicApproximation.solve(x, y, amount)[i] + ", eps = " + logarithmicApproximation.getEpsilon()[i]);
            }
            System.out.println("Коэффициент детерминации: " + logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount));
            if (logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                System.out.println("Высокая точность аппроксимации");
            } else if (logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                System.out.println("Удовлетворительная аппроксимация");
            } else if (logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                System.out.println("Слабая аппроксимация");
            } else {
                System.out.println("Точность аппроксимации недостаточна");
            }
            System.out.println("Степенная аппроксимация: a = " + exponentialApproximation.getA() + ", b = " + exponentialApproximation.getB());
            for (int i = 0; i < amount; i++) {
                System.out.println("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + powerApproximation.solve(x, y, amount)[i] + ", eps = " + powerApproximation.getEpsilon()[i]);
            }
            System.out.println("Коэффициент детерминации: " + powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount));
            if (powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                System.out.println("Высокая точность аппроксимации");
            } else if (powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                System.out.println("Удовлетворительная аппроксимация");
            } else if (powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                System.out.println("Слабая аппроксимация");
            } else {
                System.out.println("Точность аппроксимации недостаточна");
            }
            System.out.println("Среднеквадратичное отклонение:");
            System.out.println("Линейная аппроксимация: " + linearApproximation.getSko());
            System.out.println("Квадратичная аппроксимация: " + quadraticApproximation.getSko());
            System.out.println("Кубическая аппроксимация: " + qubicApproximation.getSko());
            System.out.println("Экспоненциальная аппроксимация: " + exponentialApproximation.getSko());
            System.out.println("Логарифмическая аппроксимация: " + logarithmicApproximation.getSko());
            System.out.println("Степенная аппроксимация: " + powerApproximation.getSko());
            sko[0] = linearApproximation.getSko();
            name[0] = "линейная";
            sko[1] = quadraticApproximation.getSko();
            name[1] = "квадратичная";
            sko[2] = qubicApproximation.getSko();
            name[2] = "кубическая";
            sko[3] = exponentialApproximation.getSko();
            name[3] = "экспоненциальная";
            sko[4] = logarithmicApproximation.getSko();
            name[4] = "логарифмечская";
            sko[5] = powerApproximation.getSko();
            name[5] = "степенная";
            for (int i = 0; i < 5; i++) {
                int minIndex = i;
                for (int j = i + 1; j < 6; j++) {
                    if (sko[j] < sko[minIndex]) {
                        minIndex = j;
                    }
                }
                double temp = sko[minIndex];
                String tempName = name[minIndex];
                sko[minIndex] = sko[i];
                name[minIndex] = name[i];
                sko[i] = temp;
                name[i] = tempName;
            }
            System.out.println("Минимальное среднеквадратичное отклонение: " + sko[0]);

            System.out.println("Наилчушая аппроксимирующая функция: " + name[0]);
        } else {
            try {
                FileWriter fw = new FileWriter("src/main/resources/output.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fw);
                double[] sko = new double[6];
                String[] name = new String[6];
                bufferedWriter.write("Коэффициенты аппроксимирующих функций:\n");
                bufferedWriter.write("Линейная аппроксимация: a = " + linearApproximation.getA() + ", b = " + linearApproximation.getB()+"\n");
                for (int i = 0; i < amount; i++) {
                    bufferedWriter.write("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + linearApproximation.solve(x, y, amount)[i] + ", eps = " + linearApproximation.getEpsilon()[i]+"\n");
                }
                bufferedWriter.write("Коэффициент корреляции Пирсона: " + linearApproximation.getPearsonCoefficient(x, y, amount)+"\n");
                bufferedWriter.write("Коэффициент детерминации: " + linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount)+"\n");
                if (linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                    bufferedWriter.write("Высокая точность аппроксимации\n");
                } else if (linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                    bufferedWriter.write("Удовлетворительная аппроксимация\n");
                } else if (linearApproximation.getDeterminationCoefficient(linearApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                    bufferedWriter.write("Слабая аппроксимация\n");
                } else {
                    bufferedWriter.write("Точность аппроксимации недостаточна\n");
                }
                bufferedWriter.write("Квадратичная аппроксимация: a0 = " + quadraticApproximation.getA0() + ", a1 = " + quadraticApproximation.getA2() + ", a2 = " + quadraticApproximation.getA2()+"\n");

                for (int i = 0; i < amount; i++) {
                    bufferedWriter.write("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + quadraticApproximation.solve(x, y, amount)[i] + ", eps = " + quadraticApproximation.getEpsilon()[i]+"\n");
                }
                bufferedWriter.write("Коэффициент детерминации: " + quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount)+"\n");
                if (quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                    bufferedWriter.write("Высокая точность аппроксимации\n");
                } else if (quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                    bufferedWriter.write("Удовлетворительная аппроксимация\n");
                } else if (quadraticApproximation.getDeterminationCoefficient(quadraticApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                    bufferedWriter.write("Слабая аппроксимация\n");
                } else {
                    bufferedWriter.write("Точность аппроксимации недостаточна\n");
                }
                bufferedWriter.write("Кубическая аппроксимация: a0 = " + qubicApproximation.getA0() + ", a1 = " + qubicApproximation.getA1() + ", a2 = " + qubicApproximation.getA2() + ", a3 = " + qubicApproximation.getA3()+"\n");
                for (int i = 0; i < amount; i++) {
                    bufferedWriter.write("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + qubicApproximation.solve(x, y, amount)[i] + ", eps = " + qubicApproximation.getEpsilon()[i]+"\n");
                }
                bufferedWriter.write("Коэффициент детерминации: " + qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount)+"\n");
                if (qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                    bufferedWriter.write("Высокая точность аппроксимации\n");
                } else if (qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                    bufferedWriter.write("Удовлетворительная аппроксимация\n");
                } else if (qubicApproximation.getDeterminationCoefficient(qubicApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                    bufferedWriter.write("Слабая аппроксимация\n");
                } else {
                    bufferedWriter.write("Точность аппроксимации недостаточна\n");
                }
                bufferedWriter.write("Экспоненциальная аппроксимация: a = " + exponentialApproximation.getA() + ", b = " + exponentialApproximation.getB()+"\n");
                for (int i = 0; i < amount; i++) {
                    bufferedWriter.write("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + exponentialApproximation.solve(x, y, amount)[i] + ", eps = " + exponentialApproximation.getEpsilon()[i]+"\n");
                }
                bufferedWriter.write("Коэффициент детерминации: " + exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount)+"\n");
                if (exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                    bufferedWriter.write("Высокая точность аппроксимации\n");
                } else if (exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                    bufferedWriter.write("Удовлетворительная аппроксимация\n");
                } else if (exponentialApproximation.getDeterminationCoefficient(exponentialApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                    bufferedWriter.write("Слабая аппроксимация\n");
                } else {
                    bufferedWriter.write("Точность аппроксимации недостаточна\n");
                }
                bufferedWriter.write("Логарифмическая аппроксимация: a = " + logarithmicApproximation.getA() + ", b = " + logarithmicApproximation.getB()+"\n");
                for (int i = 0; i < amount; i++) {
                    bufferedWriter.write("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + logarithmicApproximation.solve(x, y, amount)[i] + ", eps = " + logarithmicApproximation.getEpsilon()[i]+"\n");
                }
                bufferedWriter.write("Коэффициент детерминации: " + logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount)+"\n");
                if (logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                    bufferedWriter.write("Высокая точность аппроксимации\n");
                } else if (logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                    bufferedWriter.write("Удовлетворительная аппроксимация\n");
                } else if (logarithmicApproximation.getDeterminationCoefficient(logarithmicApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                    bufferedWriter.write("Слабая аппроксимация\n");
                } else {
                    bufferedWriter.write("Точность аппроксимации недостаточна\n");
                }
                bufferedWriter.write("Степенная аппроксимация: a = " + exponentialApproximation.getA() + ", b = " + exponentialApproximation.getB()+"\n");
                for (int i = 0; i < amount; i++) {
                    bufferedWriter.write("x = " + x[i] + ", y = " + y[i] + ", phi(x) = " + powerApproximation.solve(x, y, amount)[i] + ", eps = " + powerApproximation.getEpsilon()[i]+"\n");
                }
                bufferedWriter.write("Коэффициент детерминации: " + powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount));
                if (powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount) >= 0.95) {
                    bufferedWriter.write("Высокая точность аппроксимации"+"\n");
                } else if (powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount) >= 0.75) {
                    bufferedWriter.write("Удовлетворительная аппроксимация\n");
                } else if (powerApproximation.getDeterminationCoefficient(powerApproximation.solve(x, y, amount), y, amount) >= 0.5) {
                    bufferedWriter.write("Слабая аппроксимация\n");
                } else {
                    bufferedWriter.write("Точность аппроксимации недостаточна\n");
                }
                bufferedWriter.write("Среднеквадратичное отклонение:\n");
                bufferedWriter.write("Линейная аппроксимация: " + linearApproximation.getSko()+"\n");
                bufferedWriter.write("Квадратичная аппроксимация: " + quadraticApproximation.getSko()+"\n");
                bufferedWriter.write("Кубическая аппроксимация: " + qubicApproximation.getSko()+"\n");
                bufferedWriter.write("Экспоненциальная аппроксимация: " + exponentialApproximation.getSko()+"\n");
                bufferedWriter.write("Логарифмическая аппроксимация: " + logarithmicApproximation.getSko()+"\n");
                bufferedWriter.write("Степенная аппроксимация: " + powerApproximation.getSko()+"\n");
                sko[0] = linearApproximation.getSko();
                name[0] = "линейная";
                sko[1] = quadraticApproximation.getSko();
                name[1] = "квадратичная";
                sko[2] = qubicApproximation.getSko();
                name[2] = "кубическая";
                sko[3] = exponentialApproximation.getSko();
                name[3] = "экспоненциальная";
                sko[4] = logarithmicApproximation.getSko();
                name[4] = "логарифмечская";
                sko[5] = powerApproximation.getSko();
                name[5] = "степенная";
                for (int i = 0; i < 5; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < 6; j++) {
                        if (sko[j] < sko[minIndex]) {
                            minIndex = j;
                        }
                    }
                    double temp = sko[minIndex];
                    String tempName = name[minIndex];
                    sko[minIndex] = sko[i];
                    name[minIndex] = name[i];
                    sko[i] = temp;
                    name[i] = tempName;
                }
                bufferedWriter.write("Минимальное среднеквадратичное отклонение: " + sko[0]+"\n");
                bufferedWriter.write("Наилучшая аппроксимирующая функция: " + name[0]+"\n");
                bufferedWriter.close();
                fw.close();
                System.out.println("Ответ записан в файл output.txt"+"\n");
            } catch (IOException e) {
                System.out.println("Произошла ошибка при записи в файл");
            }
        }
    }
}



