package lab5.input;

import lab2.commands.Command;
import lab2.util.Point;
import lab5.Interpolation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleInput implements Command {
    @Override
    public String getMessage() {
        return "Ввести данные в консоль";
    }

    @Override
    public void execute() {
        double x;
        List<Point> points = new ArrayList<>();
        var scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите количество точек: ");
                int n = Integer.parseInt(scanner.nextLine());
                int i = 0;
                while (i < n) {
                    try {
                        System.out.print(i + 1 + ") Введите точку в формате x y: ");
                        String[] rawData = scanner.nextLine().trim().split(" ");
                        var point = new Point(Double.parseDouble(rawData[0]), Double.parseDouble(rawData[1]));
                        points.add(point);
                        i++;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException exp) {
                        System.out.println("Неверный ввод. Попробуй снова");
                    }
                }
                System.out.print("Введите x для которого необходимо найти значение: ");
                x = Double.parseDouble(scanner.nextLine());

                break;
            } catch (Throwable e) {
                System.out.println(e);
                System.out.println("Что-то пошло не так, давайте попробуем снова");
            }
        }
        Interpolation.solve(points, x, null);
    }
}
