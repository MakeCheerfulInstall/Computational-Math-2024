package lab3.commands;


import lab2.commands.Command;
import lab2.module.MenuModule;
import lab3.solution.TrapezoidIntegral;
import lab3.models.IFuncX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumericalIntegrationTrapezoid implements Command {
    @Override
    public void execute() {
        List<Command> commands = new ArrayList<>();
        HashMap<String, IFuncX> funcHashMap = new HashMap<>();
//        funcHashMap.put("x^2+x+2", x -> Math.pow(x, 2) + x + 2);
//        funcHashMap.put("3x^2-14x-5", x -> 3 * Math.pow(x, 2) - (14 * x) - 5);
//        funcHashMap.put("x^2+2x+1", x -> Math.pow(x, 2) + (2 * x) + 1);
        funcHashMap.put("2x", x -> 2 * x);
        funcHashMap.put("1/x", x -> 1/x);
        funcHashMap.put("sin(x)/x", x -> Math.sin(x)/x);

        for(Map.Entry<String, IFuncX> entry : funcHashMap.entrySet()) {
            commands.add(new Command() {
                @Override
                public String getMessage() {
                    return entry.getKey();
                }
                @Override
                public void execute() {
                    TrapezoidIntegral.execute(entry.getValue());
                }
            });
        }
        commands.add(new Integration());
        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Нахождение интегралов методом Трапеций";
    }
}