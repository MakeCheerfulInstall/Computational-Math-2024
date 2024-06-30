package lab4.input;

import lab2.commands.Command;
import lab2.util.Point;
import lab4.Approximation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileInput implements Command {
    @Override
    public void execute() {
        var scanner = new Scanner(System.in);
        List<Point> points = new ArrayList<>();

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
        try (var reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rawData = line.trim().split(" ");
                var point = new Point(Double.parseDouble(rawData[0]), Double.parseDouble(rawData[1]));
                points.add(point);
            }
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Некорректные данные: " + e.getMessage());
            throw new RuntimeException();
        }

        Approximation.solve(points);
    }

    @Override
    public String getMessage() {
        return "Прочитать данные из файла";
    }
}
