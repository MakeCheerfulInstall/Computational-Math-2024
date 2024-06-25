package lab2.commands;


import lab2.Main;
import lab2.MathModuleLab2;
import lab2.models.FirstSysFunc;
import lab2.models.ISysFunc;
import lab2.models.SecondSysFunc;
import lab2.module.MenuModule;

import java.util.ArrayList;

public class SystemEquations implements Command {
    @Override
    public String getMessage() {
        return "Решение систем нелинейных уравнений";
    }

    @Override
    public void execute() {
        ArrayList<Command> commands = new ArrayList<>();
        ArrayList<ISysFunc> sysFuncs = new ArrayList<>();


        sysFuncs.add(new FirstSysFunc());
        sysFuncs.add(new SecondSysFunc());


        for (ISysFunc func : sysFuncs){
            commands.add(new Command() {
                @Override
                public String getMessage() {
                    return func.getMessage();
                }
                @Override
                public void execute() {
                    MathModuleLab2.execute(func);
                }
            });
        }
        commands.add(new Main());
        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }
}