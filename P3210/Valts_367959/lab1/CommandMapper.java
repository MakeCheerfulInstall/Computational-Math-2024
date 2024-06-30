package lab1;

import lab1.commands.*;
import lab1.exceptions.NotCommandFoundException;

import java.util.HashMap;
import java.util.Map;

public class CommandMapper {
    private final HashMap<String, ICommand> commands;
    public CommandMapper() {
        commands = new HashMap<>();
        commands.put("exit", new Exit());
        commands.put("help", new Help());
        commands.put("solve_matrix", new SolveMatrix());
    }

    public ICommand findCommand(String input) {
        ICommand c = commands.get(input);
        if (c == null) throw new NotCommandFoundException();

        return c;
    }

    public Map<String, ICommand> getCommands() {
        return commands;
    }
}
