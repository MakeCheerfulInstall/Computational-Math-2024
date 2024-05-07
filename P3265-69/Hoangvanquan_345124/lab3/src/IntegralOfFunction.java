import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class IntegralOfFunction {
    private class Result{
        double res;
        double res2;
        double n;
    }
    static final String FILE_INPUT = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\IdeaProjects\\Math\\Lab3\\src\\Input.txt";
    Scanner sc = new Scanner(System.in);
    private int FuncNum;
    private int MethodNum;
    private double a, b;
    private double epsilon;
    private int nmin;
    private int k;
    private double Func1(double x){ return 2 * Math.pow(x,3) - 4 * Math.pow(x,2) + 6 * x - 25;}
    private double Func2(double x){ return 1/10 * Math.pow(x,4) + 1/5 * Math.pow(x,2) - 7;}
    private double Func3(double x){ return Math.pow(x,3) - 2 * Math.pow(x,2) - 5 * x + 7;}
    private double Func4(double x){ return Math.pow(x,2);}

    // допольнительные функции
    private void GetDataInput(){
        System.out.println("Выберите функцию");
        System.out.println(" 1) 2x³ - 4x² + 6x -25");
        System.out.println(" 2) 1/10х⁴ + 1/5x² - 7");
        System.out.println(" 3) x³ - 2x² - 5x + 7");
        System.out.println(" 4) x²");
        System.out.println("Выберите функцию из списка");
        FuncNum = sc.nextInt();
        while(FuncNum < 1 || FuncNum > 4){
            System.out.println("Выберите функцию из списка");
            FuncNum = sc.nextInt();
        }

        System.out.println("Выберите функцию");
        System.out.println(" 1) Метод левых прямоугольников");
        System.out.println(" 2) Метод средних прямоугольников");
        System.out.println(" 3) Метод правых прямоугольников");
        System.out.println(" 4) Метод трапеций");
        System.out.println(" 5) Метод Сипсона");
        System.out.println("Выберите метод из списка");
        MethodNum = sc.nextInt();
        while(FuncNum < 1 || FuncNum > 5){
            System.out.println("Выберите метод из списка");
            FuncNum = sc.nextInt();
        }

        System.out.println("Введите пределы интегрирования:");
        a = sc.nextDouble();
        b = sc.nextDouble();
        System.out.println("Введите точность вычисления: ");
        epsilon = sc.nextDouble();
        System.out.println("Введите начальное значение числа разбиения:");
        nmin = sc.nextInt();

    }
    private void GetDataFromFile(){
        try{
            Scanner sc = new Scanner(new File(FILE_INPUT));
            FuncNum = sc.nextInt();
            MethodNum = sc.nextInt();
            a = sc.nextDouble();
            b = sc.nextDouble();
            epsilon = sc.nextDouble();
            nmin = sc.nextInt();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Файл не доступен");
        }
    }
    private char inputMethod(){
        System.out.println("Взять исходные данные из файла (+) или ввести с клавиатуры (-)?");
        System.out.println("Режим ввода: ");
        char choice = sc.next().charAt(0);
        while(choice != '+' && choice != '-'){
            System.out.println("Введите '+' или '-' для выбора способа ввода.");
            System.out.println("Режим ввода: ");
            choice = sc.next().charAt(0);
        }
        return choice;
    }
    private double apply(double x){
        switch (FuncNum){
            case 1: return Func1(x);
            case 2: return Func2(x);
            case 3: return Func3(x);
            default: return Func4(x);
        }
    }
    // конструктор
    public IntegralOfFunction(){
        char choice = inputMethod();
        if(choice == '+') GetDataFromFile();
        else GetDataInput();
    }
    // нужные функции
    public double LeftRectangleMethod(double h){
        double res = 0;
        for(double i = a; i < b - h/2; i += h) res += h * apply(i);
        return res;
    }
    public double RightRectangleMethod(double h){
        double res = 0;
        for(double i = a + h; i < b + h/2; i += h) res += h * apply(i);
        return res;
    } // true
    public double MiddleRectangleMethod(double h){
        double res = 0;
        for(double i = a + h/2; i < b; i += h) res += h * apply(i);
        return res;
    } //true
    public double TrapezoidMethod(double h){
        double sum = 0;
        for(double i = a + h; i < b - h/2; i += h)
            sum += apply(i);
        return h*((apply(a) + apply(b))/2 + sum);
    } // true
    public double SimpsonMethod(double h){
        double sum1 = 0;
        double sum2 = 0;
        for(double i = a + h; i < b - h/2; i += 2*h) sum1 += apply(i);
        for(double i = a + 2*h; i < b - h/2; i += 2*h) sum2 += apply(i);
        return h/3*(apply(a) + 4*sum1 + 2*sum2 + apply(b));
    }
    private double supResult(double h){
        switch (MethodNum){
            case 1:
                k = 2;
                return LeftRectangleMethod(h);
            case 2:
                k = 2;
                return RightRectangleMethod(h);
            case 3:
                k = 2;
                return MiddleRectangleMethod(h);
            case 4:
                k = 2;
                return TrapezoidMethod(h);
            default:
                k = 4;
                return SimpsonMethod(h);
        }
    }
    public void resultIntegral(){
        double res, res2;
        int n;
        do{
            n = nmin;
            double h = Math.abs(b-a)/nmin;
            res = supResult(h);
            nmin *= 2;
            h = Math.abs(b-a)/nmin;
            res2 = supResult(h);
        } while(Math.abs(res2 - res) > epsilon);

        System.out.println("Значение интеграла: " + res);
        System.out.println("Число разбиения: " + n);
        System.out.println("Погрешность по правилу Рунге: " + RungeRule(res, res2));
    }
    public double RungeRule(double res, double res2){
        return Math.abs(res2 - res)/(Math.pow(2,k) - 1);
    }
    public boolean infOrError(double number) {
        try {
            double res = apply(number);
            return Double.isInfinite(res);
        } catch (Exception e) {
            return true;
        }
    }
    public boolean diverges(double a, double b) {
        return infOrError(a) || infOrError(b);
    }

}