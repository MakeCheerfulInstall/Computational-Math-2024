package lab5;


import lab2.commands.Command;
import lab2.module.MenuModule;
import lab5.input.ChooseFunc;
import lab5.input.ConsoleInput;
import lab5.input.FileInput;

import java.util.List;

public class Main implements Command {
    public static void main(String[] args) {
        new Main().execute();
    }

    @Override
    public void execute() {
        List<Command> commands = List.of(new ConsoleInput(), new FileInput(), new ChooseFunc());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Назад <--";
    }

}