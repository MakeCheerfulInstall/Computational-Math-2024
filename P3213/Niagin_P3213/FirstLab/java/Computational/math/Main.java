package Computational.math;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Double[][] system;
        Double[] systemAnswers;
        double epsilon;
        int dimension;
        System.out.println("Вы хотите ввести данные файлом? да/нет");

        try (Scanner scanner = new Scanner(System.in)) {
            String userAnswer = scanner.next();

            if (userAnswer.equals("да") || userAnswer.isEmpty() || userAnswer.equals(" ")) {
//                System.out.println("Введите название файла");
//                String path = scanner.next();
                try {
//                    Scanner fileScanner = new Scanner(new File(System.getProperty("user.dir") + "/" + path));
                    Scanner fileScanner = new Scanner(new File("input.txt"));
                    dimension = fileScanner.nextInt();
                    system = readMatrixFromFile(fileScanner, dimension);
                    systemAnswers = readAnswersFromFile(dimension, fileScanner);
                    epsilon = Double.parseDouble(fileScanner.next().replace(",", "."));
                    fileScanner.close();

                    SimpleIteration simpleIteration = new SimpleIteration(system, systemAnswers, epsilon);
                    simpleIteration.solve();
                } catch (FileNotFoundException e) {
                    System.err.println("Ошибка при работе с файлом: " + e.getMessage());
                }
            } else if (userAnswer.equals("нет")) {
                System.out.println("Введите размерность: ");
                dimension = scanner.nextInt();
                system = readMatrixFromKeyboard(dimension, scanner);
                systemAnswers = readAnswers(dimension, scanner);
                System.out.println("Введите значение epsilon:");
                epsilon = Double.parseDouble(scanner.next().replace(",", "."));
                SimpleIteration simpleIteration = new SimpleIteration(system, systemAnswers, epsilon);
                simpleIteration.solve();
            } else {
                System.err.println("Ошибка: неверный ввод.");
            }
        } catch (Exception e) {
            System.err.println("Не валидный ввод: " + e.getMessage());
        }
    }



    public static Double[][] readMatrixFromKeyboard(int rows, Scanner scanner) {
        System.out.println("Введите матрицу:");
        Double[][] matrix = new Double[rows][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }

    public static Double[] readAnswers(int size, Scanner scanner) {
        System.out.println("Введите вектор ответов:");
        Double[] vector = new Double[size];
        for (int i = 0; i < size; i++) {
            vector[i] = scanner.nextDouble();
        }
        return vector;
    }


    public static Double[][] readMatrixFromFile(Scanner fileScanner, int dimension)  {
        Double[][] matrix = new Double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = fileScanner.nextDouble();
            }
        }
        return matrix;
    }

    public static Double[] readAnswersFromFile(int size, Scanner fileScanner) {

        Double[] vector = new Double[size];
        for (int i = 0; i < size; i++) {
            vector[i] = fileScanner.nextDouble();
        }
        return vector;
    }

}