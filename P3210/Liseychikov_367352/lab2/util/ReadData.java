package lab2.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadData {
    public Data getData(boolean isSys) {
        var scanner = new Scanner(System.in);
        while (true) {
            try {
                printSelectInput();
                int number = Integer.parseInt(scanner.nextLine());

                switch (number) {
                    case 1 -> {
                        if (isSys) {
                            return readDataSysFromConsole(scanner);
                        }
                        return readDataFromConsole(scanner);
                    }
                    case 2 -> {
                        return readDataFromFile(scanner);
                    }
                }
                System.out.println("Выберите из списка");
            } catch (NumberFormatException exp) {
                System.out.println("Неверный ввод");
            } catch (RuntimeException exp) {
            }
        }
    }

    private Data readDataFromFile(Scanner scanner) {
        System.out.println("Введите полный путь до файла");
        String path = scanner.nextLine();

        var file = new File(path);
        if (!file.isFile()) {
            System.out.println("Указанный путь веден не до файла");
            throw new RuntimeException();
        } else if (!file.canRead()) {
            System.out.println("Указанный невозможно прочитать из-за отсутствия прав");
            throw new RuntimeException();
        }
        double left, right, eps;
        try (var reader = new BufferedReader(new FileReader(file))) {
            left = Double.parseDouble(reader.readLine().trim());
            right = Double.parseDouble(reader.readLine().trim());
            eps = Double.parseDouble(reader.readLine().trim());
        } catch (IOException e) {
            System.out.println("Некорректные данные: " + e.getMessage());
            throw new RuntimeException();
        }

        return new Data(left,right,eps);
    }

    private Data readDataFromConsole(Scanner scanner) {
        System.out.println("Введите левую границу:");
        double left = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите правую границу:");
        double right = Double.parseDouble(scanner.nextLine());

        if (left > right) {
            double t = left;
            left = right;
            right = t;
        }
        return new Data(left, right, getDataEps(scanner));
    }

    private double getDataEps(Scanner scanner) {
        while (true) {
            System.out.println("Введите точность:");
            double eps = Double.parseDouble(scanner.nextLine());
            if (eps > 0 && eps < 1) {
                return eps;
            } else {
                System.out.println("Точность должна быть больше 0 и меньше 1.");
            }
        }
    }

    private Data readDataSysFromConsole(Scanner scanner) {
        System.out.println("Введите приближение x:");
        double left = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите приближение y:");
        double right = Double.parseDouble(scanner.nextLine());

        return new Data(left, right, getDataEps(scanner));
    }

    private void printSelectInput() {
        System.out.println("Выберите через что будет осуществляться ввод данных:");
        System.out.println("1 - консоль");
        System.out.println("2 - файл");
    }
}
