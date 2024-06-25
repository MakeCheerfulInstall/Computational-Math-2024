package lab4;


import lab2.module.GraphModule;
import lab2.util.FuncX;
import lab2.util.Point;
import lab4.funcs.*;
import lab4.utils.Calculation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Approximation {
    public static void solve(List<Point> points) {
        System.out.println();
        List<FuncX> funcs = new ArrayList<>();
        funcs.add(new LinearFunc().create(points));
        funcs.add(new ExpFunc().create(points));
        funcs.add(new LogFunc().create(points));
        funcs.add(new PowerFunc().create(points));
        funcs.add(new QuadraticFunc().create(points));
        funcs.add(new CubicFunc().create(points));
        System.out.println("Наилучшую точность показала: " + Calculation.bestApprox() + "\n");

        Map<String, List<FuncX>> mapFunc = new HashMap<>();
        mapFunc.put("График функции", funcs);
        Map<String, List<Point>> mapPoints = new HashMap<>();
        mapPoints.put("Точки", points);
        new GraphModule(mapFunc, mapPoints);
    }
}
