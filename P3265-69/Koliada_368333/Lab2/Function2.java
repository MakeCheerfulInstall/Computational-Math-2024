package Lab2;

/**
 * x^3 - 4.81x^2 - 17.37x + 5.38
 */
public class Function2 implements IFunction{
    @Override
    public double functionOf(double x) {
        return x*x*x + 4.81*x*x - 17.37*x + 5.38;
    }

    /**
     * 3x**2 + 9,62x - 17,37
     * @param x - значение x
     * @return
     */
    @Override
    public double f1Of(double x) {
        return 3 * x * x + 9.62 * x -17.37;
    }

    /**
     * 6x + 9.62
     * @param x параметр функции
     * @return
     */
    @Override
    public double f2Of(double x) {
        //6x +1
        return 6*x + 9.62;
    }

    @Override
    public String toString() {
        return "x**3 + 4.81x**2 - 17.37x + 5.38";
    }

    @Override
    public IFunction getPhiFunction(IMethod forMethod, double a, double b) {
        Function2.Function1 f1 = new Function2.Function1();
        if(forMethod.isFunSuitable(f1,a,b)) return f1;
        return new PhiFunCommon(this,a,b);
    }

    /**
     * x = куб корень (17,37x - 4,81x^2 -5,38)
     */
    private class Function1 implements  IFunction{

        @Override
        public double functionOf(double x) {
            return Math.pow(17.37 * x - 4.81 * x*x - 5.38,1./3.);
        }
        //(17,37 - 2*4,81*x)/(3 *pow((17.37*x - 4,81 * x^2 -5,38),2/3)
        @Override
        public double f1Of(double x) {
            return (17.37 - 2*4.81*x)/(3 * Math.pow((17.37*x - 4.81*x*x-5.38),2./3.));
        }
    }
}
