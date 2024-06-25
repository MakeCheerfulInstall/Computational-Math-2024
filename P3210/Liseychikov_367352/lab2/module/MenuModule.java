package lab2.module;


import lab2.commands.Command;

import java.util.List;
import java.util.Scanner;

public class MenuModule {
    List<Command> commands;

    public MenuModule(List<Command> commands) {
        this.commands = commands;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Меню:");
                int i = 1;
                for (Command command : commands) {
                    System.out.println(i++ + ". " + command.getMessage());
                }
                if (scanner.hasNext()) {
                    String result = scanner.nextLine();
                    if (isNumeric(result) && Integer.parseInt(result) > 0 && Integer.parseInt(result) <= commands.size()) {
                        commands.get(Integer.parseInt(result) - 1).execute();
                    } else {
                        System.out.println("Такого варианта нет. Попробуйте ещё раз");
                    }
                } else {
                    System.out.println("Завершение работы");
                    System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
