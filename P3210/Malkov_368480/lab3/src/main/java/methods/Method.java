package methods;

import lombok.Data;
import util.Result;

import java.util.function.Function;

@Data
public abstract class Method {
    protected final long START_PARTITION = 4;
    public abstract Result compute(Function<Double, Double> function, double a, double b, double accuracy, String modify) throws StringIndexOutOfBoundsException;
    abstract double computeRes(Function<Double, Double> function, double a, double b, long divisions, String modify) throws StringIndexOutOfBoundsException;
}
