package lab6;


import lab6.math.Functions;
import lab6.math.MethodHandler;
import lab6.util.Printer;

public class Main {
    public static void main( String[] args ) {
        int input;
        do {
            input = lab6.util.Reader.methodInput();
            if (input == 4) break;
            MethodHandler.setMethodNumber(input);
            lab6.math.Functions.setFunctionNumber(lab6.util.Reader.functionSelection());
            double a = lab6.util.Reader.inputA();
            double b = lab6.util.Reader.inputB();
            double h = lab6.util.Reader.inputH();
            double y0 = lab6.util.Reader.inputY();
            double eps = lab6.util.Reader.inputEps();
            int e = lab6.util.Reader.inputE();
            double[][] result = lab6.math.MethodHandler.execute(a, b, y0, h, eps);
            double inaccuracy = Double.MAX_VALUE;
            while (inaccuracy > eps) {
                h = h / 2;
                double[][] newResult = lab6.math.MethodHandler.execute(a, b, y0, h, eps);
                if (input == 3) {
                    double max = Double.MIN_VALUE;
                    for (int i = 0; i < result.length; i++) {
                        double temp = Math.abs(Functions.solution(result[i][0]) - newResult[i][1]);
                        if (temp > max) {
                            max = temp;
                        }
                    }
                    inaccuracy = max;
                    System.out.println(Printer.getYellowText("Точность для метода Милна: ")
                            + Printer.getGreenText(String.valueOf(inaccuracy)) +
                            Printer.getYellowText(" для шага h = ") + Printer.getGreenText(String.valueOf(h)));
                } else {
                    int p;
                    p = input == 2 ? 4 : 2;
                    double coeff = Math.pow(2, p) - 1;
                    inaccuracy = Math.abs(newResult[newResult.length - 1][1] - result[result.length - 1][1]) / coeff;
                    System.out.println(Printer.getYellowText("Точность по правилу Рунге: ") +
                            Printer.getGreenText(String.valueOf(inaccuracy)) +
                            Printer.getYellowText(" для шага h = ") + Printer.getGreenText(String.valueOf(h)));
                }
                result = newResult;
            }
            lab6.util.Printer.printInterval(a, b, h);
            lab6.util.Printer.printTable(result, e);
            String str = " Эйлера";
            if (input == 2) str = " Рунге-Кутта 4 порядка";
            if (input == 3) str = " Милна";
            lab6.util.Drawer.draw(result, "Метод" + str);
        } while (true);
        System.out.println(Printer.getRedText(">>> Завершение работы <<<"));
    }
}
