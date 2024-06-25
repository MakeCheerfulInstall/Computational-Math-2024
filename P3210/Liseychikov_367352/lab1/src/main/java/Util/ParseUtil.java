package Util;

import exception.IncorrectInputException;
import model.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ParseUtil {
    private final Scanner sc = new Scanner(System.in);

    public double parseAccuracy() {
        System.out.print("Введите точность: ");
        sc.nextLine();
        try {
            String buffer = sc.nextLine();
            double accuracy = Double.parseDouble(buffer);
            if (accuracy < 0) {
                throw new InputMismatchException();
            }
            return accuracy;
        } catch (InputMismatchException exception) {
            throw new IncorrectInputException("Ошибка при вводе точности!");
        }
    }

    public int parseSize() {
        System.out.print("Введите размерность матрицы: ");
        try {
            int size = sc.nextInt();
            if (size > 20 || size < 1) {
                throw new InputMismatchException();
            }
            return size;
        } catch (InputMismatchException exception) {
            throw new IncorrectInputException("Неверная размерность!");
        }
    }

    public double[][] parseMatrix(int size) {
        System.out.println("Введите строки матрицы:");
        double[][] matrix = new double[size][size + 1];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        return matrix;
    }

    public File parseFileName() {
        System.out.print("Введите имя файла: ");
        var file = new File(sc.nextLine());
        if (!file.isFile()) {
            throw new IncorrectInputException("Указанный путь веден не до файла");
        } else if (!file.canRead()) {
            throw new IncorrectInputException("Указанный невозможно прочитать из-за отсутствия прав");
        }
        return file;
    }

    public Data readDataFromFile(File file) {
        try (var reader = new BufferedReader(new FileReader(file))) {
            int size = Integer.parseInt(reader.readLine().trim());
            double accuracy = Double.parseDouble(reader.readLine().trim());

            double[][] matrix = new double[size][size + 1];
            for (int i = 0; i < size; i++) {
                String[] row = reader.readLine().trim().split(" ");
                if (row.length > size + 1) throw new ArrayIndexOutOfBoundsException();

                for (int j = 0; j < size + 1; j++) {
                    matrix[i][j] = Double.parseDouble(row[j].trim());
                }
            }
            return new Data(matrix, accuracy);
        } catch (IOException e) {
            System.out.println("Некорректные данные");
            throw new IncorrectInputException(e.getMessage());
        }
    }
}
