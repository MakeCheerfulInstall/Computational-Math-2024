package Computational.math;

import Computational.math.Functions.Functions;
import Computational.math.GraphicPart.MainComponents.MainFrame;
import Computational.math.Methods.HalfDivision.HalfDivision;
import Computational.math.Methods.NewtonsMethod.NewtonsMethod;
import Computational.math.Methods.SecantMethod.SecantMethod;
import Computational.math.Methods.SimpleIteration.SimpleIteration;

import java.util.function.Function;

import static Computational.math.MethodName.HALF_DIVISION;

public class FabricMethods {

    public FabricMethods(){

    }
    public void executeMethod(MethodName methodName,double a, double b, float epsilon,int numberOfChosenFunction){
        Function<Double,Double> f = new Functions(numberOfChosenFunction).getFunction();
        switch (methodName){
            case HALF_DIVISION:

                new HalfDivision(a,b,epsilon,numberOfChosenFunction).solve();
                break;
            case SECANT_METHOD:
                new SecantMethod(a,b,epsilon,numberOfChosenFunction).solve();
                break;
            case SIMPLE_ITERATION:
                new SimpleIteration(a,b,epsilon,numberOfChosenFunction).solve();
                break;
            case NEWTON_METHOD:
                new NewtonsMethod(a,b,epsilon,numberOfChosenFunction).solve();
                break;
            default:
                System.err.println("Invalid method name");
                System.exit(-1);
        }
    }
}
