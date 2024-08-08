package Computational.Math.Utils;

import Computational.Math.Methods.AbstractMethod;
import Computational.Math.Methods.RectangleMethods.LeftRectangles;
import Computational.Math.Methods.RectangleMethods.MiddleRectangles;
import Computational.Math.Methods.RectangleMethods.RightRectangles;
import Computational.Math.Methods.SimpsonsMethod;
import Computational.Math.Methods.TrapezoidMethod;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FabricMethods {
    public ArrayList<AbstractMethod> methodList;

    public FabricMethods() {
        methodList = new ArrayList<>(
                List.of(new SimpsonsMethod(), new LeftRectangles(), new MiddleRectangles(), new RightRectangles(), new TrapezoidMethod())
        );
    }

    public void executeMethod(MethodName methodName, Function<Double, Double> function, double a, double b, double accuracy) throws MalformedTableException {
        if (!isConvergent(function, a, b)) {
            System.out.println();
            return;
        }

        AbstractMethod method = getMethodByMethodName(methodName);
        var n = 4;
        Double Integral0 = method.solve(function, a, b, n, false);
        if(Integral0 == null){
            System.out.println("Интеграл не существует(расходится внутри)\n");
            return;
        }

        n *= 2;
        Double Integral1 = method.solve(function, a, b, n, false);
        int maxIter = 20;
        while (!(Math.abs(Integral1 - Integral0) < accuracy)) {
            n *= 2;
            Integral0 = Integral1;
            Integral1 = method.solve(function, a, b, n, false);
            maxIter--;
            if(maxIter == 0){
                System.out.println("Интеграл не существует(расходится внутри)\n");
                return;
            }
        }
        method.solve(function, a, b, n, true);
    }

    private boolean isConvergent(Function<Double, Double> function, double a, double b) {
        if (function.apply(a).isInfinite() || function.apply(b).isInfinite()) {
            System.out.println("Интеграл не существует");
            return false;
        }
        return true;
    }

    public AbstractMethod getMethodByMethodName(MethodName methodName) {
        return switch (methodName) {
            case SIMPSON -> new SimpsonsMethod();
            case RECTANGLES_LEFT -> new LeftRectangles();
            case RECTANGLES_MIDDLE -> new MiddleRectangles();
            case RECTANGLES_RIGHT -> new RightRectangles();
            case TRAPEZOID -> new TrapezoidMethod();
        };
    }

    public void printNameMethods() {
        for (int i = 0; i < methodList.size(); i++) {
            System.out.println(i + 1 + ". " + methodList.get(i).getMethodName());
        }
    }
}
