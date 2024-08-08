package Computational.math.Functions;

import java.util.function.Function;

/**
 * Class which provides functions
 */
public class Functions {
    int chosenFunctionNumber = 0;
    //функции
    Function<Double,Double> firstFunction = x ->  Math.pow(x,3) - x + 4;
    Function<Double,Double> secondFunction = x -> Math.pow(x,3) + 2.28*(x*x) - 1.934*x - 3.907;
    Function<Double,Double> thirdFunction = x ->  Math.sin(x) + 0.1;
    //производные
    Function<Double,Double> firstDifFunction = x-> 3*Math.pow(x,2) - 1;
    Function<Double,Double> secondDifFunction = x-> 3*Math.pow(x,2) + 2.28 * 2* x - 1.934;

    Function<Double,Double> thirdDifFunction = Math::cos;

    public Functions(){}
    public Functions(int chosenFunctionNumber){
        this.chosenFunctionNumber = chosenFunctionNumber;
    }
    public double getValueOfChosenFunction(double x){
        return switch (this.chosenFunctionNumber) {
            case 1 -> firstFunction.apply(x);
            case 2 -> secondFunction.apply(x);
            case 3 -> thirdFunction.apply(x);
            //todo это будет под метод Ньютона. Он временно умер
            case 4 -> -1;
            default -> -1d;
        };
    }
    public Function<Double,Double> getFunction(){
        return switch (this.chosenFunctionNumber){
            case 1 -> firstFunction;
            case 2-> secondFunction;
            case 3->thirdFunction;
            case 4-> null;
            default -> throw new IllegalStateException("Unexpected value of id function: " + this.chosenFunctionNumber);
        };
    }
    public double getDifValueFunction(double x){
        return switch (this.chosenFunctionNumber) {
            case 1 -> firstDifFunction.apply(x);
            case 2 -> secondDifFunction.apply(x);
            case 3 -> thirdDifFunction.apply(x);
            //todo это будет под метод Ньютона. Он временно умер
            case 4 -> -1;
            default -> -1d;
        };
    }
    public void printFunction(){
        switch (this.chosenFunctionNumber){
            case 1:
                System.out.println("x^3 - x  + 4");
                break;
            case 2:
                System.out.println("x^3 + 2.28x^2 - 1.934*x-3.907");
                break;
            case 3:
                System.out.println("sin(x) + 0.1");
                break;
            default:
                System.out.println("Такой функции нет");
                break;
        }
    }
    public static void printAllFunctions(){
        System.out.println("1. x^3 - x  + 4");
        System.out.println("2. x^3 + 2.28x^2 - 1.934*x-3.907");
        System.out.println("3. sin(x) + 0.1");

    }
}
