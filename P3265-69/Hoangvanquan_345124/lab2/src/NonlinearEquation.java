import java.util.Scanner;

public class NonlinearEquation {
    private int functionNum;
    private double function1(double x){ return Math.pow(x, 3) - 2.92 * Math.pow(x, 2) + 4.435 * x + 0.791; }
    private double function2(double x){ return Math.pow(x, 3) - x + 4;}
    private double function3(double x){ return Math.sin(x) + 0.1; }
    public double apply(double x){
        switch (functionNum){
            case 1: return function1(x);
            case 2: return function2(x);
            default: return function3(x);
        }
    }
    public NonlinearEquation(){
        getInputFunction();
    }
    public NonlinearEquation(int functionNum){
        this.functionNum = functionNum;
    }
    public double d(int n, double x) {
        double h = 0.00000001;
        if (n <= 0) return Double.NaN;
        else if (n == 1) return (apply(x + h) - apply(x)) / h;
        return (d(n - 1, x + h) - d(n - 1, x)) / h;
    }
    private void getInputFunction(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите функцию");
        System.out.println(" 1) x³ - 2.92x² + 4.435x + 0.791");
        System.out.println(" 2) x³ - x + 4");
        System.out.println(" 3) sin(x) + 0.1");
        System.out.println("Выберите функцию из списка");
        functionNum = sc.nextInt();
    }

}