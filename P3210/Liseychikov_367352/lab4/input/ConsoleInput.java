package lab4.input;

import lab2.commands.Command;
import lab2.util.Point;
import lab4.Approximation;

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
        List<Point> points = new ArrayList<>();
        var scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите количество точек (8 <= n <= 12): ");
                int n = Integer.parseInt(scanner.nextLine());
                if (n > 12 || n < 8) {
                    throw new Exception();
                }
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
                break;
            } catch (Throwable e) {
                System.out.println(e);
                System.out.println("Что-то пошло не так, давайте попробуем снова");
            }
        }
        Approximation.solve(points);
    }
}
