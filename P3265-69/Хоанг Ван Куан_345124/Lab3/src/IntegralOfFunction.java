import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class IntegralOfFunction {
    static final String FILE_INPUT_1 = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\IdeaProjects\\Math\\Lab3\\src\\Input.txt";
    static final String FILE_INPUT_2 = "C:\\Users\\asgat\\OneDrive\\Máy tính\\WorkSpace\\IdeaProjects\\Math\\Lab3\\src\\Input2.txt";
    Scanner sc = new Scanner(System.in);
    private int FuncNum;
    private int MethodNum;
    private double a, b;
    private double epsilon;
    private int nmin;
    private int k;
    private int part;
    private double Func1(double x){ return 2 * Math.pow(x,3) - 4 * Math.pow(x,2) + 6 * x - 25;}
    private double Func2(double x){ return 1/10 * Math.pow(x,4) + 1/5 * Math.pow(x,2) - 7;}
    private double Func3(double x){ return Math.pow(x,3) - 2 * Math.pow(x,2) - 5 * x + 7;}
    private double Func4(double x){ return Math.pow(x,2);}
    private double Func5(double x){
        c = 0;
        return 1/Math.pow(x,2);
    }
    private double Func6(double x){
        c = 0;
        return 1/Math.sqrt(x);
    }
    private double Func7(double x){
        c = 1;
        return 1/(1-x);
    }

    // допольнительные функции
    private void GetFunction(){
        if(part == 1){
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
        }
        else{
            System.out.println("Выберите функцию");
            System.out.println(" 5) 1/x²");
            System.out.println(" 6) 1/√x");
            System.out.println(" 7) 1/(1-x)");
            System.out.println("Выберите функцию из списка");
            FuncNum = sc.nextInt();
            while(FuncNum < 5 || FuncNum > 7){
                System.out.println("Выберите функцию из списка");
                FuncNum = sc.nextInt();
            }
        }
    }
    public void GetMethod(){
        System.out.println("Выберите функцию");
        System.out.println(" 1) Метод левых прямоугольников");
        System.out.println(" 2) Метод средних прямоугольников");
        System.out.println(" 3) Метод правых прямоугольников");
        System.out.println(" 4) Метод трапеций");
        System.out.println(" 5) Метод Сипсона");
        System.out.println("Выберите метод из списка");
        MethodNum = sc.nextInt();
        while(MethodNum < 1 || MethodNum > 5){
            System.out.println("Выберите метод из списка");
            MethodNum = sc.nextInt();
        }
    }
    private void GetDataInput(){
        GetFunction();
        GetMethod();
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
            Scanner sc;
            if(part == 1) sc = new Scanner(new File(FILE_INPUT_1));
            else sc = new Scanner(new File(FILE_INPUT_2));
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
            case 4: return Func4(x);
            case 5: return Func5(x);
            case 6: return Func6(x);
            default: return Func7(x);
        }
    }
    // Конструктор
    public IntegralOfFunction(int part){
        this.part = part;
        char choice = inputMethod();
        if(choice == '+') GetDataFromFile();
        else GetDataInput();
    }
    // реализация методов
    private double LeftRectangleMethod(double a, double b, double h){
        double res = 0;
        for(double i = a; i < b - h/2; i += h) res += h * apply(i);
        return res;
    }
    private double RightRectangleMethod(double a, double b, double h){
        double res = 0;
        for(double i = a + h; i < b + h/2; i += h) res += h * apply(i);
        return res;
    }
    private double MiddleRectangleMethod(double a, double b, double h){
        double res = 0;
        for(double i = a + h/2; i < b; i += h) res += h * apply(i);
        return res;
    }
    private double TrapezoidMethod(double a, double b, double h){
        double sum = 0;
        for(double i = a + h; i < b - h/2; i += h)
            sum += apply(i);
        return h*((apply(a) + apply(b))/2 + sum);
    }
    private double SimpsonMethod(double a, double b, double h){
        double sum1 = 0;
        double sum2 = 0;
        for(double i = a + h; i < b - h/2; i += 2*h) sum1 += apply(i);
        for(double i = a + 2*h; i < b - h/2; i += 2*h) sum2 += apply(i);
        return h/3*(apply(a) + 4*sum1 + 2*sum2 + apply(b));
    }
    private double supMethod(double a, double b, double h){
        switch (MethodNum){
            case 1:
                k = 2;
                return LeftRectangleMethod(a, b, h);
            case 2:
                k = 2;
                return RightRectangleMethod(a, b, h);
            case 3:
                k = 2;
                return MiddleRectangleMethod(a, b, h);
            case 4:
                k = 2;
                return TrapezoidMethod(a, b, h);
            default:
                k = 4;
                return SimpsonMethod(a, b, h);
        }
    }
    private double RungeRule(double res, double res2){
        return Math.abs(res2 - res)/(Math.pow(2,k) - 1);
    }
    // Вывод
    private Result subResul(double a, double b){
        Result result = new Result();
        result.n = nmin;
        do{
            result.res = supMethod(a,b,Math.abs(b-a)/result.n);
            result.n *= 2;
            result.res2 = supMethod(a,b,Math.abs(b-a)/ result.n);
        } while(Math.abs(result.res2 - result.res) > epsilon);
        return result;
    }
    public void resultIntegral(){
        subResul(a,b).print();
    }
    // Допольнительная часть
    private double c; // Особая точка подынтегральной функции
    public void resultImproperIntegral(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Интеграл не существует");
                System.exit(0);
            }
        }, 3000);
        if(a <= c && c <= b){
            Result res = new Result();
            try{
                double e = 0.0001;
                if(a == c) res = subResul(a + e,b);
                else if(b == c) res = subResul(a, b - e);
                else {
                    Result res1 = subResul(a,c - e);
                    Result res2 = subResul(c + e,b);
                    res.res = res1.res + res2.res;
                    res.res2 = res1.res2 + res2.res;
                    res.n = Math.max(res1.n, res2.n);
                }
            } catch (Exception e){
                System.out.println("Интеграл не существует");
                return;
            }
            if(res.res == Double.NaN)  System.out.println("Интеграл не существует");
            else res.print();
            System.exit(0);
        }
        else{
            resultIntegral();
            System.exit(0);
        }

    }
    private class Result{
        double res, res2;
        int n;
        public void print(){
            System.out.println("Значение интеграла: " + res);
            System.out.println("Число разбиения: " + n/2);
            System.out.println("Погрешность по правилу Рунге: " + RungeRule(res, res2));
        }
    }
}
