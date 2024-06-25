package lab3.commands;

import lab2.commands.Command;
import lab2.module.MenuModule;

import java.util.ArrayList;
import java.util.List;

public class Integration implements Command {
    @Override
    public void execute() {
        List<Command> commands = new ArrayList<>();
        commands.add(new NumericalIntegrationTrapezoid());
        commands.add(new NumericalIntegrationRectangle());
        commands.add(new Simpson());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "<-- Назад";
    }
}