package lab1.commands;


import lab1.IContext;

public class Exit implements ICommand {
    @Override
    public void execute(IContext context, String[] args) {
        context.getReader().close();
        context.print("Выход...\n");
        System.exit(0);
    }
}
