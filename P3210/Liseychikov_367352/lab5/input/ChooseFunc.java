package lab5.input;

import lab2.commands.Command;
import lab2.module.MenuModule;
import lab2.util.FuncX;
import lab2.util.Point;
import lab5.Interpolation;
import lab5.Main;

import java.util.*;

public class ChooseFunc implements Command {

    @Override
    public String getMessage() {
        return "Выбрать функцию из списка";
    }

    @Override
    public void execute() {
        List<Command> commands = new ArrayList<>();
        HashMap<String, FuncX> funcHashMap = new HashMap<>();
        funcHashMap.put("x^2 - 3x", x -> Math.pow(x, 2) - 3 * x);
        funcHashMap.put("x^5", x -> Math.pow(x, 5));

        for (Map.Entry<String, FuncX> entry : funcHashMap.entrySet()) {
            commands.add(new Command() {
                @Override
                public String getMessage() {
                    return entry.getKey();
                }

                @Override
                public void execute() {
                    ChooseFunc.generateData(entry.getValue());
                }
            });
        }
        commands.add(new Main());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    public static void generateData(FuncX func) {
        double x;
        List<Point> points = new ArrayList<>();
        var scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите количество точек: ");
                int n = Integer.parseInt(scanner.nextLine());

                System.out.print("Введите левую и правую границу x в формате x1 x2: ");
                List<Double> arrX = Arrays.stream(scanner.nextLine().trim().split(" ")).map(Double::parseDouble).toList();
                double h = (arrX.get(1) - arrX.get(0)) / (n - 1);
                for (int i = 0; i < n; i++) {
                    double startX = arrX.get(0) + h * i;
                    var point = new Point(startX, func.solve(startX));
                    points.add(point);
                }
                System.out.print("Введите x для которого необходимо найти значение: ");
                x = Double.parseDouble(scanner.nextLine());

                break;
            } catch (Throwable e) {
                System.out.println(e);
                System.out.println("Что-то пошло не так, давайте попробуем снова");
            }
        }
        Interpolation.solve(points, x, func);
    }


}
