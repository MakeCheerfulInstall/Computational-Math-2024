package Computational.math;

import Computational.math.GraphicPart.MainComponents.MainFrame;
import Computational.math.Methods.NewtonMethodInterpolation;
import Computational.math.Utils.FunctionalTable;

import java.util.function.Function;

public class MainTest {
    void main(){
        int dimensionGen = 10;
        Double[] xArr = new Double[dimensionGen];
        for (int i = 0, j = 0; i < xArr.length; j+=2, i++) {
            xArr[i] = (double) j;
        }
        Double[] yArr = new Double[xArr.length];
        Function<Double,Double> testFunction = Math::sin;
        for (int i = 0; i < xArr.length; i++) {
            yArr[i] = testFunction.apply(xArr[i]);
        }

        FunctionalTable f = new FunctionalTable(xArr,yArr);
        FunctionalTable functionalTableByNewton;
        Double[] xNewton = f.getxArr();
        Double[] yNewton = new Double[xNewton.length];
        NewtonMethodInterpolation nm = new NewtonMethodInterpolation();
        for (int i = 0; i < f.dimension(); i++) {
            if(Double.isNaN((yNewton[i] = nm.apply(f, xNewton[i])))){
                throw new RuntimeException("ААА, УЗЛЫ НЕ РАВНОСТОЯЩИЕ");
            }
        }
        f.printTable();
        System.out.println("nm.apply(f,2.761) = " + nm.apply(f, 2.761));
        functionalTableByNewton = new FunctionalTable(xNewton,yNewton);

        MainFrame.drawInterpolation("some useful data",f,functionalTableByNewton,30,30);
    }
}
