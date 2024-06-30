package lab6.input;

import lab2.commands.Command;
import lab2.module.MenuModule;
import lab6.DifferentialEquation;
import lab6.Main;
import lab6.solver.FuncXY;

import java.util.*;

public class ChooseFunc implements Command {

    @Override
    public String getMessage() {
        return "Выбрать функцию из списка";
    }

    @Override
    public void execute() {
        List<Command> commands = new ArrayList<>();
        HashMap<String, FuncXY> funcHashMap = new HashMap<>();
        funcHashMap.put("y + (1 + x) * y^2", new FuncXY() {
            @Override
            public Double solve(double x, double y) {
                return y + (1 + x) * y * y;
            }

            @Override
            public Double solve(double x) {
                return -1 / x;
            }
        });
        funcHashMap.put("x^2", new FuncXY() {
            @Override
            public Double solve(double x, double y) {
                return Math.pow(x, 2);
            }

            @Override
            public Double solve(double x) {
                return Math.pow(x, 3) / 3 + 5;
            }
        });
        funcHashMap.put("2x", new FuncXY() {
            @Override
            public Double solve(double x, double y) {
                return 2 * x;
            }

            @Override
            public Double solve(double x) {
                return Math.pow(x, 2);
            }
        });

        for (Map.Entry<String, FuncXY> entry : funcHashMap.entrySet()) {
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

    public static void generateData(FuncXY func) {
        double y0, e, h;
        List<Double> arrX;
        List<Double> pointsX = new ArrayList<>();
        var scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Введите левую и правую границу интервала дифференцирования в формате x1 x2: ");
                arrX = Arrays.stream(scanner.nextLine().trim().split(" ")).map(Double::parseDouble).toList();

                System.out.print("Введите шаг h: ");
                h = Double.parseDouble(scanner.nextLine().trim());

                double curX = arrX.get(0);
                while (curX <= arrX.get(1)) {
                    pointsX.add(curX);
                    curX += h;
                }

                System.out.print("Введите y0: ");
                y0 = Double.parseDouble(scanner.nextLine());

                System.out.print("Введите точность: ");
                e = Double.parseDouble(scanner.nextLine());

                break;
            } catch (Throwable err) {
                System.out.println(err);
                System.out.println("Что-то пошло не так, давайте попробуем снова");
            }
        }
        DifferentialEquation.solve(arrX.get(0), arrX.get(1), y0, h, e, func);
    }

}
