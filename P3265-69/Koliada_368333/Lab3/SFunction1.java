package Lab3;

/**
 * (3𝑥3 −2𝑥2 − 7𝑥 − 8)
 */
public class SFunction1 implements ISFunction{
    @Override
    public String toString(){
        return "3\uD835\uDC653 − 2\uD835\uDC652 − 7\uD835\uDC65 − 8";
    }

    @Override
    public double functionOf(double x) {
        return 3*x*x*x - 2*x*x - 7*x -8;
    }
}
