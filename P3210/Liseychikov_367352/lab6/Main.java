package lab6;

import lab2.commands.Command;
import lab2.module.MenuModule;
import lab6.input.ChooseFunc;

import java.util.List;

public class Main implements Command {
    public static void main(String[] args) {
        new lab6.Main().execute();
    }

    @Override
    public void execute() {
        List<Command> commands = List.of(new ChooseFunc());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Назад <--";
    }

}
