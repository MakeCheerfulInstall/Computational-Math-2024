package Lab2;

/**
 * y = sin(x+10) + tgx
 */
public class Function4 implements IFunction {
    @Override
    public double functionOf(double x) {
        return Math.sin(x + 10) + Math.tan(x);
    }

    /**
     * sin(x+10) +tgx -> cos(x+10) + 1|cos^2(x)
     *
     * @param x - значение x
     * @return
     */
    @Override
    public double f1Of(double x) {
        return Math.cos(x + 1) + 1 / (Math.cos(x) * Math.cos(x));
    }

    /**
     * cos(x+10) + 1/cos^2(x) -> -sin(x+10) + 2 sinx / cos^3(x)
     *
     * @param x параметр функции
     * @return
     */
    @Override
    public double f2Of(double x) {
        return -Math.sin(x + 10) + 2 * Math.sin(x) / Math.pow(Math.cos(x), 3);
    }

    @Override
    public String toString() {
        return "y = sin(x+10) + tgx";
    }

    @Override
    public IFunction getPhiFunction(IMethod forMathod, double a, double b) {
        var f = new PhiFunCommon(this, a, b);
        return f;
    }
}
