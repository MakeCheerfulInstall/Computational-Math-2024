package Lab2;

/**
 * Система уравнений
 * 0.1*x**2 + x + 0,2*y**2 -0,3 = 0
 * 0,2*x**2 + y + 0,1*x*y - 0,7 = 0
 */
public class SystemOfFunctions2 implements ISysFunctions {

    /*
    Приведенная система уравнений
    x = 0,3 - 0,1x**2 - 0,2y**2
    y = 0,7 - 0,1xy - 0,2x**2
     */
    @Override
    public IFunction[] getSysFunctions() {
        IFunction[] f = new IFunction[2];
        f[0] = _sysFunctions[0];
        f[1] = _sysFunctions[1];
        return f;
    }

    @Override
    public String toString() {
        return _sysFunctions[0].toString() + " | " + _sysFunctions[1].toString();
    }

    private ISysFunction[] _sysFunctions;

    public SystemOfFunctions2() {
        _sysFunctions = new ISysFunction[2];
        _sysFunctions[0] = new SFunction1();
        _sysFunctions[1] = new SFunction2();
    }

    private class SFunction1 implements ISysFunction {

        // y = +-Math.sqrt((0,3 - x -0,1*x**2))

        //
        @Override
        public String toString() {
            return "0.1x**2 + x + 0,2y**2 -0,3 = 0";
        }

        @Override
        public double functionOf(double x) {
            double x1 = (0.3 - x - 0.1 * x * x) / 0.2;
            if (x1 < 0) return Double.NaN;
            return Math.sqrt(x1);
        }
        @Override
        public double getStep(double x){
            if (x > 0 && x < 0.5 || x < -10.2 && x > -10.5) return  0.01;
            else return 0.01;

        }

        @Override
        public boolean isCircle() {
            return true;
        }

        public Graph getGrafic2(int xStart, int xStop, int xZero, int yZero, int smp) {
            Graph g = new Graph();
            double step = 0.05;
            int n = 0;
            int size = (int) ((xStop - xStart) / step + 1);
            int[] xs = new int[size * 2 + 1];
            int[] ys = new int[size * 2 + 1];
            for (double x = xStart; x <= xStop; x += step) {
                step = getStep(x);
                int xInPoints = (int) (x * smp + xZero);
                double y = functionOf(x);
                if (Double.isNaN(y) || y == Double.MAX_VALUE) continue;
                int yInPoints = (int) Math.round(yZero - y * smp);
                xs[n] = xInPoints;
                ys[n] = yInPoints;
                n++;
            }
            for (double x = xStop; x >= xStart; x -= step) {
                step = getStep(x);

                int xInPoints = (int) (x * smp + xZero);
                double y = functionOf(x);
                if (Double.isNaN(y) ||y == Double.MAX_VALUE) continue;
                int yInPoints = (int) Math.round(yZero + y * smp);
                xs[n] = xInPoints;
                ys[n] = yInPoints;
                n++;
            }
            g.setN(n);
            g.setYPoints(ys);
            g.setXPoints(xs);
            return g;
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
         * Приведенный вид x = 0,3 - 0,1x**2 - 0,2y**2
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            return 0.3 - 0.1*x*x - 0.2*y*y;
        }

        /**
         * -0.2x
         * @param x
         * @return
         */
        @Override
        public double phi1X(double x,double y) {
            return -0.2*x;
        }

        /**
         * -0.4y
         * @param y
         * @return
         */
        @Override
        public double phi1Y(double x, double y) {
            return -0.4*y;
        }

        /**
         * 0.1*x**2 + x + 0,2*y**2 -0,3 = 0
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return 0.1*x*x + x + 0.2*y*y - 0.3;
        }

        @Override
        public double f1x(double x, double y) {
            return 0.2*x + 1;
        }

        @Override
        public double f1y(double x, double y) {
            return 0.4*y;
        }
    }

    /**
     * 0,2*x**2 + y + 0,1*x*y - 0,7 = 0
     */
    private class SFunction2 implements ISysFunction {

        @Override
        public String toString() {
            return "0,2x**2 + y + 0,1xy - 0,7 = 0";
        }

        /**
         * y = (0,7 - 0,2*x**2)/(1 + 0,1x)
         *
         * @param x функция от
         * @return
         */
        @Override
        public double functionOf(double x) {
            return (0.7 - 0.2 * x * x) / (1 + 0.1 * x);
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
         * 0,2*x**2 + y + 0,1*x*y - 0,7 = 0
         * Приведенный вид y = 0,7 - 0.1xy - 0,2x**2
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            return 0.7 - 0.1*x*y - 0.2*x*x;
        }

        /**
         * -0.1y - 0.4x
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi1X(double x,double y) {
            return -0.1*y - 0.4*x;
        }

        /**
         * -0.1x
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi1Y(double x,double y) {
            return -0.1*x;
        }

        /**
         * 0,2*x**2 + y + 0,1*x*y - 0,7 = 0
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return 0.2*x*x + y + 0.1*x*y - 0.7;
        }

        @Override
        public double f1x(double x, double y) {
            return 0.4*x + 0.1*y;
        }

        @Override
        public double f1y(double x, double y) {
            return 1 + 0.1*x;
        }
    }
}
