package lab3.solution;

import lab3.models.IFuncX;
import lab3.models.Separation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class RectangleIntegral {
    static Scanner scanner = new Scanner(System.in);

    public static void solve(IFuncX f) {
        System.out.println("Введите a:");
        double a = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите b:");
        double b = Double.parseDouble(scanner.nextLine());
        System.out.println("Введите погрешность:");
        double eps = Double.parseDouble(scanner.nextLine());
        ArrayList<Separation> array = findSeparations(f, a, b);
        double sum_left = 0.0d;
        double sum_right = 0.0d;
        double sum_mid = 0.0d;
        for (Separation s : array) {
            ResultSet result = solve(f, s.getLeft(), s.getRight(), eps);
            sum_left += result.getLeft();
            sum_right += result.getRight();
            sum_mid += result.getMid();
            System.out.println("Результаты для отрезка от " + s.getLeft() + " до " + s.getRight() + ":");
            System.out.println("Левые прямоугольники: " + String.format("%.8f", result.getLeft()) + " eps: " + String.format("%.8f", result.getEpsLeft()));
            System.out.println("Правые прямоугольники: " + String.format("%.8f", result.getRight()) + " eps: " + String.format("%.8f", result.getEpsRight()));
            System.out.println("Средние прямоугольники: " + String.format("%.8f", result.getMid()) + " eps: " + String.format("%.8f", result.getEpsMid()));
        }
        System.out.println("Сумма для метода левых прямоугольников: " + round(sum_left, 8));
        System.out.println("Сумма для метода правых прямоугольников: " + round(sum_right, 8));
        System.out.println("Сумма для метода средних прямоугольников: " + round(sum_mid, 8));
    }

    //функция для вычисления интеграла методом левых прямоугольников
    static double left_rectangle_integral(IFuncX f, double a, double b, int n) {
        double step;
        double sum = 0;
        step = (b - a) / n;
        for (double i = a; i < b; i += step) {
            sum += f.solve(i);
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        return sum * step;//множим на величину шага и возвращаем в вызывающую функцию
    }

    static double right_rectangle_integral(IFuncX f, double a, double b, int n) {
        double step;
        double sum = 0;
        step = (b - a) / n;
        for (int i = 1; i <= n; i++) {
            sum += f.solve(a + i * step);
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        return sum * step;//множим на величину шага и возвращаем в вызывающую функцию
    }

    static double mid_rectangle_integral(IFuncX f, double a, double b, int n) {
        double sum = 0;
        double step = (b - a) / n;
        for (int i = 0; i < n; i++) {
            sum += f.solve(a + step * (i + 0.5));//0.5 это тип 1/2
        }
        //приближенное значение интеграла равно сумме площадей прямоугольников
        return sum * step;//множим на величину шага и возвращаем в вызывающую функцию
    }

    static ArrayList<Separation> findSeparations(IFuncX func, double a, double b) {
        ArrayList<Separation> array = new ArrayList<>();
        double eps = 0.00000001;
        int scale = 8; // Количество знаков после запятой.
        // Проверка первого элемента
        Double first = func.solve(round(a, scale));
        double left_now = first.isNaN() || first.isInfinite() ? a + eps : a;
        // Проверка элементов (a, b)
        if (a <= b) {
            for (double i = a + 0.0001; i < b; i += 0.0001) {
                if (func.solve(round(i, scale)).isNaN() || func.solve(round(i, scale)).isInfinite()) {
                    array.add(new Separation(left_now, i - eps));
                    left_now = i + eps;
                }
            }
        } else {
            for (double i = a; i > b; i -= 0.0001) {
                if (func.solve(round(i, scale)).isNaN() || func.solve(round(i, scale)).isInfinite()) {
                    array.add(new Separation(left_now, i + eps));
                    left_now = i - eps;
                }
            }
        }
        // Проверка последнего элемента
        Double end = func.solve(round(b, scale));
        end = end.isNaN() || end.isInfinite() ? b - eps : b;
        array.add(new Separation(left_now, end));
        return array;
    }

    public static Double round(double x, int scale) {
        try {
            double rounded = (new BigDecimal(Double.toString(x))).setScale(scale, 4).doubleValue();
            return rounded == 0.0D ? 0.0D * x : rounded;
        } catch (NumberFormatException var6) {
            return Double.isInfinite(x) ? x : Double.NaN;
        }
    }


    public static ResultSet solve(IFuncX f, double a, double b, double eps) {
        ResultSet resultSet = new ResultSet();
        // left
        int iter = 0;
        int n = 1; //начальное число шагов
        double s, s1 = left_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = left_rectangle_integral(f, a, b, n);
            iter++;
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setLeft(s1);

        // left eps
        iter *= 2;
        n = 1; //начальное число шагов
        s1 = left_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = left_rectangle_integral(f, a, b, n);
        }
        while (iter-- > 0);  //сравнение приближений с заданной точностью
        resultSet.setEpsLeft(Math.abs(resultSet.getLeft() - s1));

        // right
        iter = 0;
        n = 1; //начальное число шагов
        s1 = right_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = right_rectangle_integral(f, a, b, n);
            iter++;
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setRight(s1);

        // right eps
        iter *= 2;
        n = 1; //начальное число шагов
        s1 = right_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = right_rectangle_integral(f, a, b, n);
        }
        while (iter-- > 0);  //сравнение приближений с заданной точностью
        resultSet.setEpsRight(Math.abs(resultSet.getRight() - s1));

        // mid
        iter = 0;
        n = 1; //начальное число шагов
        s1 = mid_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = mid_rectangle_integral(f, a, b, n);
            iter++;
        }
        while (Math.abs(s1 - s) > eps);  //сравнение приближений с заданной точностью
        resultSet.setMid(s1);

        // mid eps
        iter *= 2;
        n = 1; //начальное число шагов
        s1 = mid_rectangle_integral(f, a, b, n); //первое приближение для интеграла
        do {
            s = s1;     //второе приближение
            n = 2 * n;  //увеличение числа шагов в два раза,
            //т.е. уменьшение значения шага в два раза
            s1 = mid_rectangle_integral(f, a, b, n);
        }
        while (iter-- > 0);  //сравнение приближений с заданной точностью
        resultSet.setEpsMid(Math.abs(resultSet.getMid() - s1));
        return resultSet;
    }
}

class ResultSet {
    private double left;
    private double right;
    private double mid;
    private double epsLeft;
    private double epsRight;
    private double epsMid;

    public ResultSet() {

    }

    /*
    Getter and Setter
     */
    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getMid() {
        return mid;
    }

    public void setMid(double mid) {
        this.mid = mid;
    }

    public double getEpsLeft() {
        return epsLeft;
    }

    public void setEpsLeft(double epsLeft) {
        this.epsLeft = epsLeft;
    }

    public double getEpsRight() {
        return epsRight;
    }

    public void setEpsRight(double epsRight) {
        this.epsRight = epsRight;
    }

    public double getEpsMid() {
        return epsMid;
    }

    public void setEpsMid(double epsMid) {
        this.epsMid = epsMid;
    }
}
