package Lab3;

/**
 * 4𝑥3 − 5𝑥2 + 6𝑥 − 7
 */
public class SFunction2 implements  ISFunction{
    @Override
    public double functionOf(double x) {
        return 4*x*x*x - 5*x*x + 6*x - 7;
    }

    @Override
    public String toString() {
        return "4\uD835\uDC653 − 5\uD835\uDC652 + 6\uD835\uDC65 − 7";
    }
}
