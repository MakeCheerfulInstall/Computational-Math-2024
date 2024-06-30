package lab4;


import lab2.commands.Command;
import lab2.module.MenuModule;
import lab4.input.ConsoleInput;
import lab4.input.FileInput;

import java.util.List;

public class Main implements Command {
    public static void main(String[] args) {
        new Main().execute();
    }

    @Override
    public void execute() {
        List<Command> commands = List.of(new ConsoleInput(), new FileInput());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Назад <--";
    }

}