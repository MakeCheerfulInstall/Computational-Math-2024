package lab3.commands;

import lab2.commands.Command;
import lab2.module.MenuModule;
import lab3.solution.SimpsonIntegral;
import lab3.models.IFuncX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simpson implements Command {
    private static final SimpsonIntegral SIMPSON_INTEGRAL = new SimpsonIntegral();

    @Override
    public void execute() {
        List<Command> commands = new ArrayList<>();
        HashMap<String, IFuncX> funcHashMap = new HashMap<>();
        funcHashMap.put("3x^3+5x^2+3x-6", x -> 3 * Math.pow(x, 3) + 5 * x * x + 3 * x - 6);
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
                    SIMPSON_INTEGRAL.execute(entry.getValue());
                }
            });
        }
        commands.add(new Integration());
        MenuModule menu = new MenuModule(commands);
        menu.execute();
    }

    @Override
    public String getMessage() {
        return "Нахождение интегралов методом Симсона";
    }
}
