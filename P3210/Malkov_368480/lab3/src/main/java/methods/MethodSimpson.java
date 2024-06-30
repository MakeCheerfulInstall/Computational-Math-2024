package methods;

import util.Result;

import java.util.function.Function;

public class MethodSimpson extends Method{
    @Override
    public Result compute(Function<Double, Double> function, double a, double b, double accuracy, String modify) throws StringIndexOutOfBoundsException {
        long partition2 = START_PARTITION * 2;

        double res1 = computeRes(function, a, b, START_PARTITION, modify);
        double res2;
        while (true) {
            res2 = computeRes(function, a, b, partition2, modify);
            if (Math.abs(res2 - res1) < accuracy)
                break;
            partition2 *= 2;
            res1 = res2;
        }

        return new Result(res2, partition2);
    }

    @Override
    double computeRes(Function<Double, Double> function, double a, double b, long n, String modify) throws StringIndexOutOfBoundsException {
        double x, res, h;
        res = 0;
        h = (b - a) / n;
        x = a;
        res += function.apply(x);
        for (int i = 1; i < n; i += 2) {
            x = a + h * i;
            res += 4 * function.apply(x);
        }
        for (int i = 2; i < n; i += 2) {
            x = a + h * i;
            res += 2 * function.apply(x);
        }
        x = b;
        res += function.apply(x);
        if(Math.abs(h / 3 * res)>60000){
            throw new StringIndexOutOfBoundsException();
        }
        return h / 3 * res;
    }
    @Override
    public String toString(){
        return "Метод Симпсона";
    }
}
