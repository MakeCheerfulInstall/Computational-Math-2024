package Computational.math.Functions;

import Computational.math.LinearSystem.SimpleIterationLinSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class SystemFunctions {
    private final int chosenSystem;
    private ArrayList<Function<Double,Double>> firstSystemDedicatedY = new ArrayList<>(
            List.of(x->Math.pow(x,2),(x->Math.sqrt(4-Math.pow(x,2))),(x->-Math.sqrt(4-Math.pow(x,2))))
    );
    //df/dx df/dy dg/dx dg/dy
    private List<BinaryOperator<Double>> difFirstSystem = new ArrayList<>(
            List.of((x,y) -> 2*x,
                    (x,y) -> 2*y,
                    (x,y) -> -6*x,
                    (x,y) -> 1d)
    );
    //second system
    private List<BinaryOperator<Double>> secondSystem = new ArrayList<>(
            List.of(
                    (x,y) -> 36*x*y + 3*Math.pow(y,2) - 5,
                    (x,y) -> x + 3*Math.pow(y,2) - 7
            )
    );
    private ArrayList<Function<Double,Double>> secondSystemDedicatedY = new ArrayList<>(
            List.of(
                    //первая функция, просто расписанная
//                    x-> (-x+Math.sqrt(Math.pow(x,2) + 15))/3,
                    x->(-36*x+Math.sqrt(1296*Math.pow(x,2) + 60))/6,
                    x->-(36*x+Math.sqrt(1296*Math.pow(x,2) + 60))/6,

//                    x->-(x+Math.sqrt(Math.pow(x,2) + 15))/3,
                    //первая функция закончилась, началась вторая:D
                    x-> -Math.sqrt(
                            -((double) 1 /3)*x + (double) (7/3)
                    ),
                    x-> Math.sqrt(
                            -((double) 1 /3)*x + (double) (7/3)
                    )
                    )
    );

    public SystemFunctions(int chosenFunction){
        this.chosenSystem = chosenFunction;
    }

    public List<BinaryOperator<Double>> getChosenDifFunction(){
        return difFirstSystem;
    }
    public Double[] firstFunctionCalculateApproach(double x,double y){
        Double[][] system = new Double[][]{
                {2*x,2*y},
                {-6*x, 1d}
        };
        Double[] answers = new Double[]{
                4 - Math.pow(x,2) - Math.pow(y,2),
                3*Math.pow(x,2) - y
        };
        SimpleIterationLinSystem smp = new SimpleIterationLinSystem(system,answers,0.0001);
        return smp.solve();
    }
    public Double[] secondFunctionCalculateApproach(double x,double y){
        Double[][] system = new Double[][]{
                {36*y, 36*x+6*y},
                {1d, 6*y}
        };
        Double[] answers = new Double[]{
                -36*x*y - 3*Math.pow(y,2)+5,
                -x-3*Math.pow(y,2)+7
        };
        SimpleIterationLinSystem smp = new SimpleIterationLinSystem(system,answers,0.00000001);
        return smp.solve();
    }
    public Double[] getCalculatedChosenFunction(double x,double y){
        switch (chosenSystem){
            case 1:
                return firstFunctionCalculateApproach(x,y);
            case 2:
                return secondFunctionCalculateApproach(x,y);
            default:
                System.err.println("Invalid chosen system number: " + chosenSystem);
                return null;
        }
    }
    public static void printAllSystem(){
        System.out.println("1:");
        System.out.println("/");
        System.out.println("|\tx^2+y^2=4");
        System.out.println("|");
        System.out.println("|\ty=3x^2");
        System.out.println("\\");

        System.out.println("2:");
        System.out.println("/");
        System.out.println("|\t36xy+3y^2-5=0");
        System.out.println("|");
        System.out.println("|\tx+3y^2-7=0");
        System.out.println("\\");
    }

    public ArrayList<Function<Double, Double>> getFirstSystemDedicatedY() {
        return firstSystemDedicatedY;
    }

    public ArrayList<Function<Double, Double>> getSecondSystemDedicatedY() {
        return secondSystemDedicatedY;
    }
    public ArrayList<Function<Double, Double>> getChosenSystem(){
        switch (chosenSystem){
            case 1:
                return getFirstSystemDedicatedY();
            case 2:
                return getSecondSystemDedicatedY();
            default:
                System.err.println("Вы должны выбрать систему из списка");
                return null;
        }

    }
}
