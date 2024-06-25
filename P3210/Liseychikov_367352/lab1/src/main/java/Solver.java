import command.ConsoleCommand;
import command.FileCommand;
import command.RandomCommand;

import java.util.Scanner;

public class Solver {
    private void printHeader() {
        System.out.print("\n");
        System.out.println("\t\t\t\t\t\t\t\t Вычислительная математика");
        System.out.println("\t\t\t\t\t\t\t\t  Лисейчиков Глеб P3210");
        System.out.println("\t\t\t\t\t\t\t\t\t\t Вариант 6");
        System.out.print("\n\n");
    }

    private void printSelectInput() {
        System.out.println("Введите число, чтобы выбрать через что будет осуществляться ввод данных:");
        System.out.println("1 - консоль");
        System.out.println("2 - файл");
        System.out.println("3 - случайная генерация");
    }

    private void printGoodbye() {
        System.out.println("Пока-пока");
    }

    public void solve() {
        printHeader();
        printSelectInput();

        var scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        try {
            int number = Integer.parseInt(result);

            switch (number) {
                case 1 -> new ConsoleCommand().execute();
                case 2 -> new FileCommand().execute();
                case 3 -> new RandomCommand().execute();
                default -> printGoodbye();
            }

        } catch (RuntimeException exp) {
            System.out.println(exp.getMessage());
            printGoodbye();
        }
    }
}
