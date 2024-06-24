package Lab2;

/**
 * Система уравнений
 * x**2 + y**2 = 4;
 * y = 3 * x**2;
 *
 */
public class SystemOfFunctions implements  ISysFunctions{

    public SystemOfFunctions() {
        _sysFunctions = new ISysFunction[2];
        _sysFunctions[0] = new SFunction1();
        _sysFunctions[1] = new SFunction2();
    }

    @Override
    public String toString() {
        return _sysFunctions[0].toString() + " | " + _sysFunctions[1].toString();
    }

    private ISysFunction[] _sysFunctions = null;
    @Override
    public IFunction[] getSysFunctions() {
        IFunction[] f = new IFunction[_sysFunctions.length];
        int i = 0;
        for(var ff:_sysFunctions){
            f[i++] = ff;
        }
        return f;
    }

    /**
     * Уравнение x**2 + y**2 = 4;
     */
    private class SFunction1 implements ISysFunction{

        @Override
        public boolean isCircle() {
            return true;
        }

        @Override
        public Graph getGrafic(int xStart, int xStop, int xZero, int yZero, int smp) {
            Graph g = new Graph();
            double step = 0.001;
            int n = 0;
            int size = (int) ((4) / step + 1);
            int[] xs = new int[size*2];
            int[] ys = new int[size*2];
            for (double x = -2; x <= 2; x += step) {
                int xInPoints = (int) (x * smp + xZero);
                double y = functionOf(x);
                int yInPoints = (int) Math.round(yZero - y * smp);
                xs[n] = xInPoints;
                ys[n] = yInPoints;
                n++;
            }
            for(double x = 2; x >= -2;x -= step){
                int xInPoints = (int) (x * smp + xZero);
                double y = functionOf(x);
                int yInPoints = (int) Math.round(yZero + y * smp);
                xs[n] = xInPoints;
                ys[n] =yInPoints;
                n++;
            }
            g.setN(n);
            g.setYPoints(ys);
            g.setXPoints(xs);
            return g;
        }

        @Override
        // y = sqrt(4 - x**2)
        public double functionOf(double x) {
            if(Math.abs(x)> 2) return Double.MAX_VALUE;
            return Math.sqrt((4-x*x));
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
        public String toString() {
            //return "y = sqrt(4 - x**2)";
            return "x**2+y**2-4=0";
        }

        /**
         * x**2 + y**2 = 4;
         * y = x**2 + y**2 - 4 + y
         * Приведенный вид
         * x = Math.sqrt(4 - y**2)
         * @param x
         * @param y
         * @return
         */
        @Override
        public double phi(double x, double y) {
            if(Math.abs(y) > 2)throw new CalcErrorException(String.format("Функция %s не имеет решения для y = %f",
                    toString(),y));
            return Math.sqrt(4 - y*y);
        }

        @Override
        public double phi1X(double x,double y) {
            return 0;
        }

        @Override
        public double phi1Y(double x, double y) {
            return -1 * y/Math.pow((4-y*y),1/2.);
        }

        /**
         * x**2 + y**2 - 4 = 0
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return x*x + y*y - 4;
        }

        @Override
        public double f1x(double x, double y) {
            return 2*x;
        }

        @Override
        public double f1y(double x, double y) {
            return 2*y;
        }
    }

    private class SFunction2 implements ISysFunction{
        // y = 3 * x**2;
        @Override
        public double functionOf(double x) {
            return 3 * x*x;
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
        public String toString() {
            return "y - 3 * x**2 = 0";
        }

        @Override
        public double phi(double x, double y) {
            return 3*x*x;
        }

        @Override
        public double phi1X(double x,double y) {
            return 6*x;
        }

        @Override
        public double phi1Y(double x, double y) {
            return 0;
        }

        /**
         * y - 3 * x**2 = 0;
         * @param x
         * @param y
         * @return
         */
        @Override
        public double originalFunc(double x, double y) {
            return y-3*x*x;
        }

        @Override
        public double f1x(double x, double y) {
            return -6*x;
        }

        @Override
        public double f1y(double x, double y) {
            return 1;
        }
    }
}
