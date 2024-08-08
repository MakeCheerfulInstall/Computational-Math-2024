package Computational.math.ApproximationMethods;

import Computational.math.FunctionalTable;
import Computational.math.GraphicPart.MainComponents.MainFrame;
import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FabricMethods {
    public ArrayList<AbstractMethod> methodList;

    public FabricMethods() {
        methodList = new ArrayList<>(
                List.of(new PowerApproximation())
//                List.of(new LinearApproximation(), new QuadraticApproximation(), new ExponentialApproximation(), new LogarithmicApproximation(),new PowerApproximation())
        );
    }
    public void executeMethod(FunctionalTable fn) {
        try {
            MethodName bestFunction = null;
            double maxR = -9999999999d;
            var matrix = fn.getTable();
            for (int i = 0; i < methodList.size(); i++) {
                double[][] copyOfArray = Arrays.stream(matrix).map(double[]::clone).toArray(double[][]::new);
                AbstractMethod currentMethod = methodList.get(i);
                currentMethod.printMethodName();
                Function<Double,Double> P =  currentMethod.apply(fn);
                MainFrame.drawSingleFunction(currentMethod.getName(),P);
                currentMethod.printResult(new FunctionalTable(copyOfArray),P);
                if (currentMethod.getR2() > maxR) {
                    maxR = currentMethod.getR2();
                    bestFunction = currentMethod.getMethodName();;
                }
            }
            System.out.println("Лучшая функция: " + bestFunction.toString());
        } catch (MalformedTableException e) {
            System.out.println(e.getMessage());
        }
    }
}
