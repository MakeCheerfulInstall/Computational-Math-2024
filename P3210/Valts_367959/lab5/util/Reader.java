package lab5.util;

import lab5.entity.Functions;
import lab5.methods.Polynomial;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Reader {
    private Scanner scanner = new Scanner(System.in);

    public void readInput() throws FileNotFoundException {
        System.out.println(Printer.getBlueText("Выберите типа ввода:"));
        System.out.println(Printer.getYellowText("1. Таблица"));
        System.out.println(Printer.getYellowText("2. Файл"));
        System.out.println(Printer.getYellowText("3. Функция"));

        int typeOfInput = Integer.parseInt(scanner.nextLine());

        ArrayList<Double[]> values = new ArrayList<>();
        double x;

        switch(typeOfInput){
            case 1 -> {
                System.out.println(Printer.getBlueText("Введите количество значений:"));
                System.out.println(Printer.getYellowText("n > 2"));
                int n = Integer.parseInt(scanner.nextLine());
                while (n < 3){
                    System.out.println(Printer.getBlueText("Введите количество значений:"));
                    System.out.println(Printer.getYellowText("n > 2"));
                    n = Integer.parseInt(scanner.nextLine());
                }

                System.out.println(Printer.getBlueText("Enter table:"));
                System.out.println(Printer.getYellowText("x1 y1"));
                System.out.println(Printer.getYellowText("x2 y2"));
                System.out.println(Printer.getYellowText("... ..."));

                HashSet<Double> setX = new HashSet<>();
                for (int i = 0; i < n; ++i){
                    String[] line = scanner.nextLine().split(" ");
                    Double[] xy = new Double[2];
                    xy[0] = Double.parseDouble(line[0]);

                    if (setX.contains(xy[0])) {
                        n++;
                        System.out.println(Printer.getRedText("Такой X уже существует"));
                        continue;
                    }

                    setX.add(xy[0]);
                    xy[1] = Double.parseDouble(line[1]);
                    values.add(xy);
                }

                System.out.println(Printer.getBlueText("Введите X, чтобы найти приблизительное значение:"));
                x = Double.parseDouble(scanner.nextLine());
                while(setX.contains(x)){
                    System.out.println(Printer.getBlueText("Введите X, чтобы найти приблизительное значение:"));
                    x = Double.parseDouble(scanner.nextLine());
                }

                Polynomial.setX(x);
                Polynomial.setValues(values);
            }
            case 3 -> {
                Printer.printFunctions();

                int numberOfFunction = Integer.parseInt(scanner.nextLine());
                while(numberOfFunction < 1 || numberOfFunction > 3){
                    Printer.printFunctions();
                    numberOfFunction = Integer.parseInt(scanner.nextLine());
                }
                Functions.setNumberOfFunction(numberOfFunction);

                System.out.println(Printer.getBlueText("Введите количество значений:"));
                System.out.println(Printer.getYellowText("n > 2"));
                int n = Integer.parseInt(scanner.nextLine());
                while (n < 3){
                    System.out.println(Printer.getBlueText("Введите количество значений:"));
                    System.out.println(Printer.getYellowText("n > 2"));
                    n = Integer.parseInt(scanner.nextLine());
                }

                System.out.println(Printer.getBlueText("Введите список иксов:"));
                System.out.println(Printer.getYellowText("x1"));
                System.out.println(Printer.getYellowText("x2"));
                System.out.println(Printer.getYellowText("..."));
                HashSet<Double> setX = new HashSet<>();
                for (int i = 0; i < n; ++i){
                    Double[] xy = new Double[2];
                    xy[0] = Double.parseDouble(scanner.nextLine());

                    if (xy[0] <= 0 && numberOfFunction == 3){
                        n++;
                        System.out.println(Printer.getRedText("Икс не можеть быть отрицательным, потому что выбранная функция ln(x)"));
                        continue;
                    }

                    if (setX.contains(xy[0])) {
                        n++;
                        System.out.println(Printer.getRedText("Такой X уже существует"));
                        continue;
                    }

                    setX.add(xy[0]);
                    xy[1] = Functions.f(xy[0]);
                    values.add(xy);
                }

                System.out.println(Printer.getBlueText("Введите X, для которое нужно найти приблизительное значение:"));
                x = Double.parseDouble(scanner.nextLine());
                while(setX.contains(x)){
                    System.out.println(Printer.getBlueText("Введите X, для которое нужно найти приблизительное значение:"));
                    x = Double.parseDouble(scanner.nextLine());
                }

                Polynomial.setX(x);
                Polynomial.setValues(values);
            }
            default -> {
                scanner = new Scanner(new File("test.txt"));
                int n = Integer.parseInt(scanner.nextLine());
                System.out.println("n = " + n);

                HashSet<Double> setX = new HashSet<>();
                for (int i = 0; i < n; ++i){
                    String[] line = scanner.nextLine().split(" ");
                    Double[] xy = new Double[2];
                    xy[0] = Double.parseDouble(line[0]);

                    setX.add(xy[0]);
                    xy[1] = Double.parseDouble(line[1]);
                    System.out.println(Arrays.toString(xy));
                    values.add(xy);
                }

                x = Double.parseDouble(scanner.nextLine());
                System.out.println("x = " + x);

                Polynomial.setX(x);
                Polynomial.setValues(values);
            }
        }

    }


}
