import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------------------------------------");
        System.out.println("ЧАСТЬ 1: ОБЯЗАТЕЛЬНОЕ ЗАДАНИЕ");
        IntegralOfFunction f1 = new IntegralOfFunction(1);
        f1.resultIntegral();
        while(true){
            System.out.println("Попробуйте с другими методами (+/-) ?");
            System.out.println("Режим ввода: ");
            char choice = sc.next().charAt(0);
            while(choice != '+' && choice != '-'){
                System.out.println("Введите '+' или '-' для выбора способа ввода.");
                System.out.println("Режим ввода: ");
            }
            if(choice == '+'){
                f1.GetMethod();
                f1.resultIntegral();
            }  else break;
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("ЧАСТЬ 2: НЕОБЯЗАТЕЛЬНОЕ ЗАДАНИЕ");
        IntegralOfFunction f2 = new IntegralOfFunction(2);
        f2.resultImproperIntegral();
    }
}