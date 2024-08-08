package Computational.math.Methods;

import java.util.function.Function;

public abstract class AbstractMethod {
    private final String nameOfMethod;
    private final Function<Double,Double> function;

    public AbstractMethod(String name,Function<Double,Double> function) {
        this.nameOfMethod = name;
        this.function = function;
    }
    public abstract void solve();

    public String getNameOfMethod() {
        return nameOfMethod;
    }

    public Function<Double, Double> getFunction() {
        return function;
    }
    public void printMethodName(){
        //156 это количество пунктиров в таблице
        for (int i = 0; i < 156-4-4-getNameOfMethod().length(); i++) {
            //middle
            if(i==50){
                System.out.print("\t" + getNameOfMethod() + "\t");
            }
            System.out.print("*");
        }
        System.out.println();
    }
}
