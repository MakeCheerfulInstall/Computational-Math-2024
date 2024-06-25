package Lab3;

/**
 * x*x
 */
public class SFunction3 implements  ISFunction{
    @Override
    public String toString() {
        return "x*x";
    }

    @Override
    public double functionOf(double x) {
        return x*x;
    }
}
