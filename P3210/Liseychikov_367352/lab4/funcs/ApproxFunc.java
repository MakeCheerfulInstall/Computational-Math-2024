package lab4.funcs;

import lab2.util.FuncX;
import lab2.util.Point;

import java.util.List;

public interface ApproxFunc {
    FuncX create(List<Point> points);
}
