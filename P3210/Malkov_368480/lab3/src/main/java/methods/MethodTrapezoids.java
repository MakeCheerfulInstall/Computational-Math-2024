package methods;

import util.Result;

import java.util.function.Function;

public class MethodTrapezoids extends Method{
    @Override
    public Result compute(Function<Double, Double> function, double a, double b, double accuracy, String modify) throws StringIndexOutOfBoundsException {
        long partition2 = START_PARTITION * 2;

        double res1, res2;
        res1 = computeRes(function, a, b, START_PARTITION, modify);
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
        double x, h, res;
        res = 0;
        h = (b - a) / n;
        for (int i = 0; i < n; i++) {
            x = a + h * i;
            res += h * (function.apply(x) + function.apply(x + h));
        }
        if(Math.abs(0.5 * res)>60000){
            throw new StringIndexOutOfBoundsException();
        }
        return 0.5 * res;
    }
    @Override
    public String toString(){
        return "Метод трапеций";
    }
}
