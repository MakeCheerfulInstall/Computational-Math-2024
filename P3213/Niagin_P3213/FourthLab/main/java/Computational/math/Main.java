package Computational.math;

import Computational.math.ApproximationMethods.*;
import Computational.math.GraphicPart.MainComponents.MainFrame;
import org.netirc.library.jtables.exception.MalformedTableException;


import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static FabricMethods fb = new FabricMethods();


    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new InputStreamReader(System.in))) {
            do {
                System.out.println("Выберите вариант работы программы: ");
                System.out.println("1. Ввод файлом");
                System.out.println("2. Ввод лапками");
                System.out.println("3. Дефолтный ввод");
                System.out.print("> ");
                var input = sc.nextInt();
                if(input==3){
                    Scanner fileReader = new Scanner(new FileReader("C:\\Users\\tnt11\\IdeaProjects\\FourthLabMath\\src\\test\\java\\notMineInput"));
                    var dimens = fileReader.nextInt();
                    double[][] data = readMatrixFromFile(fileReader,dimens);
                    fb.executeMethod(new FunctionalTable(data));
                    continue;
                }
                FunctionalTable functionalTable = processingAnswer(input, sc);
                if (functionalTable == null){
                    continue;
                }
                fb.executeMethod(functionalTable);
            } while (true);
        } catch (InputMismatchException e) {
            System.out.println("Некорректный ввод");
            System.exit(-1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private static FunctionalTable processingAnswer(int input, Scanner consoleReader) {
        String inputLine;
        if (input == 1) {
            try {
                /**
                 * p.s. формат файла
                 * 8
                 * 1.2 2.9 4.1 5.5 6.7 7.8 9.2 10.3
                 * 7.4 9.5 11.1 12.9 14.6 17.3 18.2 20.7
                 *
                 * где 8 - количество столбцов
                 * строчки 1 и 2 это X и Y соответственно
                 */
                System.out.println("Введите полный путь до файла");
                System.out.print(">");
                inputLine = consoleReader.next();

                Scanner fileScanner = new Scanner(new FileReader(inputLine));
                var dimension = fileScanner.nextInt();
                return new FunctionalTable(readMatrixFromFile(fileScanner, dimension));

            } catch (FileNotFoundException e) {
                System.err.println("Файл с указанным путём не найден");
            }
        }else if(input == 2){
            try{
                System.out.println("Введите количество столбцов");
                System.out.print(">");
                int dimension = consoleReader.nextInt();
                System.out.println("Введите " + dimension + " столбцов x через пробел");
                double[][] data = new double[2][dimension];
                for (int i = 0; i < dimension; i++) {
                    data[0][i] = consoleReader.nextDouble();
                }
                System.out.println("А теперь Y");
                for (int i = 0; i < dimension; i++) {
                    data[1][i] = consoleReader.nextDouble();
                }
                return new FunctionalTable(data);
            }catch (InputMismatchException e){
                System.err.println("Не валиден ввод");
            }
        }
        else{
            System.out.println("Введите число от 1-2");
            return null;
        }
        return null;
    }

    private static double[][] readMatrixFromFile(Scanner fileScanner, int dimension) {
        double[][] matrix = new double[2][dimension];

        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < dimension; ++j) {
                matrix[i][j] = Double.parseDouble(fileScanner.next().replace(",", "."));
            }
        }

        return matrix;
    }
}