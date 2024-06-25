package lab1.commands;

import lab1.IContext;

public interface ICommand {
    void execute(IContext context, String[] args);
}
