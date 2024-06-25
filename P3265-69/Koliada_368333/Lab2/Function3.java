package Lab2;

public class Function3 implements IFunction{
    @Override
    public double functionOf(double x) {
        return x*x*x - x + 4;
    }

    @Override
    public double f1Of(double x) {
        return 3*x*x - 1;
    }

    @Override
    public double f2Of(double x) {
        //2x
        return 6*x;
    }

    @Override
    public String toString() {
        return "x**3 - x + 4";
    }

    @Override
    public IFunction getPhiFunction(IMethod forMethod, double a, double b) {
        IFunction phi = new PhiFunction1();
        if(forMethod.isFunSuitable(phi,a,b)) return phi;
        phi = new PhiFunction2();
        if(forMethod.isFunSuitable(phi,a,b)) return phi;
        return new PhiFunCommon(this,a,b);
    }

    private  class PhiFunction1 implements IFunction{

        /**
         * x = x^3 + 4
         * @param x функция от
         * @return
         */
        @Override
        public double functionOf(double x) {
            return x*x*x + 4;
        }

        /**
         * 3x^2
         * @param x - значение x
         * @return
         */
        @Override
        public double f1Of(double x) {
            return 3*x*x;
        }
    }
    private class PhiFunction2 implements IFunction{
        /**
         * x= (x-4)^1/3
         *
         * @param x функция от
         * @return
         */
        @Override
        public double functionOf(double x) {
            return Math.pow((x-4),1/3.);
        }

        /**
         * 1/3(x-4)^-2|3
         * @param x - значение x
         * @return
         */
        @Override
        public double f1Of(double x) {
            return 1/3. * Math.pow(x-4,-2./3.);
        }
    }
}
