package lab2.commands;

import lab2.Main;
import lab2.MathModuleLab2;
import lab2.util.FuncX;
import lab2.module.MenuModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleEquations implements Command {
    @Override
    public String getMessage() {
        return "Решение нелинейных уравнений";
    }

    @Override
    public void execute() {
        List<Command> commands = new ArrayList<>();
        HashMap<String, FuncX> funcHashMap = new HashMap<>();
        funcHashMap.put("x^2+x+2", x -> Math.pow(x, 2) + x + 2);
        funcHashMap.put("x^2+x+2", x -> Math.pow(x, 2) + x + 2);
        funcHashMap.put("3x^2-14x-5", x -> 3 * Math.pow(x, 2) - (14 * x) - 5);
        funcHashMap.put("x^2+2x+1", x -> Math.pow(x, 2) + (2 * x) + 1);
        funcHashMap.put("e^x-1", x -> Math.pow(Math.E, x) - 1);

        for(Map.Entry<String, FuncX> entry : funcHashMap.entrySet()) {
            commands.add(new Command() {
                @Override
                public String getMessage() {
                    return entry.getKey();
                }
                @Override
                public void execute() {
                    MathModuleLab2.execute(entry.getValue());
                }
            });
        }
        commands.add(new Main());

        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }
}