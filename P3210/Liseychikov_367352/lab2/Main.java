package lab2;

import lab2.commands.SimpleEquations;
import lab2.commands.SystemEquations;
import lab2.commands.Command;
import lab2.module.MenuModule;

import java.util.List;

public class Main implements Command {
    @Override
    public String getMessage() {
        return "Назад <--";
    }

    @Override
    public void execute() {
        List<Command> commands = List.of(new SimpleEquations(), new SystemEquations());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.execute();
    }
}
