package lab1;



import lab1.commands.ICommand;
import lab1.exceptions.NotCommandFoundException;
import lab1.exceptions.NotFileFoundException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private final CommandMapper commandMapper;
    private final IContext context;
    private final Scanner in;

    public Main() {
        commandMapper = new CommandMapper();
        in = new Scanner(System.in);
        context = new IContext() {
            double accuracy = 1E-15;// 0.00001D
            @Override
            public void print(String s) {
                System.out.print(s);
            }
            @Override
            public Scanner getReader() {
                return in;
            }
            @Override
            public void setAccuracy(double v) {
                this.accuracy = v;
            }
            @Override
            public double getAccuracy() {
                return accuracy;
            }
        };
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        String[] input;
        ICommand command;

        while (true) {
            context.print("--> ");
            input = context.getReader().nextLine().trim().split("(\\s++)");
            try {
                command = commandMapper.findCommand(input[0].toLowerCase());
                command.execute(context, input);
            } catch (NotCommandFoundException e) {
                context.print("Команда не найдена, напишите 'help'!\n");
            }catch (NotFileFoundException e) {
                context.print("Такой файл не существует\n");
            } catch (ArrayIndexOutOfBoundsException e) {
                context.print("Введены неверные аргументы...\n");
            } catch (NoSuchElementException e) {
                context.print(e.getMessage()+ "\n");
            } catch (NumberFormatException e) {
                context.print("Введенное число имеет неверный формат\n");
            }
        }
    }
}
