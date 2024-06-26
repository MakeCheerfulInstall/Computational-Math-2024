package util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {

    public int readIndex(String message, String notFoundMessage, int length) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(message);
                int val = scanner.nextInt();
                if (val <= 0) {
                    System.out.println("Требуется ввести положительное число.");
                    continue;
                }
                if (val - 1 >= length) {
                    System.out.println(notFoundMessage);
                    continue;
                }
                return val - 1;
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Требуется ввести целое число.");
            }
        }
    }

    public double readDouble(String message) {
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print(message);
                String buf = scanner.next();
                buf = buf.replaceAll(",", ".");
                return Double.parseDouble(buf);
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.println("Требуется ввести число c плавающей точкой.");
            }
        }
    }
}