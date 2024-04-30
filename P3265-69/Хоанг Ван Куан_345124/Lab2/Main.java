import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        System.out.println("---------------------------------------------------------");
        System.out.println("ЧАСТЬ 1: НЕЛИНЕЙНОЕ УПРАВЛЕНИЕ");
        char choice = inputMethod();
        SolveNonlinear result = new SolveNonlinear();
        if(choice == '+')  result.getdata_File();
        else{
            NonlinearEquation f = new NonlinearEquation();
            result.getdata_input(f);
        }
        result.Result();

        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("ЧАСТЬ 2: СИСТЕМ НЕЛИНЕЙНЫХ УРАВНЕНИЙ");
        SolveSystemNonlinear result2 = new SolveSystemNonlinear();
        result2.Solve();
    }
    static char inputMethod(){
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
}
