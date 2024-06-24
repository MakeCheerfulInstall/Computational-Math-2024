package Lab2;

/**
 * Система уравнений
 * tgx * y - x**2 = 0
 * 0,8x**2 + 2y**2 - 1 = 0
 */
public class SystemOfFunctions3 implements ISysFunctions {

    @Override
    public String toString() {
        return _functions[0].toString()+ " | "+_functions[1].toString();
    }

    @Override
    public IFunction[] getSysFunctions() {
        IFunction[] res = new IFunction[2];
        res[0] = (IFunction) _functions[0];
        res[1] = (IFunction) _functions[1];
        return res;
    }

    private ISysFunction[] _functions;

    public SystemOfFunctions3() {
        _functions = new ISysFunction[2];
        //Первой функций в системе должно быть уравнение, в котором в эквивалентном виде выражается x
        _functions[0] = new Function2();
        _functions[1] = new Function1();
    }

    /**
     * tgx * y - x**2 = 0
     */
    private class Function1 implements ISysFunction {

        @Override
        public String toString() {
            return "tgx*y-x**2=0";
        }

        /**
         * y = x**2|tgx
         *
         * @param x функция от
         * @return x**2|tgx
         */
        @Override
        public double functionOf(double x) {
            return x * x / Math.tan(x);
        }

        @Override
        public double f1Of(double x) {
            return 0;
        }

        @Override
        public double f2Of(double x) {
            return 0;
        }

        /**
         * tgx * y - x**2 = 0 -> y = x^2/tgx
         *
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            if(x == 0) return Double.NaN;

            return x*x/Math.tan(x);
        }



        /**
         * (Sin(2x)*x - x^2)/sin^2(x)
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi1X(double x, double y) {
            return (Math.sin(2*x)*x - x*x)/Math.pow(Math.sin(x),2);
        }

        @Override
        public double phi1Y(double x, double y) {
            return 0;
        }

        /**
         * tgx * y - x**2 = 0
         *
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return Math.tan(x) * y - x * x;
        }

        @Override
        public double f1x(double x, double y) {
            return y / (Math.cos(x) * Math.cos((x))) - 2 * x;
        }

        @Override
        public double f1y(double x, double y) {
            return Math.tan(x);
        }
    }

    /**
     * 0,8x**2 + 2y**2 - 1 = 0
     *
     */
    private class Function2 implements ISysFunction{
        @Override
        public String toString() {
            return "0,8x**2+2y**2-1=0";
        }

        /**
         * y = sqrt((1 - 0.8x**2)/2)
         * @param x функция от
         * @return sqrt((1 - 0.8x**2)/2)
         */
        @Override
        public double functionOf(double x) {
            double v = ((1 - 0.8*x*x)/2);
            if(v < 0) return Double.NaN;
            return Math.sqrt(v);
        }

        @Override
        public double f1Of(double x) {
            return 0;
        }

        @Override
        public double f2Of(double x) {
            return 0;
        }

        @Override
        public boolean isCircle() {
            return true;
        }

        /**
         * 0,8x**2 + 2y**2 - 1 = 0 -> x = Math.pow((1 - 2 * y*y)/0,8,1/2)*
         *
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            return Math.pow((1 - 2 * y*y)/0.8,1/2.);
        }

        @Override
        public double phi1X(double x, double y) {
            return 0;
        }

        @Override
        public double phi1Y(double x, double y) {
            return -1*y *Math.pow((5-10*y*y),1/2.)/(1 - 2* y*y);
        }

        @Override
        public double originalFunc(double x, double y) {
            return 0.8*x*x +2*y*y -1;
        }

        /**
         * 0,8x**2 + 2y**2 - 1 = 0 -> 1.6x
         * @param x
         * @param y
         * @return
         */
        @Override
        public double f1x(double x, double y) {
            return 1.6*x;
        }

        /**
         * 0,8x**2 + 2y**2 - 1 = 0 -> 4y
         * @param x
         * @param y
         * @return
         */

        @Override
        public double f1y(double x, double y) {
            return 4*y;
        }

        @Override
        public double getStep(double x) {
            var v = Math.abs(x);
            if(v>1 && v <1.5) return 0.01;
            return ISysFunction.super.getStep(x);
        }
    }
}
