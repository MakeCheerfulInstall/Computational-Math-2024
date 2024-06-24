package Lab2;

/**
 * 3x^3 + 1.7x^2 - 15.42x + 6.89
 */
public class Function1 implements IFunction{
    @Override
    public double functionOf(double x) {
        return 3*(x*x*x) + 1.7 * x*x - 15.42*x + 6.89;
    }

    @Override
    public double f1Of(double x) {
        return 9*x*x + 3.4 * x -15.42;
    }

    @Override
    public double f2Of(double x) {
        return 18*x + 3.4;
    }

    @Override
    public String toString() {
        return "3x**3 + 1.7x**2 - 15.42x + 6.89";
    }
    @Override
    public IFunction getPhiFunction(IMethod forMathod, double a, double b) {
        IFunction phi = new PhiFunction1();
        if(forMathod.isFunSuitable(phi,a,b)) return phi;
        phi = new PhiFunction2();
        if(forMathod.isFunSuitable(phi,a,b)) return phi;
        return new PhiFunCommon(this,a,b);
    }

    private   class PhiFunction1 implements IFunction{

        /**
         * x = (3/15.42)x^3 + (1.7/15.42)x^2 + 6.89/15.42
         * @param x
         * @return
         */

        @Override
        public double functionOf(double x) {
            return (3/15.42)*x*x*x + (1.7/15.42)*x*x + 6.89/15.42;
        }
        /**
         * (9/15.42)x^2 + (3.4/15.42)x
         * @param x
         * @return
         */
        @Override
        public double f1Of(double x) {
            return (9/15.42)*x*x + (3.4/15.42);
        }
    }

    /**
     * 3x^3 + 1.7x^2 - 15.42x + 6.89
     * x = qubrt((15,42x - 1.7x^2 - 6.89)/3)
     */
    private class PhiFunction2 implements IFunction{

        @Override
        public double functionOf(double x) {
            return Math.pow((15.42*x - 1.7*x*x - 6.89)/3.,1/3.);
        }

        /**
         * (15.42-1,7*2x)/(9*pow(15,42*x-1.7*x^2 - 6.89,2/3.)
         * @param x - значение x
         * @return
         */

        @Override
        public double f1Of(double x) {
            return (15.42-3.4*x)/(9*Math.pow(15.42*x-1.7*x*x - 6.89,2/3.));
        }
    }
    /**
     * 3x^3 + 1.7x^2 - 15.42x + 6.89
     * x = sqrt((15,42x - 3x^3 -6.89)/1.7)
     */
    private class PhiFunction3 implements IFunction{

        @Override
        public double functionOf(double x) {
            return 0;
        }

        @Override
        public double f1Of(double x) {
            return 0;
        }
    }
}
