package lab2;

import lab2.solution.*;
import lab2.util.Data;
import lab2.util.FuncX;
import lab2.util.Point;
import lab2.models.ISysFunc;
import lab2.module.GraphModule;
import lab2.util.ReadData;

import java.util.*;

public class MathModuleLab2 {
    private static final ReadData READ_DATA = new ReadData();
    private static double left;
    private static double right;
    private static double eps;

    public static void execute(FuncX func) {
        Data data = READ_DATA.getData(false);
        left = data.left();
        right = data.right();
        eps = data.eps();

        List<Point> points = new ArrayList<>();
        new Chord().solve(func, left, right, eps).ifPresent(points::add);
        new Secant().solve(func, left, right, eps).ifPresent(points::add);
        new SimpleIteration().solve(func, left, right, eps).ifPresent(points::add);

        useGraph(List.of(func), points);
    }

    public static void execute(ISysFunc func) {
        Data data = READ_DATA.getData(true);
        left = data.left();
        right = data.right();
        eps = data.eps();

        Newton newton = new Newton();
        double[] newtonAnsw = newton.newtonMethod(func, new double[]{left, right}, eps);
        useGraph(func.getDraw(), List.of(new Point(newtonAnsw[0], newtonAnsw[1])));
    }

    private static void useGraph(List<FuncX> funcGraph, List<Point> points) {
        // Добавление функции на график
        Map<String, List<FuncX>> map_func = new HashMap<>();
        map_func.put("График функции", funcGraph);
        // Добавление функции на график
        Map<String, List<Point>> map_points = new HashMap<>();
        map_points.put("Точки", points);
        new GraphModule(map_func, map_points);
    }
}