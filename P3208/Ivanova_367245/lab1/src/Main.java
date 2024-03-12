import java.io.*;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[][] matrix = new double[20][21];
        int n = 0;
        System.out.println("Выберете: 1 - чтение с консоли, 2 - чтение из файла, 3 - генерация матрицы со случайными коэффициентами");
        String mode = scanner.next();
        while (!((Objects.equals(mode, "1")) || (Objects.equals(mode, "2")) || (Objects.equals(mode, "3")))) {
            System.out.println("Пожалуйста, введите цифру от 1 до 3");
            mode = scanner.next();
        }
        if (mode.equals("1")){
            System.out.println("Введите размерность матрицы:");
            try {
                n = scanner.nextInt();
            }catch (InputMismatchException inputMismatchException){
                System.err.println("Введите число от 1 до 20");
            }
            System.out.println("Введите коэффициенты:");
            try {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n + 1; j++) {
                        matrix[i][j] = Double.parseDouble(scanner.next());
                    }
                }
            }catch (NumberFormatException exception){
                System.err.println("Пожалуйста, введите через пробел коэффициенты, вещественные числа вводите с точкой");
            }
        }else if(mode.equals("3")){
            System.out.println("Введите размерность матрицы:");
            n = scanner.nextInt();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n + 1; j++) {
                matrix[i][j] = Math.random() * 100;
                }
            }
        } else{
            File file = new File("input");
            try {
                Scanner scan = new Scanner(file);
                int rows = 0;
                int columns = 0;

                // Определяем количество строк и столбцов в матрице
                while (scan.hasNextLine()) {
                    rows++;
                    String[] rowValues = scan.nextLine().trim().split("\\s+");
                    columns = Math.max(columns, rowValues.length);
                }
                System.out.println("Размер матрицы: "+ rows);

                scan = new Scanner(file); // Переоткрываем сканер

                // Считываем данные из файла и заполняем матрицу
                for (int i = 0; i < rows; i++) {
                    String[] rowValues = scan.nextLine().trim().split("\\s+");
                    for (int j = 0; j < rowValues.length; j++) {
                        matrix[i][j] = Double.parseDouble(rowValues[j]);
                    }
                }
                n = rows;
                scanner.close();
            } catch (FileNotFoundException e) {
                System.err.println("Файл не найден: " + e.getMessage());
            }
            catch (NumberFormatException numberFormatException){
                System.err.println("Пожалуйста, введите вещественые числа через точку");
            }
        }


        System.out.println("Исходная матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                System.out.printf("%.3f", matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }

        GaussMethod gaussMethod = new GaussMethod(n, matrix);
        gaussMethod.calculate();

    }
}