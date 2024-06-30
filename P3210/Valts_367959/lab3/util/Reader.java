package util;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Reader {
    private static Scanner scanner = new Scanner(System.in);

    public static int readNumberOfFunction(){
        String numberOfFunction;
       do {
            System.out.println(Printer.getBlueText("Choose the function:"));
            Printer.printFunctions();
            numberOfFunction = scanner.nextLine();
            isOver(numberOfFunction);
        } while (!(numberOfFunction.equals("1")
                || numberOfFunction.equals("2")
                || numberOfFunction.equals("3")
                || numberOfFunction.equals("4")
                || numberOfFunction.equals("5")));
        return Integer.parseInt(numberOfFunction);
    }

    public static int readTypeOfMethod() {
        String typeOfMethod;
        do{
            System.out.println(Printer.getBlueText("Выберите метод:"));
            Printer.printMethods();
            typeOfMethod = scanner.nextLine();
            isOver(typeOfMethod);
        } while(!(typeOfMethod.equals("1")
               || typeOfMethod.equals("2")
               || typeOfMethod.equals("3")));
        return Integer.parseInt(typeOfMethod);
    }

    public static double readAccuracy() {
        while(true){
            try{
                System.out.print(Printer.getBlueText("""
                        Введите точность:\s"""));
                Scanner scanner = new Scanner(System.in);
                double accuracy = scanner.nextDouble();
                if (accuracy >= 1 || accuracy <= 0) throw new InputMismatchException();
                else return accuracy;
            }catch(InputMismatchException e){
                System.out.println(Printer.getRedText("Ошибка!"));
                System.out.println(Printer.getRedText("Не используйте в качестве разделителя точку! Нужна запятая"));
                System.out.println(Printer.getRedText("Точность должна быть больше 0 и меньше 1"));
            }
        }
    }

    public static double[] readAB() {
        double[] ab = new double[2];
        do{
            ab[0] = readA();
            ab[1] = readB();
        }while(ab[0]>=ab[1]);
        return ab;
    }

    private static double readA() {
        while(true){
            try{
                System.out.print(Printer.getBlueText("""
                        Введите a (нижняя граница интегрирования):\s"""));
                Scanner scanner = new Scanner(System.in);
                int a = scanner.nextInt();
                return a;
            }catch(InputMismatchException e){
                System.out.println(Printer.getRedText("Ошибка!"));
                System.out.println(Printer.getRedText("а - целочисленое"));
            }
        }
    }

    private static double readB() {
        while(true){
            try{
                System.out.print(Printer.getBlueText("""
                        Введите b (верхняя граниа интегрирования):\s"""));
                Scanner scanner = new Scanner(System.in);
                int b = scanner.nextInt();
                return b;
            }catch(InputMismatchException e){
                System.out.println(Printer.getRedText("Ошибка!"));
                System.out.println(Printer.getRedText("b - целочисленое"));
            }
        }
    }

    public static int readTypeOfRectangleMethod() {
        String typeOfRectangleMethod;
        do{
            System.out.println(Printer.getBlueText("Выберите тип метода прямоугольников:"));
            Printer.printRectangleMethods();
            typeOfRectangleMethod = scanner.nextLine();
            isOver(typeOfRectangleMethod);
        } while(!(typeOfRectangleMethod.equals("1")
                || typeOfRectangleMethod.equals("2")
                || typeOfRectangleMethod.equals("3")));
        return Integer.parseInt(typeOfRectangleMethod);
    }



    private static void isOver(String string){
        if (string.equals("/exit")){
            System.exit(0);
        }
    }
}
