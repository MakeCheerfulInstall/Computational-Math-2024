package lab2.gui;

import lab2.algebra.EquationSolver;
import lab2.algebra.Function;
import lab2.algebra.NonLinearEquationSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Application {
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        {
            {
                {
                    try {
                        new Application().run();
                    } catch (Exception e) {
                        print("Ооой...%n");
                        e.printStackTrace();
                        exit(); }
                }
            }
        }
        new Graphic().run();
    }

    private final EquationSolver solver;
    {
        solver = new NonLinearEquationSolver();
    }

    private void run() throws FileNotFoundException {
        print("[1] Ввод из файла%n");
        print("[2] Ручной ввод%n");
        String inp = input();
        if (inp.equals("1")) {
            print("Введите название файла%n");
            scanner = new Scanner(new File(input()));
        }
        if (inp.equals("2") || inp.equals("1")) {
            print("[1] Нелинейное уравнение%n");
            print("[2] Система нелинейных уравнений%n");
            switch (input()) {
                case "1": {
                    nonlinearEquation();
                    break;
                }
                case "2":
                    systemOfEquations();
                    break;
            }
        } else {
            throw new RuntimeException();
        }
    }

    private void setAccuracy() {

        print("Точность в интервале (0, 1): ");

        double accuracy = Double.parseDouble(scanner.nextLine());
        if (0 >= accuracy || accuracy >= 1) {
            setAccuracy();
        }
        solver.setAccuracy(accuracy);
    }

    // Nonlinear equations...

    private void nonlinearEquation() {

        print("[a] %s%n", Math.EQUATION[0]);
        print("[b] %s%n", Math.EQUATION[1]);
        print("[c] %s%n", Math.EQUATION[2]);
        print("[d] %s%n", Math.EQUATION[3]);

        switch (input()) {
            case "a": solveNonlinearEquation(0);
                break;
            case "b": solveNonlinearEquation(1);
                break;
            case "c": solveNonlinearEquation(2);
                break;
            case "d": solveNonlinearEquation(3);
                break;
            default: throw new RuntimeException("Неверный ввод");
        }
    }

    private void solveNonlinearEquation(int equations) {
        Function[] functions = Math.EQUATIONS[equations];

        setAccuracy();

        print("a: ");
        var str = scanner.nextLine().trim().replace(',', '.');
        double a = Double.parseDouble(str);
        print("b: ");
        str = scanner.nextLine().trim().replace(',', '.');
        double b = Double.parseDouble(str);

        Object[][] res = new Object[3][2];

        print("%n\u001B[33mchord method:\u001B[0m ");
        try {
            Object[] result = solver.solveByChord(functions[0], a, b);

            res[0][0] = result[0];
            res[0][1] = result[3];

            print("[x = %.18f, Δx = %.18f, iters = %d times]%n", result[0], result[1], result[2]);
        }
        catch (RuntimeException e) {
            print("%s%n", e.getMessage().toLowerCase(Locale.ROOT));
        }


        print("%n\u001B[33miterative method:\u001B[0m ");
        try {
            Object[] result = solver.solveByIteration(functions[1], a, b);

            res[1][0] = result[0];
            res[1][1] = result[3];

            print("[x = %.18f, Δx = %.18f, iters = %d times]%n", result[0], result[1], result[2]);
        }
        catch (RuntimeException e) {
            print("%s%n", e.getMessage().toLowerCase(Locale.ROOT));
        }


        print("%n\u001B[33mNewton method:\u001B[0m ");
        try {
            Object[] result = solver.solveByNewton(functions[0], a, b);

            res[2][0] = result[0];
            res[2][1] = result[3];

            print("[x = %.18f, Δx = %.18f, iters = %d times]%n", result[0], result[1], result[2]);
        } catch (RuntimeException e) {
            print("%s%n", e.getMessage().toLowerCase(Locale.ROOT));
        }
        Graphic.setData(0, equations, res);
    }


    // System of nonlinear equations...

    private void systemOfEquations() {

        print("[a] %s%n    %s%n", Math.SYSTEM[0][0], Math.SYSTEM[0][1]);
        print("%n");
        print("[b] %s%n    %s%n", Math.SYSTEM[1][0], Math.SYSTEM[1][1]);

        switch (input()) {
            case "a": solveSystemOfEquations(0);
                break;
            case "b": solveSystemOfEquations(1);
                break;
            default: {
                System.out.println();
                throw new RuntimeException();
            }
        }
    }

    private void solveSystemOfEquations(int system) {
        Function[] systems = Math.SYSTEMS[system];

        setAccuracy();

        print("\u001B[33miterative method:\u001B[0m ");

        // [a, b, c, d]: a < x < b, c < y < d
        double[] G;

        if (system == 0) {
                G = new double[]{0d, 1d, 0d, 1d};
        } else {
            G = new double[]{0d, 1d, 0d, -1d};
        }

        Object[][] result = solver.solveByIterations(G, systems);

        Graphic.setData(1, system, result);

        print("[x = %.18f, Δx = %.18f]%n", result[0][0], result[0][1]);
        print("                 ");
        print("[y = %.18f, Δy = %.18f]%n", result[1][0], result[1][1]);

        print("                 [iters = %d]%n", result[2][0]);
    }

    // Utilities...

    private static void print(String pattern, Object... args) {
        System.out.printf(pattern, args);
    }

    private static void exit() {
        System.exit(0);
    }

    // Read a new line...

    private String input() {
        print("> ");
        return scanner.nextLine().trim().toLowerCase(Locale.ROOT);
    }
}
