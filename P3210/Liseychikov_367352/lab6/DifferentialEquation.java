package lab6;

import lab2.commands.Command;
import lab2.module.MenuModule;
import lab6.output.GraphModule;
import lab6.solver.Adams;
import lab6.solver.Euler;
import lab6.solver.FuncXY;
import lab6.solver.RungeKutta;

import java.util.ArrayList;
import java.util.List;

public class DifferentialEquation {
    public static void solve(double start, double end, double y0, double h, double e, FuncXY funcXY) {
        List<Command> commands = new ArrayList<>();
        commands.add(new Command() {
            @Override
            public String getMessage() {
                return "Метод Эйлера";
            }

            @Override
            public void execute() {
                System.out.println();
                System.out.println("Решение методом Эйлера:");
                double newH = h;
                double[][] result = new Euler().solve(start, end, y0, newH, e, funcXY);

                double inaccuracy = Double.MAX_VALUE;
                while (inaccuracy > e) {
                    newH = newH / 2;
                    double[][] newResult = new Euler().solve(start, end, y0, newH, e, funcXY);
                    double coeff = 3;
                    inaccuracy = Math.abs(newResult[newResult.length - 1][1] - result[result.length - 1][1]) / coeff;
                    System.out.println("Точность по правилу Рунге: " + inaccuracy + " для шага h = " + newH);
                    result = newResult;
                }

                pritnTable(result, 3);
                GraphModule.draw(result, "Метод Эйлера", funcXY);
            }
        });

        commands.add(new Command() {
            @Override
            public String getMessage() {
                return "Метод Рунге-Кутта 4 порядка";
            }

            @Override
            public void execute() {
                System.out.println();
                System.out.println("Решение методом Рунге-Кутта 4 порядка:");
                double newH = h;
                double[][] rungeKuttaResult = new RungeKutta().solve(start, end, y0, newH, e, funcXY);

                double inaccuracy = Double.MAX_VALUE;
                while (inaccuracy > e) {
                    newH = newH / 2;
                    double[][] newResult = new RungeKutta().solve(start, end, y0, newH, e, funcXY);
                    double coeff = 15;
                    inaccuracy = Math.abs(newResult[newResult.length - 1][1] - rungeKuttaResult[rungeKuttaResult.length - 1][1]) / coeff;
                    System.out.println("Точность по правилу Рунге: " + inaccuracy + " для шага h = " + newH);
                    rungeKuttaResult = newResult;
                }

                pritnTable(rungeKuttaResult, 3);
                GraphModule.draw(rungeKuttaResult, "Метод Рунге-Кутта 4 порядка", funcXY);
            }
        });

        commands.add(new Command() {
            @Override
            public String getMessage() {
                return "Метод Адамса";
            }

            @Override
            public void execute() {
                System.out.println();
                System.out.println("Решение методом Адамса:");
                double newH = h;
                double[][] adamsResult = new Adams().solve(start, end, y0, newH, e, funcXY);

                double inaccuracy = Double.MAX_VALUE;
                while (inaccuracy > e) {
                    newH = h / 2;
                    double[][] newResult = new Adams().solve(start, end, y0, newH, e, funcXY);
                    double max = Double.MIN_VALUE;
                    for (int i = 0; i < adamsResult.length; i++) {
                        double temp = Math.abs(funcXY.solve(adamsResult[i][0]) - newResult[i][1]);
                        if (temp > max) {
                            max = temp;
                        }
                    }
                    inaccuracy = max;
                    System.out.println("Точность для метода Адамса: " + inaccuracy + " для шага h = " + newH);
                    adamsResult = newResult;
                }

                pritnTable(adamsResult, 3);
                GraphModule.draw(adamsResult, "Метод Адамса", funcXY);
            }
        });

        commands.add(new Main());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    private static void pritnTable(double[][] result, double e) {
        System.out.printf("%-20s %-30s %-30s\n", "x", "y", "f(x, y)");
        for (double[] doubles : result) {
            System.out.printf("%-20.2f %-30.2f %-30.2f\n",
                    (Math.round(doubles[0] * Math.pow(10, e)) / Math.pow(10, e))
                    , (Math.round(doubles[1] * Math.pow(10, e)) / Math.pow(10, e))
                    , (Math.round(doubles[2] * Math.pow(10, e)) / Math.pow(10, e)));
        }
    }
}
