package Computational.Math.Methods;

import org.netirc.library.jtables.exception.MalformedTableException;

import java.util.function.Function;

@FunctionalInterface
public interface Solve {
    Double solve(Function<Double, Double> function, Double a, Double b, int n,boolean isNeedToPrint) throws MalformedTableException;
}
