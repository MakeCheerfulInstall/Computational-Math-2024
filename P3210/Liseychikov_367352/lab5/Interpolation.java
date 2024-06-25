package lab5;

import lab2.module.GraphModule;
import lab2.util.FuncX;
import lab2.util.Point;
import lab5.solver.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpolation {
    public static void solve(List<Point> points, double x, FuncX funcX) {
        System.out.println();
        new LagrangePolynomial().solver(points, x);
        new NewtonPolynomialWithDividedDifferences().solver(points, x);
        new NewtonPolynomialWithFiniteDifferences().solver(points, x);
//        new Stirling().solver(points, x);
//        new Bessel().solver(points, x);

        Map<String, List<FuncX>> mapFunc = new HashMap<>();
        mapFunc.put("График функции", new ArrayList<>());
        if (funcX != null) {
            mapFunc.put("График функции", List.of(funcX));
        }
        Map<String, List<Point>> mapPoints = new HashMap<>();
        mapPoints.put("Точки", points);
        new GraphModule(mapFunc, mapPoints);
    }
}
