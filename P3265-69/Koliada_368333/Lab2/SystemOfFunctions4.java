package Lab2;

/**
 * 2x + cosy = 2
 * sin (x+1) - y -1,2 = 0
 *
 */
public class SystemOfFunctions4 implements ISysFunctions {

    ISysFunction[] _functions;
    public SystemOfFunctions4() {
        _functions = new ISysFunction[2];
        //Первой функций в системе должно быть уравнение, в котором в эквивалентном виде выражается x
        _functions[0] = new Function2();
        _functions[1] = new Function1();
    }
    @Override
    public IFunction[] getSysFunctions() {
        IFunction[] fs = new IFunction[2];
        fs[0] = (ISysFunction)_functions[0];
        fs[1] = (ISysFunction)_functions[1];
        return fs;
    }

    @Override
    public String toString() {
        return _functions[0].toString() + " | " + _functions[1].toString();
    }

    /**
     * sin (x+1) - y -1,2 = 0
     */
    private class Function1 implements ISysFunction {

        /**
         * sin (x+1) - y -1,2 = 0 -> y = sin(x+1) - 1.2
         *
         * @param x функция от
         * @return
         */
        @Override
        public double functionOf(double x) {
            return Math.sin(x+1) - 1.2;
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
         * y = sin(x+1) - 1.2
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            return Math.sin(x+1) - 1.2;
        }

        @Override
        public double phi1X(double x, double y) {
            return Math.cos(x+1);
        }

        @Override
        public double phi1Y(double x, double y) {
            return 0;
        }

        /**
         * sin (x+1) - y -1,2 = 0
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return Math.sin(x+1) - y - 1.2;
        }

        /**
         * sin (x+1) - y -1,2 = 0
         * @param x
         * @param y
         * @return
         */
        @Override
        public double f1x(double x, double y) {
            return Math.cos(x+1);
        }

        /**
         * x - sin(y+1) - 1 = 0 -> - cos(y)
         * @param x
         * @param y
         * @return
         */

        @Override
        public double f1y(double x, double y) {
            return -1;
        }

        @Override
        public String toString() {
            return "sin(x+1)-y-1,2=0";
        }
    }

    /**
     * 2x + cosy - 2 =  0
     *
     */
    private class Function2 implements ISysFunction{

        /**
         * 2x + cosy - 2 = 0 ->
         *
         * @param x функция от
         * @return
         */
        @Override
        public double functionOf(double x) {
            return Math.acos(2-2*x);
        }

        @Override
        public boolean isDrawByY() {
            return true;
        }
        @Override
        public double functionOfY(double y) {
            return (2-Math.cos(y))/2.;
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
         * 2x + cosy - 2 = 0 -> x = (2-cosy)/2
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            return (2.- Math.cos(y))/2.;
        }

        @Override
        public double phi1X(double x, double y) {
            return 0;
        }

        @Override
        public double phi1Y(double x, double y) {
            return Math.sin(y)/2.;
        }

        /**
         * 2x + cosy - 2 = 0
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return 2*x + Math.cos(y) - 2;
        }

        @Override
        public double f1x(double x, double y) {
            return 2;
        }

        @Override
        public double f1y(double x, double y) {
            return -Math.sin(y);
        }

        @Override
        public String toString() {
            return "2x+cosy-2=0";
        }
    }
}
