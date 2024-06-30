package lab4.util;

import lab4.entity.Dot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

    public static Dot[] readDots() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[1] Ввод с клавиатуры\n[2] Ввод с файла");
        var str = scanner.nextLine().trim();
        switch (str) {
            case "1" -> {
                Printer.printInputExample();
                System.out.print(Printer.getBlueText("""
                Input number of dots:\s"""));
                int n = Integer.parseInt(scanner.nextLine());
                if (n < 8 || n > 12) throw new NumberFormatException();
                Dot[] dots = new Dot[n];
                System.out.println(Printer.getBlueText("Input dots:"));
                for (int i = 0; i < n; i++) {
                    String line = scanner.nextLine();
                    String[] xy = line.trim().split(" ");
                    double x = Double.parseDouble(xy[0].replace(",", "."));
                    double y = Double.parseDouble(xy[1].replace(",", "."));
                    dots[i] = new Dot(x, y);
                }
                return dots;
            }
            default -> {
                System.out.println("Введите название файла");
                var reader = new Scanner(new File(scanner.nextLine().trim()));
                int n = Integer.parseInt(reader.nextLine());
                if (n < 8 || n > 12) throw new NumberFormatException();
                Dot[] dots = new Dot[n];
                for (int i = 0; i < n; i++) {
                    String line = reader.nextLine();
                    String[] xy = line.trim().split(" ");
                    double x = Double.parseDouble(xy[0].replace(",", "."));
                    double y = Double.parseDouble(xy[1].replace(",", "."));
                    dots[i] = new Dot(x, y);
                }
                return dots;
            }
        }
    }
}