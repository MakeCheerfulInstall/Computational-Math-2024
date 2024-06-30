package lab3.solution;

import lab3.models.IFuncX;

import java.util.Scanner;

public class SimpsonIntegral {
    public void execute(IFuncX func) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите нижнюю границу:");
        double a = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите верхнюю границу:");
        double b = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите количество шагов:");
        double steps = 4;
        try {
            steps = Double.parseDouble(scanner.nextLine());
        } catch(Exception e) {}

        double answer = solve(func, a, b, steps);
        double forRate = solve(func, a, b, steps / 2);
        System.out.println("Ответ: " + answer);
        System.out.println("Погрешность: " + (answer - forRate) / ((double) 1 / 15));
    }

    public double solve(IFuncX func, double a, double b, double stepCount) {
        double h = (b - a) / stepCount;
        double answer = func.solve(a) + func.solve(b);

        for (double i = a + h, j = 1; i < b; i += h, j++) {
            double y = func.solve(i);
            answer += j % 2 != 0 ? y * 4 : y * 2;
        }
        answer *= h / 3;
        return answer;
    }
}
