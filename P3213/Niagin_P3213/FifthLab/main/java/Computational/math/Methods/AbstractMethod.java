package Computational.math.Methods;

import Computational.math.Utils.FunctionalTable;

public abstract class AbstractMethod {
    String name;
    public AbstractMethod(String name){
        this.name = name;
    }
    public abstract Double apply(FunctionalTable functionalTable, double x_current);

    public String getName() {
        return name;
    }
}
